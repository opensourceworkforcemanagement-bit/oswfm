import time
import pytest
import requests

# Auth endpoints (register/login/logout/refresh/validate/authenticate)
# route: gateway /api/v1/authentication/** → authservice → userservice
AUTH_BASE_URL = "http://localhost:1110/api/v1/authentication/users"

# CRUD + profile-settings endpoints
# route: gateway /api/v1/users/** → userservice directly
USERS_BASE_URL = "http://localhost:1110/api/v1/users"

# Password rules (from PasswordSecurityProperties defaults):
#   minLength=12, requires upper+lower+digit+special, no 3-char sequences/repeats.
# "Jk#9mWpL@2vN" — 13 chars, all char classes, no sequences, no repeats of 3+.
_PASSWORD = "Jk#9mWpL@2vN"


def pytest_configure(config):
    config.addinivalue_line("markers", "auth: authentication flow tests")
    config.addinivalue_line("markers", "user_crud: user CRUD tests")
    config.addinivalue_line("markers", "profile_settings: profile settings tests")


@pytest.fixture(scope="session")
def base_url():
    return AUTH_BASE_URL


@pytest.fixture(scope="session")
def users_url():
    return USERS_BASE_URL


@pytest.fixture(scope="session")
def login_payload(auth_tokens):
    return {"userName": auth_tokens["userName"], "password": _PASSWORD}


_BFF_LOGIN_URL = "http://localhost:1110/api/bff/auth/login"


def _setup_session_user(base_url):
    """Register a fresh user, login twice, and return tokens + an authenticated session.

    - Registration and raw-token login go through the auth service
      (POST /api/v1/authentication/users/login).
    - The BFF login (POST /api/bff/auth/login) is called separately on the same
      requests.Session so the gateway sets the APPSESSION cookie.  The running
      JwtAuthenticationFilter reads ACCESS_TOKEN from that server-side session;
      without the APPSESSION cookie every session-protected request gets 401.

    Retries once on 500 to work around a service-side race in PasswordService.
    """
    for attempt in range(2):
        uid = f"apitestuser{int(time.time() * 1000) % 1_000_000_000}"
        payload = {
            "email": f"{uid}@chere.com",
            "userName": uid,
            "password": _PASSWORD,
            "firstName": "Api",
            "middleName": "",
            "lastName": "Tester",
        }

        # Register
        reg = requests.post(f"{base_url}/register", json=payload)
        if reg.status_code != 200:
            if attempt == 0:
                time.sleep(0.5)
                continue
            pytest.fail(f"Setup register failed: {reg.status_code} {reg.text}")

        # Auth-service login — get raw tokens for fixtures that need them
        # login_resp = requests.post(
        #     f"{base_url}/login",
        #     json={"userName": uid, "password": _PASSWORD},
        # )
        # if login_resp.status_code != 200:
        #     if attempt == 0:
        #         time.sleep(1)
        #         continue
        #     pytest.fail(f"Setup login failed after retry: {login_resp.status_code} {login_resp.text}")
        # body = login_resp.json()
        # access_token  = body["response"]["accessToken"]
        # refresh_token = body["response"]["refreshToken"]

        # BFF login — establishes the APPSESSION cookie the gateway filter checks,
        # and also returns tokens via the session for raw-token fixtures.
        session = requests.Session()
        bff_resp = session.post(
            _BFF_LOGIN_URL,
            json={"userName": uid, "password": _PASSWORD},
        )
        if bff_resp.status_code != 200:
            if attempt == 0:
                time.sleep(1)
                continue
            pytest.fail(f"BFF login failed: {bff_resp.status_code} {bff_resp.text}")

        # Issue a GET so the BFF's csrfCookieWebFilter writes the XSRF-TOKEN cookie.
        # The CSRF filter only sets the cookie when a CsrfToken attribute is present on
        # the exchange; login is CSRF-exempt so the cookie may not appear until a GET.
        session.get("http://localhost:1110/api/bff/auth/me")

        # BFF login response is BffUserInfo (no tokens); get raw tokens via auth-service login.
        # Use the same session so only one login exists — a second independent login can
        # invalidate the first token and cause 403s on subsequent requests.
        login_resp = session.post(
            f"{base_url}/login",
            json={"userName": uid, "password": _PASSWORD},
        )
        if login_resp.status_code != 200:
            pytest.fail(f"Auth-service login failed: {login_resp.status_code} {login_resp.text}")

        body = login_resp.json()
        xsrf_token = session.cookies.get("XSRF-TOKEN", "")
        print(f"\n[conftest] BFF login cookies: {dict(session.cookies)}")
        return {
            "accessToken":  body["response"]["accessToken"],
            "refreshToken": body["response"]["refreshToken"],
            "userName":     uid,
            "session":      session,   # carries APPSESSION + XSRF-TOKEN cookies
            "xsrfToken":    xsrf_token,
        }
    pytest.fail("Setup failed after retries")


@pytest.fixture(scope="session")
def auth_tokens(base_url):
    return _setup_session_user(base_url)


@pytest.fixture(scope="session")
def access_token(auth_tokens):
    return auth_tokens["accessToken"]


@pytest.fixture(scope="session")
def refresh_token(auth_tokens):
    return auth_tokens["refreshToken"]


@pytest.fixture(scope="session")
def authed_session(auth_tokens):
    """requests.Session carrying the gateway SESSION cookie from login.
    The running JwtAuthenticationFilter checks ACCESS_TOKEN in that server-side
    session, so any session-protected endpoint needs this session object."""
    return auth_tokens["session"]


@pytest.fixture(scope="session")
def xsrf_token(auth_tokens):
    return auth_tokens["xsrfToken"]


@pytest.fixture(scope="session")
def auth_headers(access_token, xsrf_token):
    """Headers for state-changing requests through the BFF gateway.

    The gateway uses the Token Vault pattern: the JWT lives server-side in the
    APPSESSION; the browser/client never sends it.  The Authorization header here
    is only used by endpoints that accept raw-token auth (e.g. the auth-service
    proxied routes).  State-changing requests to /api/v1/** also require the
    X-XSRF-TOKEN header to pass the gateway's CSRF filter.
    """
    return {
        "Authorization": f"Bearer {access_token}",
        "X-XSRF-TOKEN":  xsrf_token,
    }

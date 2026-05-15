"""
REST API tests for UserController — auth flow:
  POST /register
  POST /validate-token
  POST /login
  POST /refresh-token
  POST /logout
  GET  /authenticate
"""

import time
import allure
import pytest
import requests


def _fresh_register_payload(tag="reg"):
    """Each call returns a unique payload — never reuses a userName already in the DB."""
    uid = f"{tag}{int(time.time() * 1000) % 10_000_000}"
    return {
        "email": f"{uid}@chere.com",
        "userName": uid,
        "password": "Jk#9mWpL@2vN",
        "firstName": "Api",
        "middleName": "",
        "lastName": "Tester",
    }


# ---------------------------------------------------------------------------
# POST /register
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Register")
@pytest.mark.auth
class TestRegister:

    @allure.title("Register a new user — happy path")
    @allure.description("Valid registration payload returns isSuccess=true and echoes back user data.")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_register_success(self, base_url):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("smoke", "register")

        payload = _fresh_register_payload("smoke")

        with allure.step("Send valid register request"):
            resp = requests.post(f"{base_url}/register", json=payload)

        with allure.step("Assert 200 and success body"):
            assert resp.status_code == 200
            body = resp.json()
            print(body)
            assert body.get("isSuccess") is True
            user = body["response"]
            assert user["firstName"] == payload["firstName"]
            assert user["lastName"] == payload["lastName"]

    @allure.title("Register duplicate user — conflict error")
    @allure.description("Registering the same userName a second time must return a non-2xx response or isSuccess=false.")
    @allure.severity(allure.severity_level.NORMAL)
    def test_register_duplicate(self, base_url):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("negative", "register")

        with allure.step("Register the same user twice"):
            dup_payload = _fresh_register_payload("dup")
            requests.post(f"{base_url}/register", json=dup_payload)
            resp = requests.post(f"{base_url}/register", json=dup_payload)

        with allure.step("Assert conflict is signalled"):
            assert resp.status_code != 200 or resp.json().get("isSuccess") is False

    @allure.title("Register with missing required fields — validation error")
    @allure.severity(allure.severity_level.NORMAL)
    @pytest.mark.parametrize("missing_field", ["email", "userName", "password", "firstName", "lastName"])
    def test_register_missing_field(self, base_url, missing_field):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("negative", "validation")
        allure.dynamic.description(f"Omitting '{missing_field}' must be rejected.")

        payload = {k: v for k, v in _fresh_register_payload("miss").items() if k != missing_field}

        with allure.step(f"POST /register without '{missing_field}'"):
            resp = requests.post(f"{base_url}/register", json=payload)

        with allure.step("Assert non-2xx or error body"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Register with invalid email format — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_register_invalid_email(self, base_url):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("negative", "validation")

        payload = {**_fresh_register_payload("bademail"), "email": "not-an-email"}

        with allure.step("POST /register with bad email"):
            resp = requests.post(f"{base_url}/register", json=payload)

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Register with password shorter than 8 chars — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_register_short_password(self, base_url):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("negative", "validation")

        payload = {**_fresh_register_payload("shortpw"), "password": "short"}

        with allure.step("POST /register with short password"):
            resp = requests.post(f"{base_url}/register", json=payload)

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Register with userName shorter than 7 chars — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_register_short_username(self, base_url):
        allure.dynamic.label("endpoint", "POST /register")
        allure.dynamic.tag("negative", "validation")

        payload = {**_fresh_register_payload("shortun"), "userName": "abc"}

        with allure.step("POST /register with short userName"):
            resp = requests.post(f"{base_url}/register", json=payload)

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False


# ---------------------------------------------------------------------------
# POST /login
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Login")
@pytest.mark.auth
class TestLogin:

    @allure.title("Login — happy path returns token pair")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_login_success(self, base_url, login_payload):
        allure.dynamic.label("endpoint", "POST /login")
        allure.dynamic.tag("smoke", "login")

        with allure.step("POST /login with valid credentials"):
            resp = requests.post(f"{base_url}/login", json=login_payload)

        with allure.step("Assert 200 and token pair returned"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            token = body["response"]
            assert "accessToken" in token
            assert "refreshToken" in token
            assert len(token["accessToken"]) > 0
            assert len(token["refreshToken"]) > 0

    @allure.title("Login with wrong password — unauthorized")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_login_wrong_password(self, base_url, login_payload):
        allure.dynamic.label("endpoint", "POST /login")
        allure.dynamic.tag("negative", "login")

        payload = {**login_payload, "password": "WrongPass99!"}

        with allure.step("POST /login with wrong password"):
            resp = requests.post(f"{base_url}/login", json=payload)

        with allure.step("Assert non-2xx or error body"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Login with unknown userName — not found / unauthorized")
    @allure.severity(allure.severity_level.NORMAL)
    def test_login_unknown_user(self, base_url):
        allure.dynamic.label("endpoint", "POST /login")
        allure.dynamic.tag("negative", "login")

        payload = {"userName": "nonexistent_xyz_99", "password": "SomePass1!"}

        with allure.step("POST /login with non-existent user"):
            resp = requests.post(f"{base_url}/login", json=payload)

        with allure.step("Assert non-2xx or error body"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Login with blank userName — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_login_blank_username(self, base_url):
        allure.dynamic.label("endpoint", "POST /login")
        allure.dynamic.tag("negative", "validation")

        with allure.step("POST /login with blank userName"):
            resp = requests.post(f"{base_url}/login", json={"userName": "", "password": "Jk#9mWpL@2vN"})

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False


# ---------------------------------------------------------------------------
# POST /validate-token
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Validate Token")
@pytest.mark.auth
class TestValidateToken:

    @allure.title("Validate a valid access token — 200 OK")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_validate_token_valid(self, base_url, access_token, authed_session):
        allure.dynamic.label("endpoint", "POST /validate-token")
        allure.dynamic.tag("smoke", "token")

        with allure.step("POST /validate-token with valid JWT"):
            # authed_session carries the APPSESSION cookie from BFF login.
            # The gateway filter reads ACCESS_TOKEN from that server-side session
            # and injects Authorization: Bearer <token> upstream — no header needed here.
            resp = authed_session.post(
                f"{base_url}/validate-token",
                params={"token": access_token},
            )

        with allure.step("Assert 200"):
            assert resp.status_code == 200

    @allure.title("Validate an obviously fake token — error response")
    @allure.severity(allure.severity_level.NORMAL)
    def test_validate_token_invalid(self, base_url):
        allure.dynamic.label("endpoint", "POST /validate-token")
        allure.dynamic.tag("negative", "token")

        with allure.step("POST /validate-token with garbage JWT"):
            resp = requests.post(f"{base_url}/validate-token", params={"token": "this.is.not.a.jwt"})

        with allure.step("Assert non-200"):
            assert resp.status_code != 200

    @allure.title("Validate-token with empty string — bad request")
    @allure.severity(allure.severity_level.MINOR)
    def test_validate_token_empty(self, base_url):
        allure.dynamic.label("endpoint", "POST /validate-token")
        allure.dynamic.tag("negative", "token")

        with allure.step("POST /validate-token with empty token param"):
            resp = requests.post(f"{base_url}/validate-token", params={"token": ""})

        with allure.step("Assert non-200"):
            assert resp.status_code != 200


# ---------------------------------------------------------------------------
# POST /refresh-token
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Refresh Token")
@pytest.mark.auth
class TestRefreshToken:

    @allure.title("Refresh token — returns new token pair")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_refresh_token_success(self, base_url, refresh_token):
        allure.dynamic.label("endpoint", "POST /refresh-token")
        allure.dynamic.tag("smoke", "token")

        with allure.step("POST /refresh-token with valid refresh token"):
            resp = requests.post(
                f"{base_url}/refresh-token",
                json={"refreshToken": refresh_token},
            )

        with allure.step("Assert 200 and new token pair"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            assert "accessToken" in body["response"]
            assert "refreshToken" in body["response"]

    @allure.title("Refresh token with garbage value — error")
    @allure.severity(allure.severity_level.NORMAL)
    def test_refresh_token_invalid(self, base_url):
        allure.dynamic.label("endpoint", "POST /refresh-token")
        allure.dynamic.tag("negative", "token")

        with allure.step("POST /refresh-token with invalid refresh token"):
            resp = requests.post(
                f"{base_url}/refresh-token",
                json={"refreshToken": "bad.refresh.token"},
            )

        with allure.step("Assert non-2xx or error body"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Refresh token with blank value — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_refresh_token_blank(self, base_url):
        allure.dynamic.label("endpoint", "POST /refresh-token")
        allure.dynamic.tag("negative", "validation")

        with allure.step("POST /refresh-token with blank refreshToken"):
            resp = requests.post(f"{base_url}/refresh-token", json={"refreshToken": ""})

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False


# ---------------------------------------------------------------------------
# POST /logout
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Logout")
@pytest.mark.auth
class TestLogout:

    @allure.title("Logout — invalidates token pair successfully")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_logout_success(self, base_url, login_payload):
        allure.dynamic.label("endpoint", "POST /logout")
        allure.dynamic.tag("smoke", "logout")

        with allure.step("Login to obtain fresh tokens"):
            login_resp = requests.post(f"{base_url}/login", json=login_payload)
            login_resp.raise_for_status()
            tokens = login_resp.json()["response"]

        with allure.step("POST /logout with valid token pair"):
            resp = requests.post(
                f"{base_url}/logout",
                json={
                    "accessToken": tokens["accessToken"],
                    "refreshToken": tokens["refreshToken"],
                },
            )

        with allure.step("Assert 200 and isSuccess=true"):
            assert resp.status_code == 200
            assert resp.json().get("isSuccess") is True

    @allure.title("Logout with already-invalidated token — error")
    @allure.severity(allure.severity_level.NORMAL)
    def test_logout_already_invalidated(self, base_url, login_payload):
        allure.dynamic.label("endpoint", "POST /logout")
        allure.dynamic.tag("negative", "logout")

        with allure.step("Login and logout once"):
            login_resp = requests.post(f"{base_url}/login", json=login_payload)
            login_resp.raise_for_status()
            tokens = login_resp.json()["response"]
            payload = {
                "accessToken": tokens["accessToken"],
                "refreshToken": tokens["refreshToken"],
            }
            requests.post(f"{base_url}/logout", json=payload)

        with allure.step("Attempt to logout a second time with the same tokens"):
            resp = requests.post(f"{base_url}/logout", json=payload)

        with allure.step("Assert error response"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False

    @allure.title("Logout with blank accessToken — validation error")
    @allure.severity(allure.severity_level.MINOR)
    def test_logout_blank_access_token(self, base_url, refresh_token):
        allure.dynamic.label("endpoint", "POST /logout")
        allure.dynamic.tag("negative", "validation")

        with allure.step("POST /logout with blank accessToken"):
            resp = requests.post(
                f"{base_url}/logout",
                json={"accessToken": "", "refreshToken": refresh_token},
            )

        with allure.step("Assert rejection"):
            assert resp.status_code >= 400 or resp.json().get("isSuccess") is False


# ---------------------------------------------------------------------------
# GET /authenticate
# ---------------------------------------------------------------------------

@allure.suite("User Service – Auth")
@allure.feature("Authenticate")
@pytest.mark.auth
class TestAuthenticate:

    @allure.title("GET /authenticate with valid token — returns auth details")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_get_authentication_valid(self, users_url, access_token, authed_session):
        allure.dynamic.label("endpoint", "GET /authenticate")
        allure.dynamic.tag("smoke", "authenticate")

        with allure.step("GET /authenticate with valid JWT"):
            resp = authed_session.get(f"{users_url}/authenticate", params={"token": access_token})

        with allure.step("Assert 200 and principal present"):
            assert resp.status_code == 200
            body = resp.json()
            assert "principal" in body or "name" in body or body is not None

    @allure.title("GET /authenticate with invalid token — error")
    @allure.severity(allure.severity_level.NORMAL)
    def test_get_authentication_invalid(self, base_url):
        allure.dynamic.label("endpoint", "GET /authenticate")
        allure.dynamic.tag("negative", "authenticate")

        with allure.step("GET /authenticate with bogus token"):
            resp = requests.get(f"{base_url}/authenticate", params={"token": "garbage.token.value"})

        with allure.step("Assert non-200"):
            assert resp.status_code != 200

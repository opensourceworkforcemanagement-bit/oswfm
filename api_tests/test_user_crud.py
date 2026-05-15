"""
REST API tests for UserController — user CRUD:
  GET  /
  GET  /active
  GET  /{id}
  PUT  /{id}
  DELETE /{id}
"""

import time as _time
import allure
import pytest
import requests


# ---------------------------------------------------------------------------
# GET /  — all users
# ---------------------------------------------------------------------------

@allure.suite("User Service – User CRUD")
@allure.feature("Get All Users")
@pytest.mark.user_crud
class TestGetAllUsers:

    @allure.title("GET / — returns list of users")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_get_all_users(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "GET /")
        allure.dynamic.tag("smoke", "list")

        with allure.step("GET all users with valid auth"):
            resp = authed_session.get(users_url, headers=auth_headers)

        with allure.step("Assert 200 and list response"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            assert isinstance(body["response"], list)

    @allure.title("GET / without auth — unauthorized")
    @allure.severity(allure.severity_level.NORMAL)
    def test_get_all_users_no_auth(self, users_url):
        allure.dynamic.label("endpoint", "GET /")
        allure.dynamic.tag("negative", "auth")

        with allure.step("GET all users without Authorization header"):
            resp = requests.get(users_url)

        with allure.step("Assert 401, 403 or 404"):
            assert resp.status_code in (401, 403, 404)


# ---------------------------------------------------------------------------
# GET /active  — active users
# ---------------------------------------------------------------------------

@allure.suite("User Service – User CRUD")
@allure.feature("Get Active Users")
@pytest.mark.user_crud
class TestGetActiveUsers:

    @allure.title("GET /active — returns only active users (userStatus=1)")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_get_active_users(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "GET /active")
        allure.dynamic.tag("smoke", "list")

        with allure.step("GET /active with valid auth"):
            resp = authed_session.get(f"{users_url}/active", headers=auth_headers)

        with allure.step("Assert 200 and each user has userStatus=1"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            users = body["response"]
            assert isinstance(users, list)
            for user in users:
                assert user.get("userStatus") == 1

    @allure.title("GET /active without auth — unauthorized")
    @allure.severity(allure.severity_level.NORMAL)
    def test_get_active_users_no_auth(self, users_url):
        allure.dynamic.label("endpoint", "GET /active")
        allure.dynamic.tag("negative", "auth")

        with allure.step("GET /active without Authorization header"):
            resp = requests.get(f"{users_url}/active")

        with allure.step("Assert 401, 403 or 404"):
            assert resp.status_code in (401, 403, 404)


# ---------------------------------------------------------------------------
# GET /{id}  — get user by id
# ---------------------------------------------------------------------------

@allure.suite("User Service – User CRUD")
@allure.feature("Get User By ID")
@pytest.mark.user_crud
class TestGetUserById:

    @allure.title("GET /{id} — returns user for existing id")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_get_user_by_id_found(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "GET /{id}")
        allure.dynamic.tag("smoke", "get-by-id")

        with allure.step("Fetch all users to get a valid id"):
            all_resp = authed_session.get(users_url, headers=auth_headers)
            all_resp.raise_for_status()
            users = all_resp.json()["response"]

        if not users:
            pytest.skip("No users available to look up by id")

        user_id = users[0]["userId"]

        with allure.step(f"GET /{user_id}"):
            resp = authed_session.get(f"{users_url}/{user_id}", headers=auth_headers)

        with allure.step("Assert 200 and matching userId"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            assert body["response"]["userId"] == user_id

    @allure.title("GET /{id} — non-existent id returns 404")
    @allure.severity(allure.severity_level.NORMAL)
    def test_get_user_by_id_not_found(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "GET /{id}")
        allure.dynamic.tag("negative", "get-by-id")

        with allure.step("GET /9999999 (non-existent id)"):
            resp = authed_session.get(f"{users_url}/9999999", headers=auth_headers)

        with allure.step("Assert 404 or error body"):
            assert resp.status_code == 404 or resp.json().get("isSuccess") is False

    @allure.title("GET /{id} without auth — unauthorized")
    @allure.severity(allure.severity_level.MINOR)
    def test_get_user_by_id_no_auth(self, users_url):
        allure.dynamic.label("endpoint", "GET /{id}")
        allure.dynamic.tag("negative", "auth")

        with allure.step("GET /1 without Authorization header"):
            resp = requests.get(f"{users_url}/1")

        with allure.step("Assert 401, 403 or 404"):
            assert resp.status_code in (401, 403, 404)


# ---------------------------------------------------------------------------
# PUT /{id}  — update user
# ---------------------------------------------------------------------------

@allure.suite("User Service – User CRUD")
@allure.feature("Update User")
@pytest.mark.user_crud
class TestUpdateUser:

    @allure.title("PUT /{id} — updates firstName successfully")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_update_user_success(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "PUT /{id}")
        allure.dynamic.tag("smoke", "update")

        with allure.step("Fetch all users to pick an updatable id"):
            all_resp = authed_session.get(users_url, headers=auth_headers)
            all_resp.raise_for_status()
            users = all_resp.json()["response"]

        if not users:
            pytest.skip("No users available to update")

        target = users[0]
        user_id = target["userId"]
        updated_first_name = "UpdatedFirst"

        with allure.step(f"PUT /{user_id} with new firstName"):
            payload = {**target, "firstName": updated_first_name}
            resp = authed_session.put(f"{users_url}/{user_id}", json=payload, headers=auth_headers)

        with allure.step("Assert 200 and updated firstName"):
            assert resp.status_code == 200
            body = resp.json()
            assert body.get("isSuccess") is True
            assert body["response"]["firstName"] == updated_first_name

    @allure.title("PUT /{id} — non-existent id returns 404")
    @allure.severity(allure.severity_level.NORMAL)
    def test_update_user_not_found(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "PUT /{id}")
        allure.dynamic.tag("negative", "update")

        with allure.step("PUT /9999999 with dummy payload"):
            payload = {"userId": 9999999, "firstName": "Ghost", "lastName": "User", "userStatus": 1}
            resp = authed_session.put(f"{users_url}/9999999", json=payload, headers=auth_headers)

        with allure.step("Assert 404 or error body"):
            assert resp.status_code == 404 or resp.json().get("isSuccess") is False

    @allure.title("PUT /{id} without auth — unauthorized")
    @allure.severity(allure.severity_level.MINOR)
    def test_update_user_no_auth(self, users_url):
        allure.dynamic.label("endpoint", "PUT /{id}")
        allure.dynamic.tag("negative", "auth")

        with allure.step("PUT /1 without Authorization header"):
            resp = requests.put(f"{users_url}/1", json={"firstName": "X"})

        with allure.step("Assert 401, 403 or 404"):
            assert resp.status_code in (401, 403, 404)


# ---------------------------------------------------------------------------
# DELETE /{id}  — delete user
# ---------------------------------------------------------------------------

@allure.suite("User Service – User CRUD")
@allure.feature("Delete User")
@pytest.mark.user_crud
class TestDeleteUser:

    @allure.title("DELETE /{id} — deletes a freshly registered user")
    @allure.severity(allure.severity_level.CRITICAL)
    def test_delete_user_success(self, base_url, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "DELETE /{id}")
        allure.dynamic.tag("smoke", "delete")

        with allure.step("Register a throwaway user via auth endpoint"):
            uid = f"del{int(_time.time() * 1000) % 10_000_000}"
            throwaway = {
                "email": f"{uid}@chere.com",
                "userName": uid,
                "password": "Jk#9mWpL@2vN",
                "firstName": "Delete",
                "lastName": "Me",
            }
            reg_resp = requests.post(f"{base_url}/register", json=throwaway)
            reg_body = reg_resp.json() if reg_resp.content else {}
            if reg_resp.status_code != 200 or "response" not in reg_body:
                pytest.skip(f"Could not create throwaway user for delete test: {reg_resp.status_code} {reg_resp.text[:200]}")
            user_id = reg_body["response"]["userId"]

        with allure.step(f"DELETE /{user_id} via users endpoint"):
            resp = authed_session.delete(f"{users_url}/{user_id}", headers=auth_headers)

        with allure.step("Assert 200 and isSuccess=true"):
            assert resp.status_code == 200
            assert resp.json().get("isSuccess") is True

    @allure.title("DELETE /{id} — non-existent id returns 404")
    @allure.severity(allure.severity_level.NORMAL)
    def test_delete_user_not_found(self, users_url, auth_headers, authed_session):
        allure.dynamic.label("endpoint", "DELETE /{id}")
        allure.dynamic.tag("negative", "delete")

        with allure.step("DELETE /9999999 (non-existent id)"):
            resp = authed_session.delete(f"{users_url}/9999999", headers=auth_headers)

        with allure.step("Assert 404 or error body"):
            assert resp.status_code == 404 or resp.json().get("isSuccess") is False

    @allure.title("DELETE /{id} without auth — unauthorized")
    @allure.severity(allure.severity_level.MINOR)
    def test_delete_user_no_auth(self, users_url):
        allure.dynamic.label("endpoint", "DELETE /{id}")
        allure.dynamic.tag("negative", "auth")

        with allure.step("DELETE /1 without Authorization header"):
            resp = requests.delete(f"{users_url}/1")

        with allure.step("Assert 401, 403 or 404"):
            assert resp.status_code in (401, 403, 404)

package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.api.BaseApiClient;
import ru.yandex.praktikum.config.Config;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserCredentials;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class UserSteps extends BaseApiClient {
    
    @Step("Create user with email: {user.email}")
    public Response createUser(User user) {
        return createUserNoStep(user);
    }
    
    // Метод без @Step для использования в @Before/@After
    public Response createUserNoStep(User user) {
        return given()
                .spec(getRequestSpec())
                .body(user)
                .when()
                .post(Config.USER_CREATE_ENDPOINT);
    }
    
    @Step("Login user with email: {credentials.email}")
    public Response loginUser(UserCredentials credentials) {
        return given()
                .spec(getRequestSpec())
                .body(credentials)
                .when()
                .post(Config.USER_LOGIN_ENDPOINT);
    }
    
    @Step("Delete user")
    public Response deleteUser(String accessToken) {
        return deleteUserNoStep(accessToken);
    }
    
    // Метод без @Step для использования в @Before/@After
    public Response deleteUserNoStep(String accessToken) {
        return given()
                .spec(getRequestSpecWithAuth(accessToken))
                .when()
                .delete(Config.USER_ENDPOINT);
    }
    
    @Step("Check response status code is {expectedCode}")
    public void checkStatusCode(Response response, int expectedCode) {
        response.then().statusCode(expectedCode);
    }
    
    @Step("Check success field is {expectedValue}")
    public void checkSuccessField(Response response, boolean expectedValue) {
        response.then().statusCode(expectedValue ? SC_OK : SC_FORBIDDEN);
    }
}
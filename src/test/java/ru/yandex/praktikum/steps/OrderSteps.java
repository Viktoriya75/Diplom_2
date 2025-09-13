package ru.yandex.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.api.BaseApiClient;
import ru.yandex.praktikum.config.Config;
import ru.yandex.praktikum.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps extends BaseApiClient {
    
    @Step("Create order without authorization")
    public Response createOrderWithoutAuth(Order order) {
        return given()
                .spec(getRequestSpec())
                .body(order)
                .when()
                .post(Config.ORDER_CREATE_ENDPOINT);
    }
    
    @Step("Create order with authorization")
    public Response createOrderWithAuth(Order order, String accessToken) {
        return given()
                .spec(getRequestSpecWithAuth(accessToken))
                .body(order)
                .when()
                .post(Config.ORDER_CREATE_ENDPOINT);
    }
    
    @Step("Get ingredients list")
    public Response getIngredients() {
        return given()
                .spec(getRequestSpec())
                .when()
                .get(Config.INGREDIENTS_ENDPOINT);
    }
    
    @Step("Check response status code is {expectedCode}")
    public void checkStatusCode(Response response, int expectedCode) {
        response.then().statusCode(expectedCode);
    }
}
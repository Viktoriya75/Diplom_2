package ru.yandex.praktikum.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.yandex.praktikum.config.Config;
import ru.yandex.praktikum.constants.Constants;

public class BaseApiClient {
    
    private static final int CONNECTION_TIMEOUT = Constants.CONNECTION_TIMEOUT;
    private static final int SOCKET_TIMEOUT = Constants.SOCKET_TIMEOUT;
    
    protected RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", CONNECTION_TIMEOUT)
                                .setParam("http.socket.timeout", SOCKET_TIMEOUT)))
                .build();
    }
    
    protected RequestSpecification getRequestSpecWithAuth(String accessToken) {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", accessToken)
                .setConfig(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", CONNECTION_TIMEOUT)
                                .setParam("http.socket.timeout", SOCKET_TIMEOUT)))
                .build();
    }
}
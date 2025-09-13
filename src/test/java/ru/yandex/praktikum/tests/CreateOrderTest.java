package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.Order;
import ru.yandex.praktikum.model.OrderResponse;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserResponse;
import ru.yandex.praktikum.constants.Constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateOrderTest extends BaseTest {
    
    private String accessToken;
    private User testUser;
    private List<String> validIngredients;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        
        testUser = new User(generateRandomEmail(), generateRandomPassword(), generateRandomName());
        Response createResponse = userSteps.createUserNoStep(testUser);
        accessToken = createResponse.as(UserResponse.class).getAccessToken();
        
        validIngredients = Arrays.asList(
            Constants.INGREDIENT_ID_1,
            Constants.INGREDIENT_ID_2, 
            Constants.INGREDIENT_ID_3
        );
    }
    
    @After
    public void tearDown() {
        if (accessToken != null) {
            try {
                userSteps.deleteUserNoStep(accessToken);
            } catch (Exception e) {
                // Игнорируем ошибки удаления, чтобы предотвратить зависание теста
            }
        }
    }
    
    @Test
    @DisplayName("Create order with authorization successfully")
    @Description("Test creating order with valid ingredients and authorization")
    public void createOrderWithAuthorizationSuccessfullyTest() {
        Order order = new Order(validIngredients);
        
        Response response = orderSteps.createOrderWithAuth(order, accessToken);
        orderSteps.checkStatusCode(response, SC_OK);
        
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue("Success field should be true", orderResponse.isSuccess());
        assertNotNull("Order name should not be null", orderResponse.getName());
        assertNotNull("Order data should not be null", orderResponse.getOrder());
        assertNotNull("Order number should not be null", orderResponse.getOrder().getNumber());
    }
    
    @Test
    @DisplayName("Create order without authorization successfully")
    @Description("Test creating order with valid ingredients but without authorization")
    public void createOrderWithoutAuthorizationSuccessfullyTest() {
        Order order = new Order(validIngredients);
        
        Response response = orderSteps.createOrderWithoutAuth(order);
        orderSteps.checkStatusCode(response, SC_OK);
        
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue("Success field should be true", orderResponse.isSuccess());
        assertNotNull("Order name should not be null", orderResponse.getName());
        assertNotNull("Order data should not be null", orderResponse.getOrder());
        assertNotNull("Order number should not be null", orderResponse.getOrder().getNumber());
    }
    
    @Test
    @DisplayName("Cannot create order without ingredients with authorization")
    @Description("Test creating order without ingredients with authorization returns error")
    public void cannotCreateOrderWithoutIngredientsWithAuthTest() {
        Order order = new Order(Collections.emptyList());
        
        Response response = orderSteps.createOrderWithAuth(order, accessToken);
        orderSteps.checkStatusCode(response, SC_BAD_REQUEST);
        
        OrderResponse errorResponse = response.as(OrderResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing ingredients", 
                    Constants.ERROR_MISSING_INGREDIENTS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot create order without ingredients without authorization")
    @Description("Test creating order without ingredients and without authorization returns error")
    public void cannotCreateOrderWithoutIngredientsWithoutAuthTest() {
        Order order = new Order(Collections.emptyList());
        
        Response response = orderSteps.createOrderWithoutAuth(order);
        orderSteps.checkStatusCode(response, SC_BAD_REQUEST);
        
        OrderResponse errorResponse = response.as(OrderResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing ingredients", 
                    Constants.ERROR_MISSING_INGREDIENTS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot create order with invalid ingredient hash")
    @Description("Test creating order with invalid ingredient hash returns error")
    public void cannotCreateOrderWithInvalidIngredientHashTest() {
        List<String> invalidIngredients = Arrays.asList(
            Constants.INVALID_INGREDIENT_HASH_1,
            Constants.INVALID_INGREDIENT_HASH_2
        );
        Order order = new Order(invalidIngredients);
        
        Response response = orderSteps.createOrderWithAuth(order, accessToken);
        orderSteps.checkStatusCode(response, SC_INTERNAL_SERVER_ERROR);
    }
    
    @Test
    @DisplayName("Create order with single ingredient")
    @Description("Test creating order with single valid ingredient")
    public void createOrderWithSingleIngredientTest() {
        Order order = new Order(Collections.singletonList(Constants.INGREDIENT_ID_1));
        
        Response response = orderSteps.createOrderWithAuth(order, accessToken);
        orderSteps.checkStatusCode(response, SC_OK);
        
        OrderResponse orderResponse = response.as(OrderResponse.class);
        assertTrue("Success field should be true", orderResponse.isSuccess());
        assertNotNull("Order name should not be null", orderResponse.getName());
        assertNotNull("Order data should not be null", orderResponse.getOrder());
    }
    
    @Test
    @DisplayName("Cannot create order with null ingredients")
    @Description("Test creating order with null ingredients list returns error")
    public void cannotCreateOrderWithNullIngredientsTest() {
        Order order = new Order(null);
        
        Response response = orderSteps.createOrderWithAuth(order, accessToken);
        orderSteps.checkStatusCode(response, SC_BAD_REQUEST);
        
        OrderResponse errorResponse = response.as(OrderResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing ingredients", 
                    Constants.ERROR_MISSING_INGREDIENTS, errorResponse.getMessage());
    }
}
package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserResponse;
import ru.yandex.praktikum.constants.Constants;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CreateUserTest extends BaseTest {
    
    private String accessToken;
    
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
    @DisplayName("Create unique user successfully")
    @Description("Test creating a new unique user with all required fields")
    public void createUniqueUserSuccessfullyTest() {
        User user = new User(generateRandomEmail(), generateRandomPassword(), generateRandomName());
        
        Response response = userSteps.createUser(user);
        userSteps.checkStatusCode(response, SC_OK);
        
        UserResponse userResponse = response.as(UserResponse.class);
        assertTrue("Success field should be true", userResponse.isSuccess());
        assertNotNull("User data should not be null", userResponse.getUser());
        assertNotNull("Access token should not be null", userResponse.getAccessToken());
        assertNotNull("Refresh token should not be null", userResponse.getRefreshToken());
        assertEquals("Email should match", user.getEmail().toLowerCase(), userResponse.getUser().getEmail());
        assertEquals("Name should match", user.getName(), userResponse.getUser().getName());
        
        accessToken = userResponse.getAccessToken();
    }
    
    @Test
    @DisplayName("Cannot create duplicate user")
    @Description("Test that creating a user with existing email returns error")
    public void cannotCreateDuplicateUserTest() {
        User user = new User(generateRandomEmail(), generateRandomPassword(), generateRandomName());
        
        Response firstResponse = userSteps.createUser(user);
        userSteps.checkStatusCode(firstResponse, SC_OK);
        accessToken = firstResponse.as(UserResponse.class).getAccessToken();
        
        Response duplicateResponse = userSteps.createUser(user);
        userSteps.checkStatusCode(duplicateResponse, SC_FORBIDDEN);
        
        UserResponse errorResponse = duplicateResponse.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate user exists", 
                    Constants.ERROR_USER_EXISTS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot create user without email")
    @Description("Test that creating a user without email field returns error")
    public void cannotCreateUserWithoutEmailTest() {
        User user = new User(null, generateRandomPassword(), generateRandomName());
        
        Response response = userSteps.createUser(user);
        userSteps.checkStatusCode(response, SC_FORBIDDEN);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing field", 
                    Constants.ERROR_MISSING_REQUIRED_FIELDS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot create user without password")
    @Description("Test that creating a user without password field returns error")
    public void cannotCreateUserWithoutPasswordTest() {
        User user = new User(generateRandomEmail(), null, generateRandomName());
        
        Response response = userSteps.createUser(user);
        userSteps.checkStatusCode(response, SC_FORBIDDEN);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing field", 
                    Constants.ERROR_MISSING_REQUIRED_FIELDS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot create user without name")
    @Description("Test that creating a user without name field returns error")
    public void cannotCreateUserWithoutNameTest() {
        User user = new User(generateRandomEmail(), generateRandomPassword(), null);
        
        Response response = userSteps.createUser(user);
        userSteps.checkStatusCode(response, SC_FORBIDDEN);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate missing field", 
                    Constants.ERROR_MISSING_REQUIRED_FIELDS, errorResponse.getMessage());
    }
}
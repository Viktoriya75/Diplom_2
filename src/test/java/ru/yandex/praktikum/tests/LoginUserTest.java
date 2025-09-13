package ru.yandex.praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.model.User;
import ru.yandex.praktikum.model.UserCredentials;
import ru.yandex.praktikum.model.UserResponse;
import ru.yandex.praktikum.constants.Constants;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginUserTest extends BaseTest {
    
    private String accessToken;
    private User testUser;
    
    @Before
    @Override
    public void setUp() {
        super.setUp();
        testUser = new User(generateRandomEmail(), generateRandomPassword(), generateRandomName());
        Response createResponse = userSteps.createUserNoStep(testUser);
        accessToken = createResponse.as(UserResponse.class).getAccessToken();
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
    @DisplayName("Login with existing user successfully")
    @Description("Test login with valid email and password of existing user")
    public void loginWithExistingUserSuccessfullyTest() {
        UserCredentials credentials = new UserCredentials(testUser.getEmail(), testUser.getPassword());
        
        Response response = userSteps.loginUser(credentials);
        userSteps.checkStatusCode(response, SC_OK);
        
        UserResponse loginResponse = response.as(UserResponse.class);
        assertTrue("Success field should be true", loginResponse.isSuccess());
        assertNotNull("User data should not be null", loginResponse.getUser());
        assertNotNull("Access token should not be null", loginResponse.getAccessToken());
        assertNotNull("Refresh token should not be null", loginResponse.getRefreshToken());
        assertEquals("Email should match", testUser.getEmail().toLowerCase(), loginResponse.getUser().getEmail());
        assertEquals("Name should match", testUser.getName(), loginResponse.getUser().getName());
    }
    
    @Test
    @DisplayName("Cannot login with incorrect email")
    @Description("Test login with incorrect email returns error")
    public void cannotLoginWithIncorrectEmailTest() {
        UserCredentials credentials = new UserCredentials(Constants.INVALID_EMAIL, testUser.getPassword());
        
        Response response = userSteps.loginUser(credentials);
        userSteps.checkStatusCode(response, SC_UNAUTHORIZED);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate invalid credentials", 
                    Constants.ERROR_INVALID_CREDENTIALS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot login with incorrect password")
    @Description("Test login with incorrect password returns error")
    public void cannotLoginWithIncorrectPasswordTest() {
        UserCredentials credentials = new UserCredentials(testUser.getEmail(), Constants.INVALID_PASSWORD);
        
        Response response = userSteps.loginUser(credentials);
        userSteps.checkStatusCode(response, SC_UNAUTHORIZED);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate invalid credentials", 
                    Constants.ERROR_INVALID_CREDENTIALS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot login without email")
    @Description("Test login without email field returns error")
    public void cannotLoginWithoutEmailTest() {
        UserCredentials credentials = new UserCredentials(null, testUser.getPassword());
        
        Response response = userSteps.loginUser(credentials);
        userSteps.checkStatusCode(response, SC_UNAUTHORIZED);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate invalid credentials", 
                    Constants.ERROR_INVALID_CREDENTIALS, errorResponse.getMessage());
    }
    
    @Test
    @DisplayName("Cannot login without password")
    @Description("Test login without password field returns error")
    public void cannotLoginWithoutPasswordTest() {
        UserCredentials credentials = new UserCredentials(testUser.getEmail(), null);
        
        Response response = userSteps.loginUser(credentials);
        userSteps.checkStatusCode(response, SC_UNAUTHORIZED);
        
        UserResponse errorResponse = response.as(UserResponse.class);
        assertFalse("Success field should be false", errorResponse.isSuccess());
        assertEquals("Error message should indicate invalid credentials", 
                    Constants.ERROR_INVALID_CREDENTIALS, errorResponse.getMessage());
    }
}
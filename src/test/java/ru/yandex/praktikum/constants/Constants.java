package ru.yandex.praktikum.constants;

public class Constants {
    
    // API Эндпоинты
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String USER_CREATE_ENDPOINT = "/api/auth/register";
    public static final String USER_LOGIN_ENDPOINT = "/api/auth/login";
    public static final String USER_ENDPOINT = "/api/auth/user";
    public static final String ORDER_CREATE_ENDPOINT = "/api/orders";
    public static final String INGREDIENTS_ENDPOINT = "/api/ingredients";
    
    // Настройки таймаута
    public static final int CONNECTION_TIMEOUT = 10000; // 10 секунд
    public static final int SOCKET_TIMEOUT = 10000; // 10 секунд
    
    // Сообщения об ошибках
    public static final String ERROR_USER_EXISTS = "User already exists";
    public static final String ERROR_MISSING_REQUIRED_FIELDS = "Email, password and name are required fields";
    public static final String ERROR_INVALID_CREDENTIALS = "email or password are incorrect";
    public static final String ERROR_MISSING_INGREDIENTS = "Ingredient ids must be provided";
    
    // Тестовые данные
    public static final String TEST_EMAIL_PREFIX = "test_";
    public static final String TEST_EMAIL_DOMAIN = "@test.com";
    public static final String TEST_PASSWORD_PREFIX = "password_";
    public static final String TEST_NAME_PREFIX = "User_";
    public static final int TEST_NAME_UUID_LENGTH = 8;
    
    // ID тестовых ингредиентов
    public static final String INGREDIENT_ID_1 = "61c0c5a71d1f82001bdaaa6d";
    public static final String INGREDIENT_ID_2 = "61c0c5a71d1f82001bdaaa6f";
    public static final String INGREDIENT_ID_3 = "61c0c5a71d1f82001bdaaa72";
    
    // Невалидные тестовые данные
    public static final String INVALID_EMAIL = "wrong@email.com";
    public static final String INVALID_PASSWORD = "wrongPassword123";
    public static final String INVALID_INGREDIENT_HASH_1 = "invalidHash123";
    public static final String INVALID_INGREDIENT_HASH_2 = "wrongHash456";
}
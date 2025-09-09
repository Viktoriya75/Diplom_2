package ru.yandex.praktikum.tests;

import org.junit.Before;
import ru.yandex.praktikum.steps.OrderSteps;
import ru.yandex.praktikum.steps.UserSteps;
import ru.yandex.praktikum.constants.Constants;

import java.util.UUID;

public class BaseTest {
    protected UserSteps userSteps;
    protected OrderSteps orderSteps;
    
    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
    }
    
    protected String generateRandomEmail() {
        return Constants.TEST_EMAIL_PREFIX + UUID.randomUUID().toString() + Constants.TEST_EMAIL_DOMAIN;
    }
    
    protected String generateRandomPassword() {
        return Constants.TEST_PASSWORD_PREFIX + UUID.randomUUID().toString();
    }
    
    protected String generateRandomName() {
        return Constants.TEST_NAME_PREFIX + UUID.randomUUID().toString().substring(0, Constants.TEST_NAME_UUID_LENGTH);
    }
}
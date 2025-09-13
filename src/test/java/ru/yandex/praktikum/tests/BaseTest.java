package ru.yandex.praktikum.tests;

import com.github.javafaker.Faker;
import org.junit.Before;
import ru.yandex.praktikum.steps.OrderSteps;
import ru.yandex.praktikum.steps.UserSteps;

public class BaseTest {
    protected UserSteps userSteps;
    protected OrderSteps orderSteps;
    protected Faker faker = new Faker();
    
    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
    }
    
    protected String generateRandomEmail() {
        return faker.internet().emailAddress();
    }
    
    protected String generateRandomPassword() {
        return faker.internet().password(8, 16);
    }
    
    protected String generateRandomName() {
        return faker.name().firstName();
    }
}
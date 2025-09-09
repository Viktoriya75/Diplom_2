# Stellar Burgers API Tests

Automated API tests for Stellar Burgers service.

## Project Structure

```
stellar-burgers-api-tests/
├── src/test/
│   ├── java/ru/yandex/praktikum/
│   │   ├── api/          # API client classes
│   │   ├── config/       # Configuration
│   │   ├── model/        # POJO models for requests/responses
│   │   ├── steps/        # Test steps with Allure annotations
│   │   └── tests/        # Test classes
│   └── resources/
│       └── allure.properties
├── pom.xml
└── README.md
```

## Technologies

| Technology | Version | Purpose |
|------------|---------|---------|
| Java | 11 | Programming language |
| Maven | 3.9.0+ | Build tool |
| JUnit | 4.13.2 | Test framework |
| REST Assured | 5.3.2 | API testing library |
| Allure | 2.24.0 | Test reporting |
| Lombok | 1.18.30 | Code generation |
| Jackson | 2.15.3 | JSON serialization |

## Test Coverage

### User Creation Tests
- ✅ Create unique user successfully
- ✅ Cannot create duplicate user
- ✅ Cannot create user without email
- ✅ Cannot create user without password
- ✅ Cannot create user without name

### User Login Tests
- ✅ Login with existing user successfully
- ✅ Cannot login with incorrect email
- ✅ Cannot login with incorrect password
- ✅ Cannot login without email
- ✅ Cannot login without password

### Order Creation Tests
- ✅ Create order with authorization
- ✅ Create order without authorization
- ✅ Create order with ingredients
- ✅ Cannot create order without ingredients (with auth)
- ✅ Cannot create order without ingredients (without auth)
- ✅ Cannot create order with invalid ingredient hash

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd Diplom_2
```

2. Install dependencies:
```bash
mvn clean install
```

## Running Tests

### Run all tests:
```bash
mvn clean test
```

### Run specific test class:
```bash
mvn clean test -Dtest=CreateUserTest
mvn clean test -Dtest=LoginUserTest
mvn clean test -Dtest=CreateOrderTest
```

### Run tests with specific tag:
```bash
mvn clean test -Dgroups=smoke
mvn clean test -Dgroups=regression
```

## Allure Reporting

### Generate Allure report:
```bash
mvn allure:report
```

### Open Allure report:
```bash
mvn allure:serve
```

The report will automatically open in your default browser.

## API Documentation

API documentation is available at: [api-documentation.pdf](api-documentation.pdf)

Base URL: `https://stellarburgers.nomoreparties.site`

### Endpoints tested:
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/orders` - Order creation
- `DELETE /api/auth/user` - User deletion (cleanup)

## Best Practices Applied

✅ **POJO Models**: All requests/responses use proper Java objects with Lombok annotations  
✅ **No String Concatenation**: JSON is created via object serialization  
✅ **HTTP Status Constants**: Using Apache HttpStatus constants instead of magic numbers  
✅ **Clean Architecture**: Separation of concerns (models, API clients, steps, tests)  
✅ **Test Independence**: Each test manages its own test data and cleanup  
✅ **Allure Reporting**: Detailed test reports with steps and descriptions  
✅ **Proper Assertions**: Meaningful assertion messages for debugging  

## CI/CD

The project is ready for CI/CD integration. Example GitHub Actions workflow:

```yaml
name: API Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      - name: Run tests
        run: mvn clean test
      - name: Generate Allure Report
        run: mvn allure:report
      - name: Upload Allure Report
        uses: actions/upload-artifact@v3
        with:
          name: allure-report
          path: target/allure-report
```

## Author

Developed for Yandex Praktikum QA course - Sprint 7

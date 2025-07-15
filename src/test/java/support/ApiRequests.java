package support;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import data.User;

import static io.restassured.RestAssured.given;

// Класс для выполнения API-запросов к сервису аутентификации
public class ApiRequests {
    // Общая спецификация запроса с базовым URI, заголовками и логированием
    private static final RequestSpecification SPEC = new RequestSpecBuilder()
            .setBaseUri(Configuration.getHost()) // Базовый URL из конфигурации
            .addHeader("Content-type", "application/json") // Заголовок с типом контента JSON
            .setRelaxedHTTPSValidation() // Отключение строгой проверки HTTPS сертификатов
            .addFilter(new RequestLoggingFilter()) // Логирование запроса
            .addFilter(new ResponseLoggingFilter()) // Логирование ответа
            .addFilter(new ErrorLoggingFilter()) // Логирование ошибок
            .build();

    // Отправляет POST-запрос на регистрацию пользователя
    @Step("Отправить POST запрос /api/auth/register")
    public static Response sendPostRequestCreateUser(User user) {
        return given()
                .spec(SPEC)
                .body(user) // Тело запроса — объект User, сериализуется в JSON
                .post("/api/auth/register")
                .thenReturn();
    }

    // Отправляет POST-запрос на вход пользователя
    @Step("Отправить POST запрос /api/auth/login")
    public static Response sendPostRequestLoginUser(User user) {
        return given()
                .spec(SPEC)
                .body(user) // Тело запроса — объект User
                .post("/api/auth/login")
                .thenReturn();
    }

    // Отправляет DELETE-запрос на удаление пользователя с передачей токена авторизации
    @Step("Отправить DELETE запрос /api/auth/user")
    public static Response sendPostRequestDeleteUser(String accessToken) {
        return given()
                .spec(SPEC)
                .header("Authorization", accessToken) // Заголовок авторизации с токеном
                .delete("/api/auth/user")
                .thenReturn();
    }
}
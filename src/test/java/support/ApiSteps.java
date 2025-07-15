package support;

import io.qameta.allure.Step;
import data.Response;
import data.UserResponse;
import data.User;

import static org.junit.jupiter.api.Assertions.*;

// Класс с шагами для API-тестирования пользователей
public class ApiSteps {

    // Шаг: регистрация пользователя, возвращает объект с данными пользователя
    @Step("Регистрация пользователя")
    public static UserResponse createUser(User user) {
        io.restassured.response.Response response = ApiRequests.sendPostRequestCreateUser(user);
        response.then().statusCode(200); // Проверка кода ответа 200 OK
        UserResponse userResponse = response.body().as(UserResponse.class); // Десериализация тела ответа
        assertTrue(userResponse.isSuccess()); // Проверяем, что регистрация успешна
        return userResponse;
    }

    // Шаг: авторизация пользователя, возвращает объект с данными пользователя
    @Step("Авторизация пользователя")
    public static UserResponse loginUser(User user) {
        io.restassured.response.Response response = ApiRequests.sendPostRequestLoginUser(user);
        response.then().statusCode(200); // Проверка кода ответа 200 OK
        UserResponse userResponse = response.body().as(UserResponse.class); // Десериализация тела ответа
        assertTrue(userResponse.isSuccess()); // Проверяем, что авторизация успешна
        return userResponse;
    }

    // Шаг: удаление пользователя по токену доступа
    @Step("Удаление пользователя")
    public static void deleteUser(String accessToken) {
        io.restassured.response.Response response = ApiRequests.sendPostRequestDeleteUser(accessToken);
        response.then().statusCode(202); // Проверка кода ответа 202 Accepted
        Response resp = response.body().as(Response.class); // Десериализация тела ответа
        // Проверка успешности операции и корректности сообщения
        assertAll("Проверка полей ответа",
                () -> assertTrue(resp.isSuccess()),
                () -> assertEquals("User successfully removed", resp.getMessage())
        );
    }
}

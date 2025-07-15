import support.ApiSteps;
import io.qameta.allure.Step;
import data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screenform.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorizationTest extends AbstractTest {
    // Тестовый пользователь с фиксированными данными
    protected static final User USER_1 = new User("denis_user@yandex.ru", "password", "Денис");
    protected String accessToken1; // Токен доступа для аутентификации пользователя

    // Объекты экранов для взаимодействия с UI
    MainScreen objMainPage;
    AuthorizationScreen objLoginPage;
    UserProfileScreen objUserProfileScreen;
    RegistrationScreen objRegisterPage;
    PasswordRecoveryScreen objRecoverPage;

    @BeforeEach
    public void beforeEach() {
        // Создание тестового пользователя через API и получение accessToken
        accessToken1 = ApiSteps.createUser(USER_1).getAccessToken();

        // Инициализация экранов с текущим WebDriver
        objMainPage = new MainScreen(driver);
        objLoginPage = new AuthorizationScreen(driver);
        objUserProfileScreen = new UserProfileScreen(driver);
        objRegisterPage = new RegistrationScreen(driver);
        objRecoverPage = new PasswordRecoveryScreen(driver);

        objMainPage.waitForLoadPage();
    }

    @Test
    @DisplayName("Вход по кнопке \"Войти в аккаунт\"")
    public void loginForClickLoginButton() {
        objMainPage.clickLoginButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();
        checkLogin();
    }

    @Test
    @DisplayName("Вход через кнопку \"Личный кабинет\"")
    public void loginForClickAccountButton() {
        objMainPage.clickAccountButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();
        checkLogin();
    }

    @Test
    @DisplayName("Вход через кнопку на форме \"Регистрация\"")
    public void loginForRegisterPage() {
        objMainPage.clickLoginButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.clickRegisterButton();
        objRegisterPage.waitForLoadPage();

        objRegisterPage.clickLoginButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();
        checkLogin();
    }

    @Test
    @DisplayName("Вход через кнопку на форме \"Восстановление пароля\"")
    public void loginForRecoverPage() {
        objMainPage.clickLoginButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.clickRecoverButton();
        objRecoverPage.waitForLoadPage();

        objRecoverPage.clickLoginButton();
        objLoginPage.waitForLoadPage();

        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();
        checkLogin();
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Личный Кабинет\" авторизованного пользователя")
    public void checkClickAccountButton() {
        // Первый клик ведёт на форму авторизации
        objMainPage.clickAccountButton();
        objLoginPage.waitForLoadPage();

        // Авторизация пользователя
        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();

        // Второй клик ведёт в профиль пользователя
        checkLogin();
    }

    @Step("Проверка авторизации")
    private void checkLogin() {
        // Переход в профиль по кнопке "Личный Кабинет"
        objMainPage.clickAccountButton();
        objUserProfileScreen.waitForLoadPage();

        // Проверка, что данные профиля совпадают с данными пользователя
        assertAll("Проверка полей профиля",
                () -> assertEquals(USER_1.getName(), objUserProfileScreen.getNameField(),
                        "Неверное значение поля name!"),
                () -> assertEquals(USER_1.getEmail(), objUserProfileScreen.getEmailField(),
                        "Неверное значение поля email!")
        );
    }

    @AfterEach
    public void afterEach() {
        // Удаление тестового пользователя через API
        ApiSteps.deleteUser(accessToken1);
    }
}
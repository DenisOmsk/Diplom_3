import support.ApiSteps;
import data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screenform.AuthorizationScreen;
import screenform.MainScreen;
import screenform.RegistrationScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends AbstractTest {
    // Экраны для взаимодействия с UI
    MainScreen objMainPage;
    AuthorizationScreen objLoginPage;
    RegistrationScreen objRegisterPage;

    // Тестовый пользователь для регистрации
    protected static final User USER_1 = new User("denis_user@yandex.ru", "password", "Денис");

    // Токен доступа для последующего удаления пользователя через API
    protected String accessToken;

    @BeforeEach
    public void beforeEach() {
        // Инициализация экранов с текущим драйвером
        objMainPage = new MainScreen(driver);
        objLoginPage = new AuthorizationScreen(driver);
        objRegisterPage = new RegistrationScreen(driver);

        // Ожидание полной загрузки главной страницы
        objMainPage.waitForLoadPage();

        // Переход на форму авторизации
        objMainPage.clickAccountButton();
        objLoginPage.waitForLoadPage();

        // Переход на форму регистрации
        objLoginPage.clickRegisterButton();
        objRegisterPage.waitForLoadPage();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void registerUser() {
        // Заполнение и отправка формы регистрации
        objRegisterPage.registerUser(USER_1.getName(),
                USER_1.getEmail(),
                USER_1.getPassword());

        // Ожидание загрузки страницы входа после регистрации
        objLoginPage.waitForLoadPage();

        // Получение accessToken через API для подтверждения успешной авторизации
        accessToken = ApiSteps.loginUser(USER_1).getAccessToken();
    }

    @Test
    @DisplayName("Неуспешная регистрация с некорректным паролем")
    public void registerFailedUserWithWrongPassword() {
        // Попытка регистрации с слишком коротким паролем
        objRegisterPage.registerUser(USER_1.getName(),
                USER_1.getEmail(),
                "un");

        // Проверка наличия сообщения об ошибке пароля
        assertTrue(objRegisterPage.existPasswordMessage());

        // Ожидание загрузки формы регистрации (остаемся на ней)
        objRegisterPage.waitForLoadPage();

        // Проверка, что URL соответствует странице регистрации
        assertEquals(hostTest + "/register", driver.getCurrentUrl(),
                "Открыта не форма \"Регистрация\"!");
    }

    @AfterEach
    public void tearDown() {
        // Удаление тестового пользователя через API, если был создан accessToken
        if (accessToken != null) {
            ApiSteps.deleteUser(accessToken);
            accessToken = null;
        }
        // Закрываем браузер и завершаем сессию WebDriver
        if (driver != null) {
            driver.quit();
        }
    }
}


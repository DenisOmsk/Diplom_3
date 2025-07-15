import support.ApiSteps;
import data.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screenform.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserProfileTest extends AbstractTest {
    // Тестовый пользователь с фиксированными данными
    protected static final User USER_1 = new User("denis_user@yandex.ru", "password", "Денис");
    protected String accessToken; // Токен доступа для аутентификации пользователя

    // Объекты экранов для взаимодействия с UI
    MainScreen objMainPage;
    AuthorizationScreen objLoginPage;
    UserProfileScreen objUserProfileScreen;

    @BeforeEach
    public void beforeEach() {
        // Создание тестового пользователя через API и получение accessToken
        accessToken = ApiSteps.createUser(USER_1).getAccessToken();

        // Инициализация экранов с текущим WebDriver
        objMainPage = new MainScreen(driver);
        objLoginPage = new AuthorizationScreen(driver);
        objUserProfileScreen = new UserProfileScreen(driver);

        objMainPage.waitForLoadPage();

        // Нажатие на кнопку "Личный Кабинет" ведёт на страницу авторизации
        objMainPage.clickAccountButton();
        objLoginPage.waitForLoadPage();

        // Авторизация пользователя
        objLoginPage.loginUser(USER_1.getEmail(), USER_1.getPassword());
        objMainPage.waitForLoadPage();

        // Переход в профиль пользователя по кнопке "Личный Кабинет"
        objMainPage.clickAccountButton();
        objUserProfileScreen.waitForLoadPage();
    }

    @Test
    @DisplayName("Нажатие на логотип \"Stellar Burgers\"")
    public void checkClickLogoStellarBurgers() {
        objUserProfileScreen.clickLogoStellarBurgers();

        objMainPage.waitForLoadPage();
        assertEquals(hostTest + "/", driver.getCurrentUrl(),
                "Открыта не главная страница \"Stellar Burgers\"!");
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Конструктор\"")
    public void checkClickConstructorButton() {
        objUserProfileScreen.clickConstructorButton();

        objMainPage.waitForLoadPage();
        assertEquals(hostTest + "/", driver.getCurrentUrl(),
                "Открыта не главная страница \"Stellar Burgers\"!");
    }

    @Test
    @DisplayName("Нажатие на кнопку \"Выйти\"")
    public void checkClickExitButton() {
        objUserProfileScreen.clickExitButton();
        objLoginPage.waitForLoadPage();

        // После выхода при нажатии "Личный Кабинет" открывается форма авторизации
        objMainPage.clickAccountButton();
        objLoginPage.waitForLoadPage();

        assertEquals(hostTest + "/login", driver.getCurrentUrl(),
                "Открыта не форма авторизации \"Вход\"!");
    }

    @AfterEach
    public void afterEach() {
        // Удаление тестового пользователя через API
        ApiSteps.deleteUser(accessToken);
    }
}
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import screenform.AuthorizationScreen;
import screenform.MainScreen;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainScreenTest extends AbstractTest {

    // Экраны для взаимодействия с элементами интерфейса
    MainScreen objMainPage;
    AuthorizationScreen objLoginPage;

    @BeforeEach
    public void beforeEach() {
        // Инициализация экранов с текущим драйвером
        objMainPage = new MainScreen(driver);
        objLoginPage = new AuthorizationScreen(driver);

        // Ожидание полной загрузки главной страницы
        objMainPage.waitForLoadPage();
    }

    @Test
    @DisplayName("Переход на страницу входа при нажатии на \"Личный Кабинет\" без авторизации")
    public void checkClickAccountButtonWithoutAuth() {
        // Нажатие кнопки "Личный Кабинет" на главной странице
        objMainPage.clickAccountButton();

        // Ожидание загрузки формы входа
        objLoginPage.waitForLoadPage();

        // Проверка, что текущий URL соответствует странице логина
        assertEquals(hostTest + "/login", driver.getCurrentUrl(),
                "Открыта не форма авторизации \"Вход\"!");
    }

    @Test
    @DisplayName("Нажатие на раздел \"Булки\"")
    public void checkClickConstructorButton() {
        // Сначала переключаемся на раздел "Соусы" для смены активного раздела
        objMainPage.clickSauceSection();

        // Затем кликаем на раздел "Булки"
        objMainPage.clickBunSection();

        // Явное ожидание, что раздел "Булки" стал выбранным
        objMainPage.waitForSectionToBeSelected("Булки");

        // Проверяем, что активный раздел действительно "Булки"
        assertEquals("Булки", objMainPage.getSelectedSection(),
                "Выбран некорректный раздел");
    }

    @Test
    @DisplayName("Нажатие на раздел \"Соусы\"")
    public void checkClickSauceSection() {
        // Кликаем на раздел "Соусы"
        objMainPage.clickSauceSection();

        // Ожидаем, что раздел "Соусы" стал активным
        objMainPage.waitForSectionToBeSelected("Соусы");

        // Проверяем, что выбран раздел "Соусы"
        assertEquals("Соусы", objMainPage.getSelectedSection(),
                "Выбран некорректный раздел");
    }

    @Test
    @DisplayName("Нажатие на раздел \"Начинки\"")
    public void checkClickFillingSection() {
        // Нажатие на раздел "Начинки"
        objMainPage.clickFillingSection();

        // Ожидаем, что раздел "Начинки" стал активным
        objMainPage.waitForSectionToBeSelected("Начинки");

        // Проверяем, что выбран раздел "Начинки"
        assertEquals("Начинки", objMainPage.getSelectedSection(),
                "Выбран некорректный раздел");
    }
}

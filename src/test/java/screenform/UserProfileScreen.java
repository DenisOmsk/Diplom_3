package screenform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Экранная форма "Личный Кабинет"
public class UserProfileScreen extends BaseScreen {

    // Раздел "Профиль"
    private final By profileSection = By.xpath(".//*[text()='Профиль']"); // Локатор раздела "Профиль"
    // Значение поля "Имя"
    private final By nameField = By.xpath(".//input[@name='Name' and @disabled]"); // Локатор поля "Имя" (только для чтения)
    // Значение поля "Email"
    private final By emailField = By.xpath(".//input[@name='name' and @disabled]"); // Локатор поля "Email" (только для чтения)
    // Кнопка "Выход"
    private final By exitButton = By.xpath(".//button[text()='Выход']"); // Локатор кнопки "Выход"

    public UserProfileScreen(WebDriver driver) {
        super(driver); // Вызов конструктора базового класса с WebDriver
    }

    // Метод ожидания загрузки экранной формы: проверяем видимость раздела "Профиль"
    public void waitForLoadPage() {
        // Ждем до 5 секунд появления элемента раздела "Профиль"
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(profileSection));
    }

    // Нажатие на кнопку "Выход"
    public void clickExitButton() {
        driver.findElement(exitButton).click(); // Находим и кликаем кнопку "Выход"
    }

    // Метод получения значения поля "Имя"
    public String getNameField() {
        return driver.findElement(nameField).getDomAttribute("value"); // Получаем атрибут value поля "Имя"
    }

    // Метод получения значения поля "Email"
    public String getEmailField() {
        return driver.findElement(emailField).getDomAttribute("value"); // Получаем атрибут value поля "Email"
    }
}

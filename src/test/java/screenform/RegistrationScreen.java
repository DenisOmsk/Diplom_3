package screenform;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

// Представляет форму "Регистрация"
public class RegistrationScreen extends BaseScreen {

    // Локаторы элементов формы регистрации
    private final By registerHeader = By.xpath(".//div[@class='Auth_login__3hAey']/h2[text()='Регистрация']");
    private final By nameField = By.xpath(".//label[text()='Имя']/parent::div/input[@name='name']");
    private final By emailField = By.xpath(".//label[text()='Email']/parent::div/input[@name='name']");
    private final By passwordField = By.xpath(".//input[@name='Пароль']");
    private final By passwordMessage = By.xpath(".//*[text()='Некорректный пароль']");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By loginButton = By.xpath(".//*[text()='Войти']");

    private final WebDriverWait wait;

    public RegistrationScreen(WebDriver driver) {
        super(driver);
        // Явное ожидание с таймаутом 10 секунд
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Ожидаем загрузки страницы — видимость заголовка "Регистрация"
    public void waitForLoadPage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(registerHeader));
            System.out.println("Страница регистрации загружена: заголовок найден");
            takeScreenshot("register_page_loaded.png");
        } catch (TimeoutException e) {
            System.err.println("Не удалось дождаться загрузки страницы регистрации: " + e.getMessage());
            takeScreenshot("register_page_load_failed.png");
            throw e;
        }
    }

    // Универсальный метод ожидания видимости элемента с логированием и скриншотом
    private WebElement waitForVisibility(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("Элемент найден и видим: " + locator);
            return element;
        } catch (TimeoutException e) {
            System.err.println("Не удалось дождаться видимости элемента: " + locator);
            takeScreenshot("element_visibility_failed.png");
            throw e;
        }
    }

    // Ввод имени
    public void setName(String name) {
        WebElement element = waitForVisibility(nameField);
        element.clear();
        element.sendKeys(name);
    }

    // Ввод email
    public void setEmail(String email) {
        WebElement element = waitForVisibility(emailField);
        element.clear();
        element.sendKeys(email);
    }

    // Ввод пароля
    public void setPassword(String password) {
        WebElement element = waitForVisibility(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    // Проверка наличия сообщения об ошибке пароля
    public boolean existPasswordMessage() {
        try {
            return driver.findElement(passwordMessage).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Клик по кнопке "Зарегистрироваться"
    public void clickRegisterButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        element.click();
    }

    // Клик по кнопке "Войти"
    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }

    @Step("Регистрация пользователя")
    public void registerUser(String name, String email, String password) {
        waitForLoadPage();
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    // Вспомогательный метод для создания скриншотов
    private void takeScreenshot(String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fileName));
            System.out.println("Скриншот сохранён: " + fileName);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить скриншот: " + e.getMessage());
        }
    }
}


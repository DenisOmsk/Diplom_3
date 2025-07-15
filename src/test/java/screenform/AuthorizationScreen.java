package screenform;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Класс, представляющий форму авторизации "Вход"
public class AuthorizationScreen extends BaseScreen {

    // Заголовок экранной формы с текстом "Вход"
    private final By loginHeader = By.xpath(".//div[@class='Auth_login__3hAey']/h2[text()='Вход']");
    // Поле для ввода Email
    private final By emailField = By.xpath(".//input[@name='name']");
    // Поле для ввода Пароля
    private final By passwordField = By.xpath(".//input[@name='Пароль']");
    // Кнопка для входа в систему
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    // Кнопка перехода к регистрации
    private final By registerButton = By.xpath(".//*[text()='Зарегистрироваться']");
    // Кнопка для восстановления пароля
    private final By recoverButton = By.xpath(".//*[text()='Восстановить пароль']");

    public AuthorizationScreen(WebDriver driver) {
        super(driver);
    }

    // Ожидает загрузки экранной формы, проверяя видимость заголовка "Вход"
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginHeader));
    }

    // Вводит указанный email в соответствующее поле
    public void setEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    // Вводит указанный пароль в соответствующее поле
    public void setPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    // Нажатие на кнопку "Войти"
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Нажатие на кнопку "Зарегистрироваться"
    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    // Нажатие на кнопку "Восстановить пароль"
    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

    @Step("Авторизация пользователя")
    public void loginUser(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }
}

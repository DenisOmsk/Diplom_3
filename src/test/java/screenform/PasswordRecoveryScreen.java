package screenform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Представляет форму "Восстановление пароля"
public class PasswordRecoveryScreen extends BaseScreen {

    // Заголовок формы "Восстановление пароля"
    private final By recoverHeader = By.xpath(".//div[@class='Auth_login__3hAey']/h2[text()='Восстановление пароля']");
    // Кнопка "Войти"
    private final By loginButton = By.xpath(".//*[text()='Войти']");

    public PasswordRecoveryScreen(WebDriver driver) {
        super(driver);
    }

    // Ожидает загрузки формы, проверяя видимость заголовка "Восстановление пароля"
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(recoverHeader));
    }

    // Нажатие на кнопку "Войти"
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
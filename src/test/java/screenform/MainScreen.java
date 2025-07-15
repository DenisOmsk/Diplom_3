package screenform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Представляет главную страницу приложения
public class MainScreen extends BaseScreen {

    // Заголовок страницы с текстом "Соберите бургер"
    private final By headerSection = By.xpath(".//section/h1[text()='Соберите бургер']");
    // Ссылка на раздел "Булки"
    private final By bunSection = By.xpath(".//span[text()='Булки']");
    // Ссылка на раздел "Соусы"
    private final By sauceSection = By.xpath(".//span[text()='Соусы']");
    // Ссылка на раздел "Начинки"
    private final By fillingSection = By.xpath(".//span[text()='Начинки']");
    // Элемент, отображающий текущий выбранный раздел
    private final By selectedSection = By.xpath("//div[contains(@class,'tab_tab_type_current__2BEPc')]/span");
    // Кнопка для входа в аккаунт
    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");

    public MainScreen(WebDriver driver) {
        super(driver);
    }

    // Ожидает загрузки страницы, проверяя видимость заголовка
    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(headerSection));
    }

    public void waitForSectionToBeSelected(String sectionName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(driver -> getSelectedSection().equals(sectionName));
    }

    // Нажатие на раздел "Булки"
    public void clickBunSection() {
        driver.findElement(bunSection).click();
    }

    // Нажатие на раздел "Соусы"
    public void clickSauceSection() {
        driver.findElement(sauceSection).click();
    }

    // Нажатие на раздел "Начинки"
    public void clickFillingSection() {
        driver.findElement(fillingSection).click();
    }

    // Возвращает название текущего активного раздела
    public String getSelectedSection() {
        return driver.findElement(selectedSection).getText();
    }

    // Нажатие на кнопку "Войти в аккаунт"
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }
}
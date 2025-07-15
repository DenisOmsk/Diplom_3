package screenform;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

// Абстрактный базовый класс для всех экранов приложения
abstract public class BaseScreen {
    protected WebDriver driver; // Экземпляр WebDriver для взаимодействия с браузером

    // Локатор кнопки "Конструктор"
    private final By constructorButton = By.xpath(".//*[text()='Конструктор']");
    // Локатор логотипа "Stellar Burgers"
    private final By logoStellarBurgers = By.className("AppHeader_header__logo__2D0X2");
    // Локатор кнопки "Личный Кабинет"
    private final By accountButton = By.xpath(".//*[text()='Личный Кабинет']");

    public BaseScreen(WebDriver driver) {
        this.driver = driver; // Инициализация WebDriver
    }

    // Нажатие на логотип "Stellar Burgers"
    public void clickLogoStellarBurgers() {
        driver.findElement(logoStellarBurgers).click();
    }

    // Нажатие на кнопку "Конструктор"
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    // Нажатие на кнопку "Личный Кабинет"
    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }
}


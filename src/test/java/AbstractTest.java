import support.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

abstract public class AbstractTest {
    protected WebDriver driver;       // Экземпляр WebDriver для управления браузером
    protected static String hostTest; // URL тестового приложения

    @BeforeAll
    public static void before() {
        hostTest = Configuration.getHost(); // Загрузка URL тестового приложения из конфигурации
    }

    @BeforeEach
    public void setUp() {
        driver = Configuration.getWebDriver(); // Создание WebDriver на основе настроек
        driver.get(hostTest);                   // Открытие страницы тестового приложения
    }

    @AfterEach
    public void tearDown() {
        driver.quit(); // Закрытие браузера после выполнения каждого теста
    }
}
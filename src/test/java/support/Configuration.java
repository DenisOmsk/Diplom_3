package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Класс для загрузки конфигурации и создания WebDriver для тестирования
public class Configuration {
    // Объект для хранения параметров конфигурации
    private static final Properties properties = new Properties();
    // Значение по умолчанию для хоста тестирования
    private static final String HOST_TEST = "https://stellarburgers.nomoreparties.site";

    // Статический блок инициализации для загрузки конфигурационного файла
    static {
        try {
            InputStream inputStream = Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream("config.properties");
            if (inputStream == null) {
                System.out.println("Не удалось вычитать конфиг");
            } else {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке конфигурационного файла", e);
        }
    }

    // Получает URL хоста для тестирования из конфигурации или возвращает значение по умолчанию */
    public static String getHost() {
        return properties.getProperty("hostTest", HOST_TEST);
    }

    // Создаёт и возвращает WebDriver в зависимости от конфигурации браузера */
    public static WebDriver getWebDriver() {
        String browserName = properties.getProperty("browser");
        if (browserName == null) {
            browserName = "";
        }
        switch (browserName.toLowerCase()) {
            case "firefox":
                return new FirefoxDriver();

            case "yandex":
                // Устанавливаем путь к драйверу Yandex Browser
                System.setProperty("webdriver.chrome.driver",
                        "C:\\cygwin64\\home\\dsokolov\\Diplom\\Diplom_3\\src\\test\\resources\\yandexdriver.exe");
                ChromeOptions yandexOptions = new ChromeOptions();
                // Указываем путь к исполняемому файлу Yandex Browser
                yandexOptions.setBinary("C:\\Users\\DSokolov\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
                return new ChromeDriver(yandexOptions);

            case "edge":
                // Устанавливаем драйвер для Microsoft Edge (если необходимо, путь можно добавить)
                return new EdgeDriver();

            case "chrome":
                // Явно указано использование ChromeDriver без опций
                return new ChromeDriver();

            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browserName +
                        ". Поддерживаемые браузеры: chrome, firefox, yandex, edge.");
        }
    }
}

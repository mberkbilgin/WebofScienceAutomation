package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class WebofScienceSearch {

    private WebDriver driver;
    private FluentWait<WebDriver> wait;

    public WebofScienceSearch() {
        // Headless ChromeDriver ayarları
        System.setProperty("webdriver.chrome.driver", "/Users/mbb/Documents/Programming/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");  // Headless mod aktif
        options.addArguments("--disable-gpu");  // GPU'yu devre dışı bırak
        options.addArguments("--window-size=1920,1080");  // Tarayıcı boyutunu ayarla (gerekli olabilir)
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        this.driver = new ChromeDriver(options);

        // FluentWait ayarları optimize edildi
        this.wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))  // Maksimum 10 saniye bekle (genel bekleme için)
                .pollingEvery(Duration.ofMillis(500))  // 500 ms'de bir kontrol et
                .ignoring(Exception.class);  // Hata durumunda devam et
    }

    public String searchByDOIAndJournal(String doi, String journalName) {
        try {
            driver.get("https://www.webofscience.com/wos/woscc/basic-search");

            // Çerez uyarısını kapatmak için maksimum 2 saniye bekle
            try {
                // Çerez uyarısı varsa maksimum 2 saniye bekle ve tıklamaya çalış
                FluentWait<WebDriver> shortWait = new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(2))  // Maksimum 2 saniye bekle
                        .pollingEvery(Duration.ofMillis(500))  // 500 ms'de bir kontrol et
                        .ignoring(Exception.class);  // Hata durumunda devam et

                WebElement acceptCookiesButton = shortWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='onetrust-close-btn-container']/button")));
                acceptCookiesButton.click();
            } catch (Exception e) {
                // Eğer çerez uyarısı bulunmazsa, devam et
            }

            // Dropdown menüsüne tıkla
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//wos-select[@data-ta='search-field-dropdown']")));
            dropdown.click();

            // DOI seçeneğine tıkla
            WebElement doiOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='DOI']")));
            doiOption.click();

            // DOI arama alanını bul ve içeriği temizle
            WebElement doiInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='search-option']")));
            doiInput.clear();  // Arama kutusunu temizle
            doiInput.sendKeys(doi);  // DOI verisini gir
            doiInput.sendKeys(Keys.ENTER);  // Aramayı gerçekleştir

            // Arama sonuçlarını bekle ve dergiye tıkla
            WebElement journalResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + journalName.toLowerCase() + "']")));
            journalResult.click();

            // İlk "menuitem"e tıkla
            WebElement firstMenuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@role='menuitem'])[1]")));
            firstMenuItem.click();

            // Quartile bilgisini al
            WebElement quartileElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='Sidenav-1-JCR-quartile_0']")));
            return quartileElement.getText();

        } catch (Exception e) {
            e.printStackTrace();
            return "not found";
        }
    }

    public void close() {
        driver.quit();
    }
}

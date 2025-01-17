package base;

import com.yakuperenermurat.utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

/**
 * Tüm test senaryoları için temel ayarları yöneten sınıf.
 */
public class BaseTest {

    // driver değişkenini protected yaparak miras alan sınıfların erişmesini sağlıyoruz.
    protected WebDriver driver;

    /**
     * Test öncesi tarayıcı başlatma ve ayarları yükleme.
     */
    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Sayfa yüklenmesi için bekleme süresi
        driver.get("https://www.lcw.com/"); // Test edilecek sayfaya yönlendirme
    }

    /**
     * Test sonrası tarayıcıyı kapatma.
     */
    @AfterMethod
    public void teardown() {
        DriverManager.quitDriver();
    }
}

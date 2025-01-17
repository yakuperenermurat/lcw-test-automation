package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.LoginPage;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

/**
 * Login testi senaryosu.
 */
public class LoginTest extends BaseTest {

    /**
     * Kullanıcının başarılı bir şekilde giriş yapmasını test eder.
     */
    @Test
    @Feature("Login Testi")
    @Description("Geçerli email ve şifre ile giriş yapma testi")
    public void validLoginTest() {
        // Sayfa nesnesi oluşturuluyor
        LoginPage loginPage = new LoginPage(driver);

        // Test adımları
        loginPage.loginSteps("test@example.com", "password123");
    }
}

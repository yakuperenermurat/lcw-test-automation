package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.CategoryPage;
import com.yakuperenermurat.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

/**
 * Kategori Navigasyonu Testi: Kullanıcının login sonrası kategori seçimi.
 */
public class CategoryNavigationTest extends BaseTest {

    @Test(priority = 1)
    @Feature("Kategori Navigasyonu Testi")
    @Description("Login sonrası Çocuk & Bebek > Kız Çocuk > Mont ve Kaban testi")
    public void categoryNavigationTest() {
        // **Login işlemi gerçekleştiriliyor.**
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSteps("test@example.com", "password123");

        // **Kategori Sayfası nesnesi oluşturuluyor.**
        CategoryPage categoryPage = new CategoryPage(driver);

        // **Adımlar: Çocuk & Bebek > Kız Çocuk > Mont ve Kaban**
        categoryPage.clickCocukBebekCategory();
        categoryPage.clickKizCocukCategory();
        categoryPage.clickMontVeKaban();

        // **Sayfa Doğrulama**
        Assert.assertTrue(categoryPage.isCorrectPageLoaded(), "Sayfa doğru açılmadı.");
    }
}

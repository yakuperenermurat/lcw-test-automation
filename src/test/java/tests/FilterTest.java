package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.FilterPage;
import com.yakuperenermurat.pages.LoginPage;
import com.yakuperenermurat.pages.CategoryPage;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;

/**
 * Beden ve Renk Filtreleme Testi
 */
public class FilterTest extends BaseTest {

    @Test(priority = 3)
    @Feature("Filtreleme Testi")
    @Description("Beden ve renk filtresini test eder.")
    public void filterTest() {
        // Login işlemi
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSteps("test@example.com", "password123");

        // Kategoriye git
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.categorySteps();

        // Filtreleme işlemleri
        FilterPage filterPage = new FilterPage(driver);
        filterPage.applyAllFilters();

        // Doğru sayfa yüklenme kontrolü
        Assert.assertTrue(driver.getCurrentUrl().contains("kiz-cocuk-dis-giyim"),
                "Filtreleme sonrası sayfa yanlış yüklendi!");
    }
}

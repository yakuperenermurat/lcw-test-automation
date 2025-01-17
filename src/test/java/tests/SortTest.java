package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.FilterPage;
import com.yakuperenermurat.pages.LoginPage;
import com.yakuperenermurat.pages.CategoryPage;
import com.yakuperenermurat.pages.SortPage;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;

public class SortTest extends BaseTest {

    @Test(priority = 4)
    @Feature("Sıralama Testi")
    @Description("Sıralama 'En çok satanlar' seçeneği ile test edilir.")
    public void sortTest() {
        // Giriş yap
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSteps("test@example.com", "password123");

        // Kategoriye git
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.categorySteps();

        // Filtreleme
        FilterPage filterPage = new FilterPage(driver);
        filterPage.applyAllFilters();

        // Sıralama testi
        SortPage sortPage = new SortPage(driver);
        sortPage.sortSteps();

        // Doğru sıralama kontrolü
        Assert.assertTrue(driver.getCurrentUrl().contains("en-cok-satanlar"),
                "Sıralama sonrası sayfa yanlış yüklendi!");
    }
}

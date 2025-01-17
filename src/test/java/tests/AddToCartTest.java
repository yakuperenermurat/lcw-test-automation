package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.*;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

public class AddToCartTest extends BaseTest {

    @Test(priority = 5)
    @Feature("Ürünü Sepete Ekleme Testi")
    @Description("Kız Çocuk Mont & Kaban kategorisinden 4. ürünü seçer, ürün detayına gider, stokta olan bir bedeni seçer ve sepete ekler.")
    public void addToCartTest() {
        // Giriş işlemi
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginSteps("test@example.com", "password123");

        // Kategori testi
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.categorySteps();

        // Filtreleme
        FilterPage filterPage = new FilterPage(driver);
        filterPage.applyAllFilters();

        // Sıralama
        SortPage sortPage = new SortPage(driver);
        sortPage.sortSteps();

        // 4. Ürün ve Stokta Olan Beden Seçimi
        ProductSelectionPage productPage = new ProductSelectionPage(driver);
        productPage.productSelectionSteps();
    }
}

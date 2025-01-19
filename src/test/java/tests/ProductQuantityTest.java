package tests;

import base.BaseTest;
import com.yakuperenermurat.pages.*;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;

public class ProductQuantityTest extends BaseTest {

    @Test(priority = 7)
    @Feature("Ürün Adedi Güncelleme Testi")
    @Description("Sepetteki ürün adedini artırma ve azaltma testi.")
    public void productQuantityUpdateTest() {
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

        // Ürünün detay sayfasındaki bilgileri al
        String selectedProductName = productPage.getSelectedProductName();
        String selectedProductQuantity = "1";

        // Sepete ekleme sonrası bekleme
        productPage.addToCart();

        // Sepet kontrolü
        CartPage cartPage = new CartPage(driver);
        cartPage.goToCart();

        // Doğrulamalar
        boolean isNameCorrect = cartPage.verifyProductName(selectedProductName);
        boolean isQuantityCorrect = cartPage.verifyProductQuantity(selectedProductQuantity);
        boolean isPriceCorrect = cartPage.verifyPrices();
        boolean isColorCorrect = cartPage.verifyProductColor(selectedProductName);


        // Test sonuçlarını doğrula
        Assert.assertTrue(isNameCorrect, "❌ Ürün adı eşleşmiyor!");
        Assert.assertTrue(isColorCorrect, "❌ Ürün rengi eşleşmiyor!");
        Assert.assertTrue(isQuantityCorrect, "❌ Ürün adedi eşleşmiyor!");
        Assert.assertTrue(isPriceCorrect, "❌ Sepet fiyatları eşleşmiyor!");

        // Ürün adedini artır ve doğrula
        cartPage.increaseProductQuantity();
        Assert.assertTrue(cartPage.verifyProductQuantity("2"), "❌ Ürün adedi artırılamadı!");

        // Ürün adedini azalt ve doğrula
        cartPage.decreaseProductQuantity();
        Assert.assertTrue(cartPage.verifyProductQuantity("1"), "❌ Ürün adedi azaltılamadı!");
    }
}

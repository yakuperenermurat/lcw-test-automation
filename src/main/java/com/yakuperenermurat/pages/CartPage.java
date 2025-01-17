package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[normalize-space()='Sepetim']")
    private WebElement sepetimButton;

    @FindBy(xpath = "//span[@class='rd-cart-item-code']") // Sepetteki ürün adı (Örn: "Mont")
    private WebElement productNameInCart;

    @FindBy(xpath = "//strong[normalize-space()='Koyu Bej']") // Sepetteki ürün rengi
    private WebElement productColorInCart;

    @FindBy(xpath = "//input[@value='1']") // Sepetteki ürün adedi
    private WebElement productQuantityInCart;

    @FindBy(xpath = "//span[@class='rd-cart-item-price mb-15']") // Sepetteki ürün fiyatı
    private WebElement productPriceInCart;

    @FindBy(xpath = "(//span[@class='pull-right'][normalize-space()='1.499,99 TL'])[1]") // Ödeme adımındaki toplam fiyat
    private WebElement totalPriceInCheckout;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @Step("Sepetim ekranına gidiliyor ve sayfanın yüklenmesi bekleniyor.")
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(sepetimButton)).click();

        // Sepet açıldığında ürünlerin yüklenmesini bekleyelim
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='rd-cart-item-code']")));
    }

    @Step("Sepetteki ürün adı doğrulanıyor.")
    public boolean verifyProductName(String expectedFullName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productNameInCart));
        String productInCart = element.getText();

        // Ürün detay sayfasındaki isim içinde sepetteki ürün adı geçiyor mu?
        return expectedFullName.contains(productInCart);
    }

    @Step("Sepetteki ürün rengi doğrulanıyor.")
    public boolean verifyProductColor(String expectedColor) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productColorInCart));
        return element.getText().equalsIgnoreCase(expectedColor);
    }

    @Step("Sepetteki ürün adedi doğrulanıyor.")
    public boolean verifyProductQuantity(String expectedQuantity) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productQuantityInCart));
        return element.getAttribute("value").equals(expectedQuantity);
    }

    @Step("Sepetteki fiyat doğrulanıyor.")
    public boolean verifyPrices() {
        String priceInCart = wait.until(ExpectedConditions.visibilityOf(productPriceInCart)).getText();
        String totalPrice = wait.until(ExpectedConditions.visibilityOf(totalPriceInCheckout)).getText();
        return priceInCart.equals(totalPrice);
    }
}

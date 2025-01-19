package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductSelectionPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @FindBy(xpath = "//div[@class='product-grid']/div[4]") // 4. ürünü seçiyoruz.
    private WebElement fourthProduct;

    @FindBy(xpath = "//h1[@class='product-title']")
    private WebElement productTitle;

    @FindBy(xpath = "//label[contains(text(), 'Bej') or contains(text(), 'Renk')]")
    private WebElement productColor;

    @FindBy(xpath = "//button[contains(text(),'SEPETE EKLE')]")
    private WebElement addToCartButton;

    @FindBy(xpath = "//button[contains(text(),'Yaş') and not(contains(@class,'disabled'))]")
    private List<WebElement> availableSizes;

    @FindBy(xpath = "//span[normalize-space()='Sepetim']/following-sibling::span")
    private WebElement cartItemCount;

    @FindBy(xpath = "//div[contains(@class, 'evam-first-screenControl')]")
    private List<WebElement> popups; // Popup'lar varsa yakalamak için

    public ProductSelectionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @Step("4. sıradaki ürüne tıklanıyor ve detay sayfasına gidiliyor.")
    public void selectFourthProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(fourthProduct)).click();
        waitUntilProductDetailPageLoads();
    }

    @Step("Ürün detay sayfasının tamamen yüklenmesi bekleniyor.")
    private void waitUntilProductDetailPageLoads() {
        wait.until(webDriver -> jsExecutor.executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Yaş')]")));
    }

    @Step("Popup veya engelleyici elementleri kapatıyoruz.")
    private void closePopupsIfExist() {
        try {
            if (!popups.isEmpty()) {
                for (WebElement popup : popups) {
                    jsExecutor.executeScript("arguments[0].style.display='none';", popup);
                }
            }
        } catch (Exception ignored) {
        }
    }

    @Step("Stokta olan ilk uygun beden seçiliyor.")
    public void selectAvailableSize() {
        waitUntilProductDetailPageLoads();
        closePopupsIfExist(); // Öncelikle engelleyici elementleri gizleyelim.

        availableSizes = driver.findElements(By.xpath("//button[contains(text(),'Yaş') and not(contains(@class,'disabled'))]"));

        if (!availableSizes.isEmpty()) {
            WebElement firstAvailableSize = availableSizes.get(0);
            scrollToElement(firstAvailableSize); // Scroll yap
            wait.until(ExpectedConditions.elementToBeClickable(firstAvailableSize));
            clickWithJS(firstAvailableSize); // JavaScript ile tıkla
            waitForButtonToBeEnabled();
        } else {
            throw new NoSuchElementException("Stokta mevcut beden bulunamadı.");
        }
    }

    @Step("Sepete ekle butonunun aktif olması bekleniyor.")
    private void waitForButtonToBeEnabled() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
    }

    @Step("Ürün sepete ekleniyor ve sepette 1 ürün olup olmadığı kontrol ediliyor.")
    public void addToCart() {
        waitForButtonToBeEnabled();
        clickWithJS(addToCartButton); // Sepete ekleme için JavaScript click kullan
        wait.until(ExpectedConditions.textToBePresentInElement(cartItemCount, "1"));
    }

    @Step("Elemente scroll yapılıyor.")
    private void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    @Step("Elemente JavaScript ile tıklanıyor.")
    private void clickWithJS(WebElement element) {
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    @Step("Ürün adı alınıyor.")
    public String getSelectedProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productTitle)).getText();
    }

    @Step("Ürün rengi alınıyor.")
    public String getSelectedProductColor() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(productColor)).getText();
        } catch (Exception e) {
            return "Bilinmeyen Renk";
        }
    }

    @Step("4. ürünü seçme, beden seçme ve sepete ekleme işlemleri çalıştırılıyor.")
    public void productSelectionSteps() {
        selectFourthProduct();
        selectAvailableSize();
        addToCart();
    }
}

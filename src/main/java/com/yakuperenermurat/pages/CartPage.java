package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[normalize-space()='Sepetim']")
    private WebElement sepetimButton;

    @FindBy(xpath = "//span[@class='rd-cart-item-code']")
    private WebElement productNameInCart;

    @FindBy(xpath = "//input[@value='1']")
    private WebElement productQuantityInCart;

    @FindBy(xpath = "//i[@class='fa fa-heart-o']")
    private WebElement favoriteIcon;

    @FindBy(xpath = "//span[normalize-space()='Favorilerim']")
    private WebElement goToFavoritesButton;

    @FindBy(xpath = "//div[@class='col-md-12 pl-20 pr-20']//a[@class='main-button mt-15'][normalize-space()='ÖDEME ADIMINA GEÇ']")
    private WebElement proceedToCheckoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @Step("Sepetim ekranına gidiliyor ve sayfanın yüklenmesi bekleniyor.")
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(sepetimButton)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='rd-cart-item-code']")));
    }

    @Step("Sepetteki ürün adı doğrulanıyor.")
    public boolean verifyProductName(String expectedFullName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productNameInCart));
        return expectedFullName.contains(element.getText());
    }

    @Step("Sepetteki ürün rengi doğrulanıyor.")
    public boolean verifyProductColor(String expectedProductName) {
        try {
            // 1️⃣ Sepetteki ürün rengini al
            WebElement productColorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[normalize-space()='Bej']")));
            String productColor = productColorElement.getText().trim();

            // 2️⃣ Küçük harfe çevirerek karşılaştır

            boolean isColorInTitle = expectedProductName.toLowerCase().contains(productColor.toLowerCase());

            return isColorInTitle;
        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Ürün rengi doğrulaması zaman aşımına uğradı!");
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("❌ Hata: Renk bilgisi içeren element bulunamadı! XPath yanlış olabilir.");
            return false;
        }
    }


    @Step("Sepetteki ürün adedi doğrulanıyor.")
    public boolean verifyProductQuantity(String expectedQuantity) {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productQuantityInCart));
        return element.getAttribute("value").equals(expectedQuantity);
    }

    @Step("Sepetteki fiyat doğrulanıyor.")
    public boolean verifyPrices() {
        try {
            // Sepette görünen ürün fiyatını al
            WebElement productPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='rd-cart-item-price mb-15']")));
            String productPrice = productPriceElement.getText().trim();

            // Ödeme sayfasındaki toplam fiyatı al
            WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='price-info-area']//span[@class='total-grand-box-amount']")));
            String totalPrice = totalPriceElement.getText().trim();

            // Fiyatlar eşleşiyor mu?
            boolean isPriceMatching = productPrice.equals(totalPrice);

            return isPriceMatching;
        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Fiyat doğrulaması zaman aşımına uğradı!");
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("❌ Hata: Fiyat bilgisi içeren element bulunamadı!");
            return false;
        }
    }

    @Step("Ürün adedi artırılıyor.")
    public void increaseProductQuantity() {
        try {
            WebElement increaseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@id,'Cart_AddQuantity')]")));

            // Tıklamadan önce mevcut değeri al
            String initialQuantity = getProductQuantity();

            // Butona tıkla
            wait.until(ExpectedConditions.elementToBeClickable(increaseBtn)).click();

            // Bekleme mekanizması (mevcut değer değişene kadar bekle)
            wait.until(driver -> !getProductQuantity().equals(initialQuantity));

        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Ürün adedi artırma butonu bulunamadı! XPath yanlış olabilir veya buton görünmüyor.");
        } catch (StaleElementReferenceException e) {
            System.out.println("❌ Hata: StaleElementReferenceException - Sayfa yeniden yüklendi veya element kayboldu.");
        }
    }

    @Step("Ürün adedi azaltılıyor.")
    public void decreaseProductQuantity() {
        try {
            WebElement decreaseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@id,'Cart_RemoveQuantity')]")));

            // Tıklamadan önce mevcut değeri al
            String initialQuantity = getProductQuantity();

            // Butona tıkla
            wait.until(ExpectedConditions.elementToBeClickable(decreaseBtn)).click();

            // Bekleme mekanizması (mevcut değer değişene kadar bekle)
            wait.until(driver -> !getProductQuantity().equals(initialQuantity));
        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Ürün adedi azaltma butonu bulunamadı! XPath yanlış olabilir veya buton görünmüyor.");
        } catch (StaleElementReferenceException e) {
            System.out.println("❌ Hata: StaleElementReferenceException - Sayfa yeniden yüklendi veya element kayboldu.");
        }
    }

    @Step("Sepetteki mevcut ürün adedi alınıyor.")
    public String getProductQuantity() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOf(productQuantityInCart));
            return element.getAttribute("value").trim();
        } catch (StaleElementReferenceException e) {
            System.out.println("❌ Hata: StaleElementReferenceException - Element değişti, tekrar bulmayı deniyoruz...");
            WebElement element = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(productQuantityInCart)));
            return element.getAttribute("value").trim();
        }
    }

    @Step("Sepetteki ürün adını alınıyor.")
    public String getProductNameFromCart() {
        WebElement element = wait.until(ExpectedConditions.visibilityOf(productNameInCart));
        return element.getText().trim();
    }

    @Step("Ürün favorilere ekleniyor.")
    public void addToFavorites() {
        try {
            // Favori butonunun tıklanabilir olmasını bekle
            WebElement favButton = wait.until(ExpectedConditions.elementToBeClickable(favoriteIcon));
            favButton.click();
            // Favoriye ekleme işlemini doğrulamak için bekleyelim
            Thread.sleep(3000);

        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Favori butonu bulunamadı!");
        } catch (NoSuchElementException e) {
            System.out.println("❌ Hata: Favori butonu XPath yanlış olabilir!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Favoriler sayfasına gidiliyor ve ürün doğrulanıyor.")
    public boolean verifyFavoriteProduct(String cartProductName) {
        try {
            // Favoriye ekleme işlemini doğrulamak için bekleyelim
            Thread.sleep(3000);
            // 2️⃣ Favoriler sayfasına git
            WebElement favoritesBtn = wait.until(ExpectedConditions.elementToBeClickable(goToFavoritesButton));
            favoritesBtn.click();

            // 3️⃣ **Favoriler sayfasının tamamen yüklenmesini bekle**
            wait.until(ExpectedConditions.titleIs("Favorilerim | LCW"));

            // 4️⃣ **Favorilerdeki ürün başlığını alalım**
            WebElement favProductElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Kapüşonlu Kız Çocuk Kaban')]")));
            String favProductName = favProductElement.getText().trim();

            // 5️⃣ **Sepetteki ürün adını normalize edelim**
            String cartProductNameNormalized = cartProductName.replaceAll("\\s+", " ").toLowerCase();
            String favProductNameNormalized = favProductName.replaceAll("\\s+", " ").toLowerCase();

            // 6️⃣ **Karşılaştırma**
            boolean isMatching = favProductNameNormalized.contains(cartProductNameNormalized);
            Thread.sleep(2000);

            return isMatching;
        }
         catch (TimeoutException e) {
            System.out.println("❌ Hata: Favori ürünü doğrulama zaman aşımına uğradı!");
            return false;
        } catch (NoSuchElementException e) {
            System.out.println("❌ Hata: Favori ürünü bulunamadı! XPath yanlış olabilir.");
            return false;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Step("Ödeme adımına geçiliyor.")
    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
    }
}

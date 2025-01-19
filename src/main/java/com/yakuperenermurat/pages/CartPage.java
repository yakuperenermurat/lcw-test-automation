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
            System.out.println("🎨 Sepetteki Ürün Rengi: " + productColor);

            // 2️⃣ Ürün detay sayfasındaki ürün başlığını al

            System.out.println("🛍 Ürün Detay Sayfası Başlık: " + expectedProductName);

            // 3️⃣ Küçük harfe çevirerek karşılaştır
            boolean isColorInTitle = expectedProductName.toLowerCase().contains(productColor.toLowerCase());

            if (!isColorInTitle) {
                System.out.println("❌ Hata: Ürün adı içinde renk bilgisi eşleşmiyor!");
                System.out.println("🔍 Karşılaştırılan Değerler:");
                System.out.println("   - 🛍 Ürün Adı: " + expectedProductName);
                System.out.println("   - 🎨 Sepetteki Renk: " + productColor);
            } else {
                System.out.println("✅ Başarılı: Ürün adı içinde renk bilgisi doğrulandı! (" + productColor + ")");
            }

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

            if (!isPriceMatching) {
                System.out.println("❌ Hata: Sepetteki fiyat (" + productPrice + ") ile ödeme adımındaki fiyat (" + totalPrice + ") uyuşmuyor!");
            } else {
                System.out.println("✅ Başarılı: Fiyat doğrulandı! (" + productPrice + ")");
            }

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
            wait.until(ExpectedConditions.elementToBeClickable(increaseBtn)).click();
            wait.until(ExpectedConditions.attributeToBe(productQuantityInCart, "value", "2"));
            System.out.println("✅ Ürün adedi artırıldı!");
        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Ürün adedi artırma butonu bulunamadı! XPath yanlış olabilir veya buton görünmüyor.");
        }
    }

    @Step("Ürün adedi azaltılıyor.")
    public void decreaseProductQuantity() {
        try {
            WebElement decreaseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@id,'Cart_RemoveQuantity')]")));
            wait.until(ExpectedConditions.elementToBeClickable(decreaseBtn)).click();
            wait.until(ExpectedConditions.attributeToBe(productQuantityInCart, "value", "1"));
            System.out.println("✅ Ürün adedi azaltıldı!");

        } catch (TimeoutException e) {
            System.out.println("❌ Hata: Ürün adedi azaltma butonu bulunamadı! XPath yanlış olabilir veya buton görünmüyor.");
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
            System.out.println("💖 Favori butonuna tıklandı!");

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
            System.out.println("💖 Favorilere gidiliyor...");
            favoritesBtn.click();

            // 3️⃣ **Favoriler sayfasının tamamen yüklenmesini bekle**
            wait.until(ExpectedConditions.titleIs("Favorilerim | LCW"));
            System.out.println("✅ Favoriler sayfası başarıyla ");

            // 4️⃣ **Favorilerdeki ürün başlığını alalım**
            WebElement favProductElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Kapüşonlu Kız Çocuk Kaban')]")));
            String favProductName = favProductElement.getText().trim();
            System.out.println("💖 Favorilerde Bulunan Ürün Adı: " + favProductName);

            // 5️⃣ **Sepetteki ürün adını normalize edelim**
            String cartProductNameNormalized = cartProductName.replaceAll("\\s+", " ").toLowerCase();
            String favProductNameNormalized = favProductName.replaceAll("\\s+", " ").toLowerCase();

            // 6️⃣ **Karşılaştırma**
            boolean isMatching = favProductNameNormalized.contains(cartProductNameNormalized);
            if (isMatching) {
                System.out.println("✅ Favorilere eklenen ürün, sepetteki ürünle eşleşiyor!");
                Thread.sleep(2000);
            } else {
                System.out.println("❌ Hata: Favorilerdeki ürün adı sepetteki ürünle eşleşmiyor!");
            }

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

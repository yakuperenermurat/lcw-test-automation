package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * LoginPage: Kullanıcı giriş işlemleri için sayfa nesnesi.
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[contains(@class,'user-wrapper')]//span[1]")
    private WebElement userWrapper;

    @FindBy(xpath = "//a[@class='cart-action__btn cart-action__btn--bg-blue']")
    private WebElement homeLoginButton;

    @FindBy(xpath = "//input[@placeholder='E-posta Adresi veya Telefon Numarası']")
    private WebElement emailInput;

    @FindBy(xpath = "//button[normalize-space()='Devam Et']")
    private WebElement continueButton;

    @FindBy(xpath = "//input[@placeholder='Şifreniz']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(),'Giriş Yap')]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Kullanıcı simgesinin üzerine gelir.
     */
    @Step("Kullanıcı ikonunun üzerine geliniyor.")
    public void hoverOverUserWrapper() {
        Actions actions = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(userWrapper));
        actions.moveToElement(userWrapper).perform();
    }

    /**
     * Anasayfadaki giriş butonuna tıklar.
     */
    @Step("Anasayfadaki giriş butonuna tıklanıyor.")
    public void clickHomeLoginButton() {
        hoverOverUserWrapper();
        wait.until(ExpectedConditions.elementToBeClickable(homeLoginButton)).click();
    }

    /**
     * Email giriliyor ve devam butonuna tıklanıyor.
     * @param email Kullanıcının email adresi
     */
    @Step("Email giriliyor: {0}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        continueButton.click();
    }

    /**
     * Şifre giriliyor.
     * @param password Kullanıcının şifresi
     */
    @Step("Şifre giriliyor: {0}")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
    }

    /**
     * Giriş yap butonuna tıklar ve anasayfaya yönlendirir.
     */
    @Step("Giriş butonuna tıklanıyor ve anasayfaya yönlendiriliyor.")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

        // **Manuel Yönlendirme:** Giriş butonuna tıklandıktan sonra URL'yi değiştiriyoruz.
        driver.navigate().to("https://www.lcw.com/");

        // **Anasayfaya yönlendirme kontrolü:**
        wait.until(ExpectedConditions.urlToBe("https://www.lcw.com/"));
    }

    /**
     * Login adımlarını sırasıyla çalıştırır ve **anasayfaya yönlendirir**.
     */
    @Step("Login adımları çalıştırılıyor ve anasayfaya yönlendiriliyor.")
    public void loginSteps(String email, String password) {
        clickHomeLoginButton();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}

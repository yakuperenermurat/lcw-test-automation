package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    // Kullanıcı profilini temsil eden element (Hover için)
    @FindBy(xpath = "//span[contains(@class,'user-wrapper')]//span[1]")
    private WebElement userWrapper;

    // Hover sonrası açılan "Giriş Yap" butonu
    @FindBy(xpath = "//a[@class='cart-action__btn cart-action__btn--bg-blue']")
    private WebElement homeLoginButton;

    // Email giriş alanı
    @FindBy(xpath = "//input[@placeholder='E-posta Adresi veya Telefon Numarası']")
    private WebElement emailInput;

    // Devam butonu
    @FindBy(xpath = "//button[normalize-space()='Devam Et']")
    private WebElement continueButton;

    // Şifre giriş alanı
    @FindBy(xpath = "//input[@placeholder='Şifreniz']")
    private WebElement passwordInput;

    // Giriş yap butonu
    @FindBy(xpath = "//button[contains(text(),'Giriş Yap')]")
    private WebElement loginButton;

    // Yapıcı metod: Sayfa nesneleri başlatılır
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this); // PageFactory ile elementler başlatılıyor
    }

    /**
     * Anasayfada kullanıcı simgesinin üzerine gelerek açılan "Giriş Yap" butonuna tıklar.
     */
    @Step("Anasayfadaki giriş yap butonuna tıklanıyor.")
    public void clickHomeLoginButton() {
        Actions actions = new Actions(driver);
        // Kullanıcı simgesinin üzerine geliniyor
        actions.moveToElement(userWrapper).perform();
        // Açılan giriş yap butonuna tıklanıyor
        wait.until(ExpectedConditions.elementToBeClickable(homeLoginButton)).click();
    }

    /**
     * Email adresini girer ve devam butonuna tıklar.
     * @param email Kullanıcının email adresi
     */
    @Step("Email giriliyor ve devam butonuna tıklanıyor.")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    /**
     * Kullanıcının şifresini girer.
     * @param password Kullanıcının şifresi
     */
    @Step("Şifre giriliyor.")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput)).sendKeys(password);
    }

    /**
     * Giriş yap butonuna tıklar.
     */
    @Step("Giriş butonuna tıklanıyor.")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        jsExecutor.executeScript("arguments[0].click();", loginButton);
    }
}

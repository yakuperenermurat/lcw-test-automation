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
 * CategoryPage: Kategori seçim işlemleri için sayfa nesnesi.
 */
public class CategoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @FindBy(xpath = "//a[normalize-space()='ÇOCUK & BEBEK']")
    private WebElement cocukBebekCategory;

    @FindBy(xpath = "//span[normalize-space()='KIZ ÇOCUK']")
    private WebElement kizCocukCategory;

    @FindBy(xpath = "//section[contains(@class,'content-tab')]//a[normalize-space()='Mont ve Kaban']")
    private WebElement montVeKabanMenu;

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Çocuk & Bebek kategorisine tıklar ve sayfaya gider.
     */
    @Step("Çocuk & Bebek kategorisine tıklanıyor.")
    public void clickCocukBebekCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(cocukBebekCategory)).click();
        wait.until(ExpectedConditions.urlContains("cocuk-bebek"));
    }

    /**
     * Çocuk & Bebek menüsüne hover yapar.
     */
    @Step("Çocuk & Bebek menüsüne hover yapılıyor.")
    public void hoverOverCocukBebekMenu() {
        actions.moveToElement(cocukBebekCategory).perform();
        wait.until(ExpectedConditions.visibilityOf(kizCocukCategory));
    }

    /**
     * Kız Çocuk kategorisini seçer.
     */
    @Step("Kız Çocuk (6-14 YAŞ) kategorisine tıklanıyor.")
    public void clickKizCocukCategory() {
        hoverOverCocukBebekMenu();
        wait.until(ExpectedConditions.elementToBeClickable(kizCocukCategory)).click();
    }

    /**
     * Mont ve Kaban alt menüsüne tıklar.
     */
    @Step("Mont ve Kaban alt menüsüne tıklanıyor.")
    public void clickMontVeKaban() {
        wait.until(ExpectedConditions.elementToBeClickable(montVeKabanMenu)).click();
    }

    /**
     * Kategori test adımlarını topluca çalıştırır.
     */
    @Step("Kategori test adımları çalıştırılıyor.")
    public void categorySteps() {
        clickCocukBebekCategory();
        hoverOverCocukBebekMenu();
        clickKizCocukCategory();
        clickMontVeKaban();
    }

    /**
     * Sayfanın doğru açıldığını doğrular.
     */
    @Step("Sayfa URL doğrulama kontrolü yapılıyor.")
    public boolean isCorrectPageLoaded() {
        wait.until(ExpectedConditions.urlContains("kiz-cocuk-dis-giyim-t-1010"));
        return driver.getCurrentUrl().equals("https://www.lcw.com/kiz-cocuk-dis-giyim-t-1010");
    }
}

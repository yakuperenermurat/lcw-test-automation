package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SortPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @FindBy(xpath = "//button[@class='dropdown-button__button']")
    private WebElement siralamaDropdown;

    @FindBy(xpath = "//a[normalize-space()='En çok satanlar']")
    private WebElement enCokSatanlar;

    public SortPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Sayfanın en üstüne kaydırılıyor.")
    private void scrollToTop() {
        jsExecutor.executeScript("window.scrollTo(0, 0);");
        waitForPageLoad();
    }

    @Step("Sıralama açılır menüsü tıklanıyor.")
    private void clickSortDropdown() {
        scrollToTop(); // Sayfa yukarı kaydırılıyor
        wait.until(ExpectedConditions.elementToBeClickable(siralamaDropdown)).click();
    }

    @Step("Sıralama 'En çok satanlar' olarak ayarlanıyor ve sayfanın güncellenmesi bekleniyor.")
    private void clickBestSellingOption() {
        wait.until(ExpectedConditions.elementToBeClickable(enCokSatanlar)).click();
        waitForPageLoad();
    }

    @Step("Sayfa yüklenmesi bekleniyor.")
    private void waitForPageLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-overlay')]")));
    }

    /**
     * Sıralama test adımlarını topluca çalıştırır.
     */
    @Step("Sıralama test adımları çalıştırılıyor.")
    public void sortSteps() {
        clickSortDropdown();
        clickBestSellingOption();
    }
}

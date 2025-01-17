package com.yakuperenermurat.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FilterPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor jsExecutor;

    @FindBy(xpath = "//span[contains(text(),'5-6 Yaş')]")
    private WebElement beden56;

    @FindBy(xpath = "(//span[@class='filter-option__text' and contains(text(),'6 Yaş')])[3]")
    private WebElement beden6;

    @FindBy(xpath = "//span[contains(text(),'6-7 Yaş')]")
    private WebElement beden67;

    @FindBy(xpath = "(//img[@src='https://img-lcwaikiki.mncdn.com//resource/images/icon/bej.png'])[1]")
    private WebElement renkBej;

    public FilterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.jsExecutor = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Beden filtresi (5-6 yaş) uygulanıyor ve sayfanın güncellenmesi bekleniyor.")
    public void applySizeFilter56() {
        scrollToElement(beden56);
        wait.until(ExpectedConditions.elementToBeClickable(beden56)).click();
        waitForPageLoad();
    }

    @Step("Beden filtresi (6 yaş) uygulanıyor ve sayfanın güncellenmesi bekleniyor.")
    public void applySizeFilter6() {
        scrollToElement(beden6);
        wait.until(ExpectedConditions.elementToBeClickable(beden6)).click();
        waitForPageLoad();
    }

    @Step("Beden filtresi (6-7 yaş) uygulanıyor ve sayfanın güncellenmesi bekleniyor.")
    public void applySizeFilter67() {
        scrollToElement(beden67);
        wait.until(ExpectedConditions.elementToBeClickable(beden67)).click();
        waitForPageLoad();
    }

    @Step("Renk filtresi (Bej) uygulanıyor ve sayfanın güncellenmesi bekleniyor.")
    public void applyColorFilter() {
        scrollToElement(renkBej);
        wait.until(ExpectedConditions.elementToBeClickable(renkBej)).click();
        waitForPageLoad();
    }

    @Step("Elemente kaydırma işlemi uygulanıyor.")
    private void scrollToElement(WebElement element) {
        jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    @Step("Sayfa yüklenmesi bekleniyor.")
    private void waitForPageLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'loading-overlay')]")));
    }

    @Step("Tüm filtreler sırayla uygulanıyor.")
    public void applyAllFilters() {
        applySizeFilter56();
        applySizeFilter6();
        applySizeFilter67();
        applyColorFilter();
    }
}

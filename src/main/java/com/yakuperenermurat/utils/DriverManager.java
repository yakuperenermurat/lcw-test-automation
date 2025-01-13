package com.yakuperenermurat.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * WebDriver yöneticisi. Tarayıcı oturumlarını yönetir.
 */
public class DriverManager {
    private static WebDriver driver;

    /**
     * WebDriver nesnesini döndürür.
     * @return WebDriver örneği
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            // Chrome WebDriver setup
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Tarayıcı tam ekran başlatılır
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    /**
     * WebDriver oturumunu sonlandırır.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}

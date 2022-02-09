package com.xyzcorp;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CGIPageObj {

    private WebDriver driver;

    public CGIPageObj(WebDriver driver) {
        this.driver = driver;
    }

    public void assertBannerVisible(WebElement banner) {
        assertThat(banner.getAttribute("style")).isEqualTo("display: inline;");
    }

    public void switchToFrench() {
        // Switch Language
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[2]/a")).click();
    }

    public void switchToEnglish() {
        // Switch to English
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[1]/a")).click();
    }

    public void closeSearchBar(WebElement banner) {
        // Close search bar
        driver.findElement(By.xpath("//*[@id='main-nav']/div/div[1]/div/div")).click();
    }

    public void closeCookieAcceptancePopup() {
        // Close Cookie acceptace pop-up
        driver.findElement(By.xpath("//*[@id='popup-buttons']/button[1]")).click();
    }

    public void openSearchBar(WebElement banner) {
        // Open search bar
        driver.findElement(By.xpath("//*[@id='main-nav']/div/div[2]/button")).click();
    }

    public void assertBannerInvisible(WebElement banner) {
        assertThat(banner.getAttribute("style")).isEqualTo("display: none;");
    }

    public WebElement getBanner() {
        // Verify that the banner is exist
        WebElement banner = driver.findElement(By.xpath("//*[@id='main-nav']/div/nav"));
        return banner;
    }

}

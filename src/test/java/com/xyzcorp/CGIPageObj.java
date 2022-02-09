package com.xyzcorp;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class CGIPageObj {

    private WebDriver driver;

    CGIPageObj(WebDriver driver) {
        this.driver = driver;
    }

    void assertBannerVisible(WebElement banner) {
        assertThat(banner.getAttribute("style")).isEqualTo("display: inline;");
    }

    void switchToFrench() {
        // Switch Language
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[2]/a")).click();
    }

    void switchToEnglish() {
        // Switch to English
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id='block-languageswitcher']/ul/li[1]/a")).click();
    }

    void closeSearchBar(WebElement banner) {
        // Close search bar
        driver.findElement(By.xpath("//*[@id='main-nav']/div/div[1]/div/div")).click();
    }

    void closeCookieAcceptancePopup() {
        // Close Cookie acceptace pop-up
        driver.findElement(By.xpath("//*[@id='popup-buttons']/button[1]")).click();
    }

    void openSearchBar(WebElement banner) {
        // Open search bar
        driver.findElement(By.xpath("//*[@id='main-nav']/div/div[2]/button")).click();
    }

    void assertBannerInvisible(WebElement banner) {
        assertThat(banner.getAttribute("style")).isEqualTo("display: none;");
    }

    WebElement getBanner() {
        // Verify that the banner is exist
        WebElement banner = driver.findElement(By.xpath("//*[@id='main-nav']/div/nav"));
        return banner;
    }

    void assertEnglishByURL() {
        assertThat(CGITestLab.driver.getCurrentUrl()).contains("/en");
    }

    void assertFrenchByURL() {
        assertThat(CGITestLab.driver.getCurrentUrl()).contains("/fr");
    }

    void assertEnglishByContent() {
        assertThat(CGITestLab.driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contact");
    }

    void assertFrenchByContent() {
        assertThat(CGITestLab.driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contactez-nous");
    }

}

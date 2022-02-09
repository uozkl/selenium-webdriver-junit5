package com.xyzcorp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.junit.jupiter.api.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.Set;

public class CGITestLab {
    static WebDriver driver;
    static Options options;
    static CGIPageObj cgiPage;
    String sutUrl = "https://www.cgi.com";

    @BeforeAll
    static void setup() {
        driver = WebDriverManager.chromedriver().create();
        cgiPage = new CGIPageObj(driver);
        // Resize window to avoid mobile view
        driver.manage().window().setSize(new Dimension(1440, 960));
        options = driver.manage();
    }

    @AfterEach
    void cleanup() {
        options.deleteAllCookies();
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }

    @Test
    public void verifyCookiesHasCreated() {
        driver.get(sutUrl);
        Set<Cookie> cookiesBeforeAccept = options.getCookies();
        cgiPage.closeCookieAcceptancePopup();
        Set<Cookie> cookiesAfterAccept = options.getCookies();
        assertThat(cookiesBeforeAccept).hasSizeLessThan(cookiesAfterAccept.size());
    }

    @Test
    public void verifyNoCookieAcceptanceAfterTheCookieHasBeenPlaced() {
        driver.get(sutUrl);
        // Make sure the Cookie Acceptance pop-up exist
        driver.findElement(By.xpath("//*[@id='popup-text']"));
        options.addCookie(new Cookie("cookie-agreed", "2"));
        driver.get(sutUrl);
        assertThatThrownBy(() -> driver.findElement(By.xpath("//*[@id='popup-text']")))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void verifyTranslationWorksByURL() {
        driver.get(sutUrl);
        cgiPage.closeCookieAcceptancePopup();
        // By default, it should be English
        assertThat(driver.getCurrentUrl()).contains("en");
        cgiPage.switchToFrench();
        assertThat(driver.getCurrentUrl()).contains("fr");
        // Nav to another page
        driver.findElement(By.xpath("//*[@id='hero-banner']/div/div/div/div[2]/div/div/div[1]/div[2]/a")).click();
        assertThat(driver.getCurrentUrl()).contains("fr");
        cgiPage.switchToEnglish();
        assertThat(driver.getCurrentUrl()).contains("en");
        // Nav to main page
        driver.findElement(By.xpath("//*[@id='Calque_1']")).click();
        assertThat(driver.getCurrentUrl()).contains("en");
    }

    @Test
    public void verifyTranslationWorksByContent() {
        driver.get(sutUrl);
        cgiPage.closeCookieAcceptancePopup();
        // By default, it should be English
        assertThat(driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contact");
        cgiPage.switchToFrench();
        assertThat(driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contactez-nous");
        // Nav to another page
        driver.findElement(By.xpath("//*[@id='hero-banner']/div/div/div/div[2]/div/div/div[1]/div[2]/a")).click();
        assertThat(driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contactez-nous");
        cgiPage.switchToEnglish();
        assertThat(driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contact");
        // Nav to main page
        driver.findElement(By.xpath("//*[@id='Calque_1']")).click();
        assertThat(driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).getText())
                .isEqualTo("Contact");
    }

    @Test
    public void verifySearchButtonHideBanners() {
        driver.get(sutUrl);
        cgiPage.closeCookieAcceptancePopup();
        WebElement banner = cgiPage.getBanner();
        cgiPage.assertBannerVisible(banner);
        cgiPage.openSearchBar(banner);
        cgiPage.assertBannerInvisible(banner);
    }

    @Test
    public void verifyCloseSearchBarDisplaysNavBanners() {
        driver.get(sutUrl);
        cgiPage.closeCookieAcceptancePopup();
        WebElement banner = cgiPage.getBanner();
        cgiPage.assertBannerVisible(banner);
        cgiPage.openSearchBar(banner);
        cgiPage.assertBannerInvisible(banner);
        cgiPage.closeSearchBar(banner);
        cgiPage.assertBannerVisible(banner);
    }

    @Test
    public void verifyContactFormTextFiled() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        driver.get(sutUrl);
        cgiPage.closeCookieAcceptancePopup();
        // Open Contact Form
        driver.findElement(By.xpath("//*[@id='block-cgicontactusblockcontact']/a")).click();
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector(
                        "body > div.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.ui-dialog-buttons")));
        // Find elements and input values
        WebElement editName, editCompany, editEmail, editPhone;
        editName = driver.findElement(By.xpath("//*[@data-drupal-selector='edit-name']"));
        String textValue = "Test Content";
        editName.sendKeys(textValue);
        // Submit form
        driver.findElement(By.cssSelector(
                "body > div.ui-dialog.ui-corner-all.ui-widget.ui-widget-content.ui-front.ui-dialog-buttons > div.ui-dialog-buttonpane.ui-widget-content.ui-helper-clearfix > div > button"))
                .click();
        // Wait till Error message displayed
        wait.until(ExpectedConditions
                .presenceOfElementLocated(By.xpath("//*[@class='messages drupal-messages messages--error']")));
        // Find the element again as the old one has been replaced
        editName = driver.findElement(By.xpath("//*[@data-drupal-selector='edit-name']"));
        editCompany = driver.findElement(By.xpath("//*[@data-drupal-selector='edit-company']"));
        editEmail = driver.findElement(By.xpath("//*[@data-drupal-selector='edit-email'][not(@id='edit-email')]"));
        editPhone = driver.findElement(By.xpath("//*[@data-drupal-selector='edit-phone']"));
        // Verify: Name should have no errors as it has valid input
        assertThat(editName.getAttribute("class")).isEqualTo("form-text required");
        // Verify: Company should be error
        assertThat(editCompany.getAttribute("class")).isEqualTo("form-text required error");
        // Verify: Email should be error
        assertThat(editEmail.getAttribute("class")).isEqualTo("form-email required error");
        // Verify: Phone is not mandatory and should pass
        assertThat(editPhone.getAttribute("class")).isEqualTo("form-tel");

    }

}

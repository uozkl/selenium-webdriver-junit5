package com.xyzcorp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class FindPlanetsTest {
    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void findEarthRadius() {
        String sutUrl = "https://en.wikipedia.org/wiki/Planet";
        driver.get(sutUrl);
        // Nav to Earth raidus page
        driver.findElement(By.xpath("//*[@id='mw-content-text']/div[1]/p[83]/a[1]")).click();
        // Get radius
        WebElement earth_radius = driver
                .findElement(By.xpath("//*[@id='mw-content-text']/div[1]/table[1]/tbody/tr[11]/td/span[2]"));
        // text value == "6,357 to 6,378 km"
        assertThat(earth_radius.getText()).isEqualTo("6,357 to 6,378 km");
    }

    @Test
    public void findEarthDiameterByClass() {
        String sutUrl = "https://en.wikipedia.org/wiki/Solar_System";
        driver.get(sutUrl);
        // Get diameter
        WebElement earth_diameter = driver
                .findElement(By.xpath("//*[@class='wikitable sortable jquery-tablesorter']/tbody/tr[4]/td[1]"));
        // text value == "12,756"
        assertThat(earth_diameter.getText()).isEqualTo("12,756");
    }

    @Test
    public void findEarthDiameterBySelector() {
        String sutUrl = "https://en.wikipedia.org/wiki/Solar_System";
        driver.get(sutUrl);
        // Get diameter
        WebElement earth_diameter = driver.findElement(By.cssSelector(
                "table.wikitable.sortable.jquery-tablesorter > tbody > tr:nth-child(4) > td:nth-child(2)"));
        // text value == "12,756"
        assertThat(earth_diameter.getText()).isEqualTo("12,756");
    }

    @Test
    void testRelativeLocators() {
        driver.get(
                "https://en.wikipedia.org/wiki/Solar_System");

        List<WebElement> captions = driver.findElements(By.tagName("caption"));
        for (WebElement element: captions) {
            System.out.println(">>>" + element);
        }
    }

}

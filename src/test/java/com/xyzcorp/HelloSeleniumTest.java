package com.xyzcorp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloSeleniumTest {

    private static ChromeDriver chromeDriver;

    @BeforeAll
    public static void setup() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\zekun.li\\Workspace\\Selenium\\Driver\\98\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
    }

    @Test
    public void testPage() {
        chromeDriver.get("https://www.google.ca/");
        chromeDriver.manage().window().setSize(new Dimension(1152, 658));
        chromeDriver.findElement(By.name("q")).click();
        chromeDriver.findElement(By.name("q")).sendKeys("Selenium");
        chromeDriver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        chromeDriver.findElement(By.linkText("Wikipedia")).click();
        chromeDriver.findElement(By.id("firstHeading")).click();
        chromeDriver.findElement(By.id("firstHeading")).click();
    }

    @AfterAll
    public static void cheanup() {
        chromeDriver.close();
    }

}

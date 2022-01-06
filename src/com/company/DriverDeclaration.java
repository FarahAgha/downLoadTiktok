package com.company;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverDeclaration {
//    public  WebDriver driver;
    public static WebDriver getWebDriver() {
        //TODO: Remove the Absolute path and convert to relative
        //TODO: use a folder in the project structure
        System.setProperty("webdriver.gecko.driver", "C://Users//Deltaman//Documents//GitUpload//myGit//resources//geckodriver-v0.26.0-win64//geckodriver.exe");
        //System.setProperty("webdriver.gecko.driver", "//Applications//gecko//geckodriver");
        WebDriver driver = new FirefoxDriver();
            System.out.println("Firefox Driver created");
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            System.out.println("Returning");
        return driver;
    }
}

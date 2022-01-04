package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.company.Helper.*;

public class Douyin {
    public static void douyin() throws IOException {
        WebDriver driver = DriverDeclaration.getWebDriver();
//        String url = "https://www.douyin.com/search/dog%20true%20love?";
//        String url = "https://www.douyin.com/search/handsome%20dog?";
//        String url = "https://www.douyin.com/search/lovely%20dog?";
//        String url = "https://www.douyin.com/search/cute%20dog?";
//        String url = "https://www.douyin.com/search/birthday%20dog?";
//        String url = "https://www.douyin.com/search/Mood%20dog?";
        String url = "https://www.douyin.com/search/day%20in%20dogs%20life%20dog?";


        driver.get(url);

        FileWriter myWriter = new FileWriter(Helper.getTodayDate() + ".csv");
        System.out.println("File object is create");



        try {
            //get videos from the selected tab
            Thread.sleep(25000);

            // e.g. @funnydogs99
            By byAtTag = By.xpath("//a[contains(@href,'//www.douyin.com/video')]");

            List<WebElement> videos = Helper.channelVideosToList(driver, byAtTag);
            System.out.println("Got the List. Going to download to CSV");
            channelVideosToCSV(videos, myWriter);

            System.out.println("channelVideosToCSV is executed");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");


        } catch (Exception e) {
            if (myWriter != null)
                myWriter.close();
            if (driver != null)
                driver.quit();
            e.printStackTrace();
        }
        driver.quit();

    }
}

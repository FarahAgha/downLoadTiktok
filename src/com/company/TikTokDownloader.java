package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.company.Helper.channelVideosToCSV;

public class TikTokDownloader {
    public static void tiktok(String[] args) throws IOException {
        String from = args[0];
        System.out.println("Value passed in an arguments " + args[0]);

        String targetURL = "https://www.tiktok.com/@funnydogs99?lang=en";

        if (!from.isEmpty()) {
            targetURL = "https://www.tiktok.com/@" + args[0] + "?lang=en";
        }
        System.out.println("target URL " + targetURL);

        WebDriver driver = DriverDeclaration.getWebDriver();
        driver.get(targetURL);

        FileWriter myWriter = new FileWriter(Helper.getTodayDate() + from + ".csv");
        System.out.println("File object is create");
        try {
            //get videos from the selected tab
            Thread.sleep(25000);

            // e.g. @funnydogs99
            By byAtTag = By.xpath("//a[contains(@href,'https://www.tiktok.com/@" + args[0] + "/video')]");

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

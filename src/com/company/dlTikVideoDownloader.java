package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.company.Helper.writeToCSVURLAndTagName;

public class dlTikVideoDownloader {
    public static void dlTikVideoDownload(String[] args) {
        String from = args[0];
        System.out.println("Value passed in an arguments " + args[0]);

        String targetURL = "https://www.tiktok.com/@funnydogs99?lang=en";

        if (!from.isEmpty()) {
            targetURL = "https://www.tiktok.com/";
//                    "@" + args[0]+ "?lang=en";
        }
        System.out.println("target URL " + targetURL);

        WebDriver driver = DriverDeclaration.getWebDriver();
        driver.get(targetURL);


        try {
            Thread.sleep(30000);
            targetURL = "https://www.tiktok.com/@" + args[0] + "?lang=en";
            driver.get(targetURL);

            List<WebElement> videos = getListOfVideoLinks(args, driver);

            dltikDownload(videos);

        } catch (Exception e) {
            if (driver != null)
                driver.quit();
            e.printStackTrace();
        }
        driver.quit();
        System.out.println("Got the List. Going to download videos");
    }


    private static void dltikDownload(List<WebElement> videos) throws InterruptedException {
        String targetURL;
        targetURL = "https://dltik.com/";
        System.out.println("dltik URL " + targetURL);

        WebDriver driver = DriverDeclaration.getWebDriver();

        for (WebElement vid : videos
        ) {
            driver.get(targetURL);
            System.out.println(vid.getAttribute("href"));

            driver.findElement(By.id("txt-input-url")).click();
            driver.findElement(By.id("txt-input-url")).sendKeys(vid.getAttribute("href"));

            driver.findElement(By.id("btn-submit-link")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//a[span='Download Server 1']")).click();

        }
        driver.quit();

    }

    private static List<WebElement> getListOfVideoLinks(String[] args, WebDriver driver) {


        List<WebElement> videos = null;
        try {
            //get videos from the selected tab
            Thread.sleep(25000);

            // e.g. @funnydogs99
            By byAtTag = By.xpath("//a[contains(@href,'https://www.tiktok.com/@" + args[0] + "/video')]");

            videos = Helper.channelVideosToList(driver, byAtTag);
            System.out.println("Got the List.");

            return videos;
        } catch (Exception e) {
            if (driver != null)
                driver.quit();
            e.printStackTrace();
        }
        return videos;
    }

    public static void dltikDownloadFromCSVFile(List<String> videos) throws InterruptedException {

        String targetURL;
        targetURL = "https://dltik.com/";
        System.out.println("dltik URL " + targetURL);
        FileWriter myWriter = null;

        WebDriver driver = DriverDeclaration.getWebDriver();
        try {
            myWriter = new FileWriter(Helper.getTodayDate() + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File object is create");

        for (String vid : videos) {
            driver.get(targetURL);

            driver.findElement(By.id("txt-input-url")).click();
            driver.findElement(By.id("txt-input-url")).sendKeys(vid);

            driver.findElement(By.id("btn-submit-link")).click();
            Thread.sleep(3000);
            try {
            String text = driver.findElement(By.xpath("//P[contains(text(),'')]")).getText();

            String[] temp = text.split("\\#");
            String tagName = (temp[0]);
//                    .replaceAll("[^a-zA-Z]", " ");
            System.out.println(tagName);
            System.out.println();

                writeToCSVURLAndTagName(vid, tagName, myWriter);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            driver.findElement(By.xpath("//a[span='Download Server 1']")).click();

        }
        try {
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.quit();

    }
}

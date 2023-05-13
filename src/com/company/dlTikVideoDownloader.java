package com.company;

import com.sun.java.swing.plaf.gtk.GTKConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.net.URL;
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

        for (WebElement vid : videos) {
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
            if (driver != null) driver.quit();
            e.printStackTrace();
        }
        return videos;
    }
    public static void dltikDownloadFromCSVFileVersion2(List<String> videos,String savePath ) throws IOException {

        String targetURL;
        targetURL = "https://snaptik.app/";
        System.out.println("ssstik URL " + targetURL);
        FileWriter myWriter = null;
        String WritefileNameDate = Helper.getTodayDate();
        WebDriver driver = DriverDeclaration.getWebDriver();
        try {
            myWriter= new FileWriter(WritefileNameDate+ "_download.csv");

            System.out.println("File object is create");

            for (String vid : videos) {
                System.out.println("Video url=" + vid);
                driver.get(targetURL);

                driver.findElement(By.xpath("//*[@placeholder='Paste TikTok link here']")).click();
                driver.findElement(By.xpath("//*[@placeholder='Paste TikTok link here']")).sendKeys(vid);
                System.out.println("Video URL entered");


                By downloadBtnXpath = By.xpath("//button[@type='submit']");
//                elementExists(driver,downloadBtnXpath);

                driver.findElement(downloadBtnXpath).click();
//                Thread.sleep(35000);
                System.out.println("Getting element title ");
                String titleXpath = "//div[@class='video-title']";
                String videoText = driver.findElement(By.xpath(titleXpath)).getText();

                System.out.println("Getting element title ");
                String titleUser= driver.findElement(
                        By.xpath("//div[@class='info']/span")).getText();

                System.out.println("Href "+ titleUser);

                driver.findElement(By.xpath(
                        "//*[@class='button download-file']")).click();

//                String hrefValue = driver.findElement(By.xpath(
//                        "//*[@class='button download-file']")).getAttribute("href");
//                System.out.println("Href "+ hrefValue);
//                extractedVideoFromSSStok(hrefValue,videoText,savePath);

                String tagName = "#funnypets,#shorts";
                writeToCSVURLAndTagName(vid,  videoText+","+titleUser, myWriter);
            }

        } catch (IOException e) {
            assert myWriter != null;
            myWriter.close();
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            assert myWriter != null;
            myWriter.close();
        }
        finally {
            myWriter.close();
        }
        driver.quit();

    }
    public static void dltikDownloadFromCSVFile(List<String> videos,String savePath ) throws IOException {

        String targetURL;
        targetURL = "https://ssstik.com/";
        System.out.println("ssstik URL " + targetURL);
        FileWriter myWriter = null;
        String WritefileNameDate = Helper.getTodayDate();
        WebDriver driver = DriverDeclaration.getWebDriver();
        try {
            myWriter= new FileWriter(WritefileNameDate+ "_download.csv");

            System.out.println("File object is create");

            for (String vid : videos) {
                System.out.println("Video url=" + vid);
                driver.get(targetURL);

                driver.findElement(By.xpath("//*[@placeholder='Paste a TikTok video link here']")).click();
                driver.findElement(By.xpath("//*[@placeholder='Paste a TikTok video link here']")).sendKeys(vid);
                System.out.println("Video URL entered");

                elementExists(driver,By.xpath("//*[@id='searchBoxBtn']/span[contains(text(),'DOWNLOAD')]"));

                driver.findElement(By.xpath("//*[@id='searchBoxBtn']/span[contains(text(),'DOWNLOAD')]")).click();
                Thread.sleep(35000);
                System.out.println("getting element to right clicked");
                WebElement rightClickElement = driver.findElements(By.xpath("//*[@class='downbutt vLink']")).get(0);

                String hrefValue =rightClickElement.getAttribute("href");
                System.out.println("Href "+ hrefValue);



                String videoText = driver.findElements(By.xpath("//h2")).get(0).getText();
                System.out.println("video Text "+ videoText);
                extractedVideoFromSSStok(hrefValue,videoText,savePath);

                String tagName = "#funnypets,#shorts";
                writeToCSVURLAndTagName(vid,  videoText, myWriter);
            }

        } catch (IOException e) {
            assert myWriter != null;
            myWriter.close();
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
            assert myWriter != null;
            myWriter.close();
        }
        finally {
            myWriter.close();
        }
        driver.quit();

    }

    private static void extractedVideoFromSSStok(String hrefValue,String fileName,String savePath){
        // saving video
         try
         {
            String savefileName = fileName+".mp4";
                    //"//Users//fagha//Documents//myProjects//mp4Downloads//"+Helper.getTodayDate()+".mp4";
            URL imageURL = new URL(hrefValue);
            InputStream in = null;

            in = new BufferedInputStream(imageURL.openStream());

            OutputStream out = new BufferedOutputStream(new FileOutputStream(savefileName));

            for ( int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean elementExists(WebDriver driver, By by) {
        try {

            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }




}

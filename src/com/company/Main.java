package com.company;

import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;

import static com.company.Helper.readCsvFile;


public class Main {

    public static void main(String[] args) {
//        TikTokDownloader.tiktok(args);
//        Douyin.douyin();
//        dlTikVideoDownloader.dlTikVideoDownload(args);

        String WritefileNameDate = Helper.getTodayDate();
        String readPath = "//Users//fagha//Documents//myProjects//";
        String filename = readPath + "File_list_petlove_05232023_copy.txt";

        List<String> vidLst = readCsvFile(filename);
//        dltikDownloadFromCSVFile(vidLst,readPath+"downloads//"+WritefileNameDate);
//        dltikDownloadFromCSVFileVersion2(vidLst,readPath+"downloads//"+WritefileNameDate);

        WebDriver driver = DriverDeclaration.getWebDriver();
        try {
            dlTikVideoDownloader dl = new dlTikVideoDownloader(driver);
            dl.extractedVideoFromTTSave(vidLst, readPath + "downloads//" + WritefileNameDate + "//", WritefileNameDate);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (driver != null) driver.quit();
        }

    }

}

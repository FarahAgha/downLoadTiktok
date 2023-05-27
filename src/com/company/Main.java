package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.List;

import static com.company.Helper.readCsvFile;
import static com.company.dlTikVideoDownloader.*;


public class Main {

    public static void main(String[] args) throws IOException {
//        TikTokDownloader.tiktok(args);
//        Douyin.douyin();
//        dlTikVideoDownloader.dlTikVideoDownload(args);
//        ArrayList lst =

        String WritefileNameDate = Helper.getTodayDate();
        String readPath = "//Users//fagha//Documents//myProjects//";
        String filename = readPath+"Read1641140159788.csv";
//        String filename = "C://Users//Deltaman//Downloads//www.tiktok.com-1641140159788_1.csv";
        //String filename = "C://Users//Deltaman//Downloads//oneURL.txt";

        List<String> vidLst = readCsvFile(filename);
//        dltikDownloadFromCSVFile(vidLst,readPath+"downloads//"+WritefileNameDate);

//        dltikDownloadFromCSVFileVersion2(vidLst,readPath+"downloads//"+WritefileNameDate);

        WebDriver driver = DriverDeclaration.getWebDriver();

        dlTikVideoDownloader dl = new dlTikVideoDownloader(driver);
        dl.tickTokDownloaderTTSave(vidLst,readPath+"downloads//"+WritefileNameDate);

        driver.quit();

    }



}

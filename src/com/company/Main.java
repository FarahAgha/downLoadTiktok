package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.company.Helper.readCsvFile;
import static com.company.dlTikVideoDownloader.dltikDownloadFromCSVFile;


public class Main {

    public static void main(String[] args) throws IOException {
//        TikTokDownloader.tiktok(args);
//        Douyin.douyin();
//        dlTikVideoDownloader.dlTikVideoDownload(args);
//        ArrayList lst =



        String filename = "C://Users//Deltaman//Downloads//www.tiktok.com-1641140159788.csv";
        List<String> vidLst = readCsvFile(filename);
        try {
            dltikDownloadFromCSVFile(vidLst);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

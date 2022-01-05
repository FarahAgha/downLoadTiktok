package com.company;

import java.io.IOException;
import java.util.List;

import static com.company.Helper.readCsvFile;
import static com.company.dlTikVideoDownloader.dltikDownloadFromCSVFile;


public class Main {

    public static void main(String[] args) throws IOException {
//        TikTokDownloader.tiktok(args);
//        Douyin.douyin();
//        dlTikVideoDownloader.dlTikVideoDownload(args);
//        ArrayList lst =



        String filename = "//Users//fagha//Documents//myProjects//Read1641140159788.csv";
        List<String> vidLst = readCsvFile(filename);
        try {
            dltikDownloadFromCSVFile(vidLst);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

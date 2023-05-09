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
//        String filename = "C://Users//Deltaman//Downloads//www.tiktok.com-1641140159788_1.csv";
        //String filename = "C://Users//Deltaman//Downloads//oneURL.txt";

        List<String> vidLst = readCsvFile(filename);
        dltikDownloadFromCSVFile(vidLst);

    }

}

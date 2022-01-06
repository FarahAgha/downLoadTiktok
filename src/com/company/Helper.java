package com.company;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Helper {

    static int vCounter;
    public static String getTodayDate() {
        String today = "";
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

            today = dateFormat.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return today;
    }

    public static List<WebElement> channelVideosToList(WebDriver driver, By videoPath) throws InterruptedException {
        List<WebElement> videos = driver.findElements(videoPath);
        System.out.println("List is Object created");
        WebElement ele = null;

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        System.out.println("JavascriptExecutor object is created");
        int count = 0;

        for (int i = 0; i < 20; i++) {
//            for (int i = 0; i < 1; i++) {
            System.out.println("i is " + i);

            Thread.sleep(10000);
            videos = driver.findElements(videoPath);
            System.out.println("Total count so far " + videos.size());
            for (WebElement link : videos
            ) {
                ele = link;
            }
            jse.executeScript("arguments[0].scrollIntoView(true);", ele);
            if (count == videos.size())
                break;
            count = videos.size();
        }
        System.out.println("Returning List");
        return videos;
    }

    public static void channelVideosToCSV(List<WebElement> videos, FileWriter myWriter) throws IOException {
        System.out.println("Inside channel Videos to CSV");

        System.out.println("" + videos.size());
        for (WebElement link : videos
        ) {
            myWriter.write(link.getAttribute("href"));
            myWriter.write("\n");
        }

    }

    public static List<String> readCsvFile(String fileName) {

        BufferedReader fileReader = null;
        String COMMA_DELIMITER = ",";
        List<String> videoURLs = new ArrayList<String>();

        try {

            //Create a new list of student to be filled by CSV file data
            String line = "";

            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));

            //Read the CSV file header to skip it
//            fileReader.readLine();

            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
//                String[] tokens = line.split(COMMA_DELIMITER);
//                if (tokens.length > 0)
//                {
                    //Create a new student object and fill his  data
                    videoURLs.add(line);
//                }
            }

            //Print the new student list
//            for (String student : videoURLs) {
//                System.out.println(student);
//            }

        } catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
        return videoURLs;

    }

    public static void writeToCSVURLAndTagName(String videos,String temp, FileWriter myWriter) throws IOException {
        System.out.println("Writing channelVideos to CSV");
            myWriter.write(videos+',');
            myWriter.write(temp);
            myWriter.write("\n");
            System.out.println("Files written " + ++vCounter);
        }

}

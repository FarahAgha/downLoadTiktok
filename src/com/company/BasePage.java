package com.company;


import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BasePage<T extends WebDriver> {

    protected final int waitTimeOutSeconds;

    protected WebDriver driver;

    public BasePage(WebDriver driver, int waitTimeOutSeconds) {
        this.driver = driver;
        this.waitTimeOutSeconds = waitTimeOutSeconds;
    }

    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> finds(By locator) {
        return driver.findElements(locator);
    }

    public boolean elementExists(By by) {
        try {
            findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void elementIsVisible(By e) {
        wait_until_true_or_timeout(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void elementIsPresent(By e) {
        wait_until_true_or_timeout(ExpectedConditions.presenceOfElementLocated(e));
    }

    public void elementIsClickable(By e) {
        wait_until_true_or_timeout(ExpectedConditions.elementToBeClickable(e));
    }

    public boolean elementIsDisplayed(By by) {
        return waitUntilElementIsVisible(by).isDisplayed();
    }

    public boolean elementIsSelected(By by) {
        return waitUntilElementIsVisible(by).isSelected();
    }

    /**
     * wait until condition is true or timeout kicks in
     */
    protected <V> void wait_until_true_or_timeout(ExpectedCondition<V> isTrue) {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, waitTimeOutSeconds).ignoring(StaleElementReferenceException.class);
        wait.until(isTrue);
    }

    public void chooseSelectByValue(By by, String value) {
        new Select(findElement(by)).selectByValue(value);
    }

    public void chooseSelectByText(By by, String text) {
        new Select(findElement(by)).selectByVisibleText(text);
    }

    public void enterText(By by, String text) {
        waitUntilElementIsClickable(by).sendKeys(text);
    }

    public void clearBeforeEnterText(By by, String text) {
        waitUntilElementIsClickable(by).clear();
        waitUntilElementIsClickable(by).sendKeys(text);
    }

    public WebElement waitUntilElementIsVisible(By by) {
        return new WebDriverWait(driver, waitTimeOutSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitUntilElementIsClickable(By by) {
        return new WebDriverWait(driver, waitTimeOutSeconds).until(ExpectedConditions.elementToBeClickable(by));
    }

    public void clickElement(WebDriver driver, By by, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            JavascriptExecutor js = ((JavascriptExecutor) driver);
            //wait for element to be present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            //scroll element into view
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            //wait for element to be clickable
            wait.until(ExpectedConditions.elementToBeClickable(by));
            js.executeScript("arguments[0].click();", element);

        } catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException-" + e.getLocalizedMessage());

            e.printStackTrace();


        } catch (WebDriverException e) {
            System.out.println("WebDriverException getMessage-" + e.getMessage());
            e.printStackTrace();
            System.out.println("WebDriverException done");

        }
    }

    public boolean click(By by) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                clickElement(driver, by, waitTimeOutSeconds);
                result = true;
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("WebDriverException getMessage-" + e.getMessage());
                e.printStackTrace();
                System.out.println("WebDriverException done");
            }
            attempts++;
        }
        return result;
    }

    public void sleep(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void switchToPreviousWindow(int numberWindow) {
        int winSize = driver.getWindowHandles().size();
        System.out.println("Total open windows " + winSize);
        while (winSize > numberWindow) {
            System.out.println("Initiating close");
            driver.switchTo().window(driver.getWindowHandles().toArray()[numberWindow].toString());
            driver.close();
            System.out.println("Close has been called");
            driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
            sleep(1000);
            winSize = driver.getWindowHandles().size();
            System.out.println("Total open windows after close call" + winSize);
        }
    }


    public void takeWebElementScreenshot(By by, String fileName) {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        WebElement element = driver.findElement(by);
        Point p = element.getLocation();

        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();

        BufferedImage img = null;
        try {
            img = ImageIO.read(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert img != null;
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, height);

        File testFile = new File("./log/" + "ActualScreenshots/" + fileName + ".png");
        try {
            ImageIO.write(dest, "png", testFile);
            // ImageIO.write(dest, "png", testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // FileUtils.copyFile(screen, testFile);
    }


    public void waitUntilUrlContains(final String text) {
        new WebDriverWait(driver, waitTimeOutSeconds).until(new ExpectedCondition<Boolean>() {
            private String currentUrl = "";

            @Override
            public Boolean apply(WebDriver driver) {
                currentUrl = driver.getCurrentUrl();
                return currentUrl.toLowerCase().contains(text.toLowerCase());
            }

            @Override
            public String toString() {
                return String.format("URL to contain \"%s\". Current URL: \"%s\"", text, currentUrl);
            }
        });
    }

    public void waitUntilPageContainsText(final String text) {
        new WebDriverWait(driver, waitTimeOutSeconds).until(new ExpectedCondition<Boolean>() {
            private String currentPage = "";

            @Override
            public Boolean apply(WebDriver driver) {
                currentPage = driver.getPageSource();
                return currentPage.contains(text);
            }

            @Override
            public String toString() {
                return String.format("Page to contain \"%s\"", text);
            }
        });
    }


}

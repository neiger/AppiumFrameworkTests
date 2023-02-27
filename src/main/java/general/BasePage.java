package general;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.functions.ExpectedCondition;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

public abstract class BasePage {

    protected AndroidDriver<MobileElement> driver;
    private AppiumFluentWait<AppiumDriver<MobileElement>> wait;

    private int staticTimeOut;
    private int dynamicTimeOut;

    // CONSTRUCTOR - Receiving webdriver as a parameter to save it on a global variable to be used later
    public BasePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        this.dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new AppiumFluentWait<>(driver);
        this.wait.withTimeout(Duration.ofSeconds(this.dynamicTimeOut));

    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    // method to wait for the visibility of an element
    protected boolean waitForElementToBeVisible(MobileElement element) {
        boolean flag = false;
        flag = this.wait.until(new Function<AppiumDriver<MobileElement>, Boolean>() {
            public Boolean apply(AppiumDriver<MobileElement> arg0) {
                return element != null && element.isDisplayed();
            };
        });
        return flag;
    }

    protected boolean waitForElementToBeClickable(MobileElement element) {
        boolean flag = false;
        flag = this.wait.until(new Function<AppiumDriver<MobileElement>, Boolean>() {
            public Boolean apply(AppiumDriver<MobileElement> arg0) {
                return element.isEnabled();
            };
        });
        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapElement(MobileElement element) {
        boolean flag = false;
        flag = waitForElementToBeVisible(element) && waitForElementToBeClickable(element) &&
                this.wait.until(new Function<AppiumDriver<MobileElement>, Boolean>() {
                    public Boolean apply(AppiumDriver<MobileElement> arg0) {
                        element.click();
                        return true;
                    };
                });
        return flag;
    }

    // method to enter text on an specific field
    protected boolean sendTextOnCleanElement(MobileElement element, String txt) {

        boolean validationReturn = false;

        if (waitForElementToBeClickable(element)) {
            element.click();
            element.clear();
            validationReturn = typeOnTxtElement(element, txt);
        }
        return validationReturn;
    }

    private boolean typeOnTxtElement(MobileElement element, String txt) {
        element.sendKeys(txt);
        return element.isEnabled();
        //true;//element.getTagName().contains(txt);
    }

    // method to verify text on a certain element
    protected boolean verifyTextOnElement(MobileElement element, String text) {
        boolean flag = false;
        flag = waitForElementToBeVisible(element) &&
                this.wait.until(new Function<AppiumDriver<MobileElement>, Boolean>() {
                    public Boolean apply(AppiumDriver<MobileElement> arg0) {
                        return element.getText().contains(text);
                    };
                });
        return flag;
    }

    protected boolean implicityWaitTimeOnScreen() {
        try {
            TimeUnit.SECONDS.sleep(this.staticTimeOut);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected String getTextFromElement(MobileElement element) {
        String flag = "";
        if (waitForElementToBeVisible(element))
        {
            flag = element.getText();
            return flag;
        } else return flag;
    }

    //     driver.getKeyboard().pressKey(Keys.ENTER);
    protected boolean pressKeyboardKey(Keys keyValue) {
        boolean flag = false;
        if(keyValue != null) {
            driver.getKeyboard().pressKey(keyValue);
            flag = true;
        } else {
            System.out.println("[ERROR]    There is a problem with the Key pressed");
        }

        return flag;
    }

    protected void takeDeviceSnapShot() throws IOException {
        //driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE), new File("/Users/neiger.serrano/Desktop/Repo/Screenshots"));
    }

    /*
     *
     *
     *
     *
     *
     * BELOW METHODS NEED TO BE MIGRATED FOR ANDROID AUTOMATION CALLS
     *
     *
     *
     *
     *
     *  */

    protected boolean isElementVisible(MobileElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            String exp = e.toString();
            if(e.toString().contains("element not found")) {
                System.out.println("[ERROR]  Element is not found");
            }
            return false;
        }
    }

    // clear text
    protected boolean cleanTextField(MobileElement element) {
        try {
            waitForElementToBeVisible(element);
            element.clear();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //will get anytext present on a field
    protected String getTextOnField(MobileElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            String link = element.getAttribute("value");
            return link;
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean jsScrollToFindElement(String scrollY) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("window.scrollBy(0," + scrollY + ")");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean hooverOnElement(MobileElement element) {
        try {
            Actions action = new Actions(driver);
            waitForElementToBeVisible(element);
            action.moveToElement(element).perform();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    protected boolean verifyElementsOnList(List<MobileElement> elements, List<String> elementsText) {
        try {
            for (int i = 0, listVal = elements.size(); i < listVal; i++) {
                verifyTextOnElement(elements.get(i), elementsText.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    protected boolean listMatchSize(List<MobileElement> elementList, int listSize) {
        if (elementList.size() == listSize) {
            System.out.println("The list on WebElement: " + elementList.size() + " match: " + listSize);
            return true;
        } else {
            System.out.println("[ERROR] The list on WebElement: " + elementList.size() + " does not match: " + listSize);
            return false;
        }
    }

    protected boolean waitForJStoLoad(){

        JavascriptExecutor j = (JavascriptExecutor)driver;
        if (j.executeScript("return document.readyState").toString().equals("complete")){
            System.out.println("Page loaded properly.");
            return true;
        } else {
            System.out.println("[ERROR]  ----->  The wait time has failed");
            return false;
        }
    }

    protected List<String> convertListFromWebElementstoStringArray(List<MobileElement> webElementList){
        try {
            List <String> enlist = new ArrayList<String>();
            for (MobileElement e : webElementList) {
                enlist.add(getTextFromElement(e));
            }
            return enlist;
        } catch (Exception e){
            return null;
        }
    }

    protected boolean isElementPresenceLocated(MobileElement element, String value) {
        try {
            return (boolean) this.wait.until((Function<WebDriver, Boolean>)new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver arg0) {
                    return element.getCssValue("display").equals(value);
                };
            });
        } catch (Exception e) {
            return false;
        }
    }
}
package general;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;

public abstract class BasePage {

    protected AndroidDriver<MobileElement> driver;
    private final AppiumFluentWait<AndroidDriver<MobileElement>> wait;
    private final AndroidTouchAction androidTouchAction;

    private final int staticTimeOut;
    private final int dynamicTimeOut;

    // CONSTRUCTOR - Receiving webdriver as a parameter to save it on a global variable to be used later
    public BasePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        this.dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new AppiumFluentWait<>(driver);
        this.wait.withTimeout(Duration.ofSeconds(this.dynamicTimeOut));
        androidTouchAction = new AndroidTouchAction(this.driver);
    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    // method to wait for the visibility of an element
    protected boolean waitForElementToBeVisible(MobileElement element) {
        boolean flag;
        flag = this.wait.until(new Function<AndroidDriver<MobileElement>, Boolean>() {
            public Boolean apply(AndroidDriver<MobileElement> arg0) {
                return element != null && element.isDisplayed();
            }
        });
        return flag;
    }

    protected boolean waitForElementToBeClickable(MobileElement element) {
        boolean flag;
        flag = this.wait.until(new Function<AndroidDriver<MobileElement>, Boolean>() {
            public Boolean apply(AndroidDriver<MobileElement> arg0) {
                return element.isEnabled();
            }
        });
        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapElement(MobileElement element) {
        boolean flag;
        flag = waitForElementToBeVisible(element) && waitForElementToBeClickable(element) &&
                this.wait.until(new Function<AndroidDriver<MobileElement>, Boolean>() {
                    public Boolean apply(AndroidDriver<MobileElement> arg0) {
                        androidTouchAction.tap(ElementOption.element(element)).perform();
                        //element.click();
                        return true;
                    }
                });
        return flag;
    }

    // method to enter text on an specific field
    protected boolean sendTextOnCleanElement(MobileElement element, String txt) {

        boolean validationReturn = false;

        if (waitForElementToBeClickable(element)) {
            androidTouchAction.tap(ElementOption.element(element)).perform();
            //element.click();
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


    protected boolean implicityWaitTimeOnScreen() {
        try {
            TimeUnit.SECONDS.sleep(this.staticTimeOut);
            return true;
        } catch (Exception e) {
            TestUtilities.errorsAndExceptionsManagement(e);
            return false;
        }
    }

    protected boolean pressKeyboardKey(Keys keyValue) {
        boolean flag = false;
        if(keyValue != null) {
            driver.getKeyboard().pressKey(keyValue);
            flag = true;
        } else {
            System.out.println("[]  ----->  There is a problem with the Key pressed");
        }
        return flag;
    }

    /**
     *
     *
     *   ANDROID GESTURES
     *
     *
     **/

    protected boolean doubleTapOnElement(MobileElement element) {
        boolean flag = false;
        try {
            androidTouchAction.tap(ElementOption.element(element)).perform();
            flag = true;
        } catch (Exception e) {
            TestUtilities.errorsAndExceptionsManagement(e);
        }
        return flag;
    }

    protected boolean doubleTapOnScreenWithCoordinatesXY(MobileElement element, int getStartX, int getStartY) {
        boolean flag = false;
        try {
            androidTouchAction.tap(ElementOption.element(element, getStartX, getStartY)).perform();
            androidTouchAction.tap(ElementOption.element(element, getStartX, getStartY)).perform();
            flag = true;
        } catch (Exception e) {
            TestUtilities.errorsAndExceptionsManagement(e);
        }
        return flag;
    }

    protected boolean swipeOnScreenWithCoordinatesXxYy(int getStartX, int getStartY, int getEndX, int getEndY) {
        boolean flag = false;
        try{
            androidTouchAction.longPress(ElementOption.point(getStartX, getStartY)).moveTo(ElementOption.point(getEndX,getEndY)).release().perform();
            flag = true;
        } catch (Exception e){
            TestUtilities.errorsAndExceptionsManagement(e);
        }
        return flag;
    }

     /*
    UNDER DEVELOPMENT
     */

    protected boolean scrollToAnElementOnScreen(String scrollVal) {
        boolean flag = false;
        try {
            driver.findElementByAndroidUIAutomator(scrollVal);
        } catch (Exception e) { TestUtilities.errorsAndExceptionsManagement(e);}
        return flag;
    }

    protected boolean zoomInOutOnScreen(MobileElement element) {
        boolean flag = false;

        Dimension dim = element.getSize();
        int width = dim.width;
        int height = dim.height;

        //Start XY && End XY 1st Touch
        int ftStartXcc = 750; //(int) (width * .5);
        int ftStartYcc = 1450; //(int) (height * .4);
        int ftEndXcc = 1100; //(int) (width * .1);
        int ftEndYcc = 800; //(int) (height * .1);

        //Start XY && End XY 2nd Touch
        int stStartXcc = 748;//(int) (width * .5);
        int stStartYcc = 1448;//(int) (height * .6);
        int stEndXcc = 400; //(int) (width * .9);
        int stEndYcc = 2000; //(int) (height * .9);

        try {

            AndroidTouchAction touch1 = new AndroidTouchAction(driver);
            AndroidTouchAction touch2 = new AndroidTouchAction(driver);

            touch1.longPress(PointOption.point(ftStartXcc, ftStartYcc)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(ftEndXcc, ftEndYcc));

            touch2.longPress(PointOption.point(stStartXcc, stStartYcc)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(stEndXcc, stEndYcc));

            MultiTouchAction multi = new MultiTouchAction(driver);
            multi.add(touch1).add(touch2).perform();

            flag = true;
        } catch (Exception e) {TestUtilities.errorsAndExceptionsManagement(e);}

        return flag;
    }

    /*
    UNDER DEVELOPMENT
     */


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

    // method to verify text on a certain element
    protected boolean verifyTextOnElement(MobileElement element, String text) {
        boolean flag;
        flag = waitForElementToBeVisible(element) &&
                this.wait.until(new Function<AndroidDriver<MobileElement>, Boolean>() {
                    public Boolean apply(AndroidDriver<MobileElement> arg0) {
                        return element.getText().contains(text);
                    }
                });
        return flag;
    }


    protected String getTextFromElement(MobileElement element) {
        String flag = "";
        if (waitForElementToBeVisible(element))
        {
            flag = element.getText();
            return flag;
        } else return flag;
    }

}
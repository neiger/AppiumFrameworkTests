package general;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

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
    private AndroidTouchAction androidTouchAction;
    private final int staticTimeOut;

    // CONSTRUCTOR - Receiving web driver as a parameter to save it on a global variable to be used later
    public BasePage(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        int dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new AppiumFluentWait<>(driver);
        this.wait.withTimeout(Duration.ofSeconds(dynamicTimeOut));
        androidTouchAction = new AndroidTouchAction(this.driver);
    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    // method to wait for the visibility of an element
    protected boolean waitForMobElementToBeVisible(MobileElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element != null && element.isDisplayed());
        return flag;
    }

    protected boolean waitForMobElementToBeTappable(MobileElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element.isEnabled());
        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapMobElement(MobileElement element) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) && waitForMobElementToBeTappable(element) &&
                this.wait.until(arg0 -> {
                        androidTouchAction.tap(ElementOption.element(element)).perform();
                        return true;
                    });
        return flag;
    }

    // method to enter text on a specific field
    protected boolean sendTextOnEmptyMobElement(MobileElement element, String txt) {

        boolean validationReturn = false;

        if (waitForMobElementToBeTappable(element)) {
            androidTouchAction.tap(ElementOption.element(element)).perform();
            element.clear();
            validationReturn = typeTxtOnMobElement(element, txt);
        }
        return validationReturn;
    }

    private boolean typeTxtOnMobElement(MobileElement element, String txt) {
        element.sendKeys(txt);
        return element.isEnabled();
        //true;//element.getTagName().contains(txt);
    }


    // method to verify text on a certain element
    protected boolean verifyTextOnMobElement(MobileElement element, String text) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) &&
                this.wait.until(arg0 -> element.getText().contains(text));
        return flag;
    }


    protected String getTextFromMobElement(MobileElement element) {
        String flag = "";
        if (waitForMobElementToBeVisible(element))
        {
            flag = element.getText();
        }
        return flag;
    }

    protected boolean implicityWaitTimeOnScreen() {
        try {
            TimeUnit.SECONDS.sleep(this.staticTimeOut);
            return true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
            return false;
        }
    }

    protected boolean implicityWaitTimeOnScreenManual(int secs) {
        try {
            TimeUnit.SECONDS.sleep(secs);
            return true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
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

    /*****   ANDROID GESTURES  *****/

    protected boolean doubleTapOnMobElement(MobileElement element) {
        boolean flag = false;
        try {
            androidTouchAction.tap(ElementOption.element(element)).perform();
            androidTouchAction.tap(ElementOption.element(element)).perform();
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
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
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    protected boolean swipeOnScreenWithCoordinatesXxYy(int getStartX, int getStartY, int getEndX, int getEndY) {
        boolean flag = false;
        try{
            androidTouchAction.longPress(ElementOption.point(getStartX, getStartY)).moveTo(ElementOption.point(getEndX,getEndY)).release().perform();
            flag = true;
        } catch (Exception e){
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    protected boolean multiTouchOnScreen(MobileElement element) {
        boolean flag = false;

        Dimension dim = element.getSize();
        int width = dim.width;
        int height = dim.height;
        System.out.println("Maps Dimension: " + dim + " Width: " +width + " Height: " + height);

        //Start XY && End XY 1st Touch
        int ftStartXcc = 400;//(int) (width * .5);    // 1440 * 0.5 = 720
        int ftStartYcc = (int) (height * .4);   // 2547 * 0.4 = 1018.8

        int ftEndXcc = (int) (width * .1);      // 1440 * 0.1 = 144
        int ftEndYcc = (int) (height * .1);     // 2547 * 0.1 = 254.7
        System.out.println("First Touch: " + ftStartXcc + " -> " + ftStartYcc + " ----> " + ftEndXcc + " -> " + ftEndYcc);

        //Start XY && End XY 2nd Touch
        int stStartXcc = (int) (width * .5);    // 1440 * 0.5 = 720
        int stStartYcc = (int) (height * .6);   // 2547 * 0.6 = 1528.2

        int stEndXcc = (int) (width * .9);      // 1440 * 0.9 = 1296
        int stEndYcc = (int) (height * .9);     // 2547 * 0.9 = 2292.3
        System.out.println("Second Touch: " + stStartXcc + " -> " + stStartYcc + " ----> " + stEndXcc + " -> " + stEndYcc);

        try {

            AndroidTouchAction touch1 = new AndroidTouchAction(driver);
            touch1.longPress(PointOption.point(ftStartXcc, ftStartYcc))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(ftEndXcc, ftEndYcc));

            AndroidTouchAction touch2 = new AndroidTouchAction(driver);
            touch2.longPress(PointOption.point(stStartXcc, stStartYcc))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
                    .moveTo(PointOption.point(stEndXcc, stEndYcc));

            MultiTouchAction multi = new MultiTouchAction(driver);
            multi.add(touch1)
                 .add(touch2)
                 .perform();

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}

        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapOnScreenXY(int getX, int getY) {
        boolean flag;
        flag = this.wait.until(arg0 -> {
                    androidTouchAction.tap(ElementOption.point(getX,getY)).release().perform();
                    //androidTouchAction.press(ElementOption.point(getX,getY)).perform();
            return true;
                });
        return flag;
    }

}
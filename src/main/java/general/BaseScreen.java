package general;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;


public abstract class BaseScreen {

    protected AndroidDriver driver;
    private final AppiumFluentWait<AndroidDriver> wait;
    private final Actions touchAction;
    private final int staticTimeOut;

    // CONSTRUCTOR - Receiving web driver as a parameter to save it on a global variable to be used later
    public BaseScreen(AndroidDriver driver) {
        this.driver = driver;
        this.staticTimeOut = MobileDriverManager.getStaticTime();
        int dynamicTimeOut = MobileDriverManager.getDynamicTime();
        this.wait = new AppiumFluentWait<>(driver);
        this.wait.withTimeout(Duration.ofSeconds(dynamicTimeOut));
        touchAction = new Actions(this.driver);
    }

    /****** GENERIC METHODS ******/

    public abstract boolean verifyLoads();

    // method to wait for the visibility of an element
    protected boolean waitForMobElementToBeVisible(WebElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element != null && element.isDisplayed());
        return flag;
    }

    protected boolean waitForMobElementToBeTappable(WebElement element) {
        boolean flag;
        flag = this.wait.until(arg -> element.isEnabled());
        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapMobElement(WebElement element) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) && waitForMobElementToBeTappable(element) &&
                this.wait.until(arg0 -> {
                    touchAction.click(element).perform();
                    return true;
                    });
        return flag;
    }

    // method to enter text on a specific field
    protected boolean sendTextOnEmptyMobElement(WebElement element, String txt) {

        boolean validationReturn = false;

        if (waitForMobElementToBeTappable(element)) {
            touchAction.click(element).perform();
            element.clear();
            validationReturn = typeTxtOnMobElement(element, txt);
        }
        return validationReturn;
    }

    private boolean typeTxtOnMobElement(WebElement element, String txt) {
        element.sendKeys(txt);
        return element.isEnabled();
    }


    // method to verify text on a certain element
    protected boolean verifyTextOnMobElement(WebElement element, String text) {
        boolean flag;
        flag = waitForMobElementToBeVisible(element) &&
                this.wait.until(arg0 -> element.getText().contains(text));
        return flag;
    }


    protected String getTextFromMobElement(WebElement element) {
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

    protected boolean pressAndroidKey(AndroidKey keyValue) {
        boolean flag = false;
        if(keyValue != null) {
            driver.pressKey(new KeyEvent(keyValue));
            flag = true;
        } else {
            System.out.println(TestUtilities.basicErrorMsg("There is a problem with the Key pressed"));
        }
        return flag;
    }

    /*****   ANDROID GESTURES  *****/

    protected boolean doubleTapOnMobElement(WebElement element) {
        boolean flag = false;
        try {
            touchAction.doubleClick(element).perform();
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    protected boolean doubleTapOnScreenWithCoordinatesXY(WebElement element, int getStartX, int getStartY) {
        boolean flag = false;
        try {
            System.out.println(element.toString());
            Map<String, Object> args = new HashMap<>();
            args.put("x", getStartX);
            args.put("y", getStartY);
            driver.executeScript("mobile: doubleTap", args);
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    protected boolean horizontalSwipeOnScreenXY(WebElement element) {
        boolean flag = false;

        System.out.println("Maps Dimension: " + element.getSize());

        int centerY = element.getRect().y + (element.getSize().height/2);
        double stStartXcc = element.getRect().x + (element.getSize().width * 0.9);
        double stEndXcc = element.getRect().x + (element.getSize().width * 0.1);

        System.out.println("Horizontal swipe Y: " + centerY + "\nStart X: " + stStartXcc + "\nEnd X: " + stEndXcc);


        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), (int) stStartXcc, centerY));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), (int) stEndXcc, centerY));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}
        return flag;
    }

    protected boolean verticalSwipeOnScreenXY(WebElement element) {
        boolean flag = false;

        System.out.println("Maps Dimension: " + element.getSize());

        int centerX = element.getRect().x + (element.getSize().width/2);
        double stStartYcc = element.getRect().y + (element.getSize().height * 0.8);
        double stEndYcc = element.getRect().y + (element.getSize().height * 0.1);

        System.out.println("Vertical swipe X: " + centerX + "\nStart Y: " + stStartYcc + "\nEnd Y: " + stEndYcc);

        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger,1);
            swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), centerX, (int) stStartYcc));
            swipe.addAction(finger.createPointerDown(0));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, (int) stEndYcc));
            swipe.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(swipe));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}
        return flag;
    }

    protected boolean zoomInOnScreenXY(WebElement element) {
        boolean flag = false;

        int centerX = element.getRect().x + (element.getSize().width/2);
        int centerY = element.getRect().y + (element.getSize().height/2);
        int xMovement = 300;

        int finger1Start = centerX - 40;
        int finger2Start = centerX + 40;

        int finger1End = (int) (centerX - (1.33 * xMovement));
        int finger2End = (int) (centerX + (1.4 * xMovement));

        System.out.println("ZOOM IN\nCenterX: " + centerX + "\nCenterY: " + centerY + "\nFinger1 Start: " + finger1Start
                            + "--->Finger1 End: " + finger1End + "\nFinger2 Start: " + finger2Start + "--->Finger2 End: " + finger2End);


        try {
            PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
            Sequence swipe01 = new Sequence(finger1,1);
            swipe01.addAction(finger1.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), finger1Start, centerY-100));
            swipe01.addAction(finger1.createPointerDown(0));
            swipe01.addAction(finger1.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), finger1End, centerY-100));
            swipe01.addAction(finger1.createPointerUp(0));

            PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
            Sequence swipe02 = new Sequence(finger2,1);
            swipe02.addAction(finger2.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), finger2Start, centerY+100));
            swipe02.addAction(finger2.createPointerDown(0));
            swipe02.addAction(finger2.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), finger2End, centerY+100));
            swipe02.addAction(finger2.createPointerUp(0));

            driver.perform(Arrays.asList(swipe01,swipe02));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}

        return flag;
    }

    protected boolean zoomOutOnScreenXY(WebElement element) {
        boolean flag = false;

        int centerX = element.getRect().x + (element.getSize().width/2);
        int centerY = element.getRect().y + (element.getSize().height/2);
        int xMovement = 300;

        int finger1Start = (int) (centerX - (1.65 * xMovement));
        int finger2Start = (int) (centerX + (1.65 * xMovement));

        int finger1End = centerX - 40;
        int finger2End = centerX + 40;

        System.out.println("ZOOM OUT\nCenterX: " + centerX + "\nCenterY: " + centerY + "\nFinger1 Start: " + finger1Start
                + "--->Finger1 End: " + finger1End + "\nFinger2 Start: " + finger2Start + "--->Finger2 End: " + finger2End);

        try {
            PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
            Sequence swipe01 = new Sequence(finger1,1);
            swipe01.addAction(finger1.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), finger1Start, centerY));
            swipe01.addAction(finger1.createPointerDown(0));
            swipe01.addAction(finger1.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), finger1End, centerY));
            swipe01.addAction(finger1.createPointerUp(0));

            PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");
            Sequence swipe02 = new Sequence(finger2,1);
            swipe02.addAction(finger2.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), finger2Start, centerY));
            swipe02.addAction(finger2.createPointerDown(0));
            swipe02.addAction(finger2.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), finger2End, centerY));
            swipe02.addAction(finger2.createPointerUp(0));

            driver.perform(Arrays.asList(swipe01,swipe02));

            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);}

        return flag;
    }

    // method to wait for an element to be clickable
    protected boolean tapOnScreenXY(int getX, int getY) {
        boolean flag;
        flag = this.wait.until(arg0 -> {
                    //touchAction.tap(ElementOption.point(getX,getY)).release().perform();
                    touchAction.moveByOffset(getX,getY).release().perform();
            return true;
                });
        return flag;
    }

}
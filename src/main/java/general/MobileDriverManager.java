package general;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.URL;


public class MobileDriverManager extends TestUtilities {

    private static final ThreadLocal<AndroidDriver> mobAndroidDriver = new ThreadLocal<>();

    private static int staticTime;
    private static int dynamicTime;

    public static int getStaticTime() {
        return staticTime;
    }
    public static int getDynamicTime() {
        return dynamicTime;
    }

    @Parameters({"tmStatic", "tmDynamic"})
    @BeforeMethod
    public void setMobDriverTimes(int tmStatic, int tmDynamic) {
        MobileDriverManager.staticTime = tmStatic;
        MobileDriverManager.dynamicTime = tmDynamic;
    }


    @Parameters({"platformName", "platformVersion", "deviceName", "automationName", "appPackage","appActivity", "noReset", "appiumServer"})
    @BeforeMethod(alwaysRun = true)
    public final void setMobDriver(String platformName, String platformVersion, String deviceName,
                                   String automationName, String appPackage, String appActivity,
                                   String noReset, String appiumServer) {
        try {
            System.out.println("[DRIVER MSG]  ----> The mobile test driver is being initialized now");

            DesiredCapabilities capability = new DesiredCapabilities();

            // Android Device capabilities
            capability.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
            capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
            capability.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
            //capability.setCapability(MobileCapabilityType.APP, appPackage); // In case apk needs to be installed
            capability.setCapability("appPackage", appPackage);
            capability.setCapability("appActivity", appActivity);
            capability.setCapability(MobileCapabilityType.NO_RESET, noReset);
            capability.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 0);

            mobAndroidDriver.set(new AndroidDriver<MobileElement>(new URL(appiumServer), capability));

        } catch (Exception e) {ErrorsManager.errNExpManager(e);}

    }

    // driver initiator which gets ready in the @BeforeMethod and does not require to be passed
    // as parameter in the ScreenTests classes
    public AndroidDriver getDriver() {
        return mobAndroidDriver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteDriver() throws NullPointerException{
        try {
            System.out.println("[DRIVER MSG]  ----> The browser driver is being close now");
            getDriver().quit();
        } catch (NullPointerException e) {ErrorsManager.errNExpManager(e);}
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getInstance().getClass().getSimpleName();
        if(iTestResult.getStatus() == 2) {
            System.out.println("[FAILED TEST NAME:]  ----->  " + testName);
            takeDeviceSnapshot(mobAndroidDriver, testName);
        }
    }
}
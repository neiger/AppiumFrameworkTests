package general;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


public class MobileDriverManager extends TestListenerAdapter {

    private static ThreadLocal<AppiumDriver> mobAppiumDriver = new ThreadLocal<AppiumDriver>();

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


    @Parameters({"platformName", "platformVersion", "deviceName", "automationName", "appPath","appActivity", "noReset", "appiumServer"})
    @BeforeMethod(alwaysRun = true)
    public final void setMobDriver(String platformName, String platformVersion, String deviceName,
                                   String automationName, String appPath, String appActivity,
                                   String noReset, String appiumServer) throws Exception {

        System.out.println("[DRIVER MSG]  ---- The mobile test driver is being initialized now");

        DesiredCapabilities capability = new DesiredCapabilities();

        // Android Device capabilities
        capability.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
        capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        capability.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);
        //capability.setCapability(MobileCapabilityType.APP, appPath); // In case apk needs to be installed
        capability.setCapability("appPackage", appPath);
        capability.setCapability("appActivity", appActivity);
        capability.setCapability(MobileCapabilityType.NO_RESET, noReset);

        mobAppiumDriver.set(new AndroidDriver<MobileElement>(new URL(appiumServer), capability));
    }

    // driver initiator which gets ready in the @BeforeMethod and does not require to be passed
    // as parameter in the ScreenTests classes
    public AndroidDriver<MobileElement> getDriver() {
        return (AndroidDriver<MobileElement>) mobAppiumDriver.get();
    }

    @AfterMethod(alwaysRun = true)
    public void deleteDriver() {
        System.out.println("[DRIVER MSG]  ---- The browser driver is being close now");
        getDriver().quit();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = iTestResult.getInstance().getClass().getSimpleName();
        if(iTestResult.getStatus() == 2) {
            System.out.println("THE TEST FAILED IS: " + testName);
            try {
                File srcFile =  mobAppiumDriver.get().getScreenshotAs(OutputType.FILE);
                Date d = new Date();
                String TimeStamp = d.toString().replace(":","_").replace(" ","_");
                FileUtils.copyFile(srcFile, new File("./Screenshots/" +
                        testName + "_" + TimeStamp + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
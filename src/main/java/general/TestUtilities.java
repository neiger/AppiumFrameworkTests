package general;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TestUtilities extends TestListenerAdapter {

    private static final ThreadLocal<SoftAssert> assertGeneric = new ThreadLocal<SoftAssert>();
    private static final ThreadLocal<Boolean> assertCalling = new ThreadLocal<>();

    protected void assertTrue(boolean val, String msg){
        assertCalling.set(true);
        assertGeneric.get().assertTrue(val,msg);
    }

    protected void assertFalse(boolean val, String msg){
        assertCalling.set(true);
        assertGeneric.get().assertFalse(val,msg);
    }

    protected void assertAll(){
        assertCalling.set(true);
        assertGeneric.get().assertAll();
    }

    @BeforeMethod
    protected void setSoftAssertValidation() {
        assertGeneric.set(new SoftAssert());
        assertCalling.set(false);
    }

    protected void takeDeviceSnapshot(ThreadLocal<AndroidDriver> mobAndroidDriver, String testName) {
        try {
            File srcFile =  mobAndroidDriver.get().getScreenshotAs(OutputType.FILE);
            Date d = new Date();
            String TimeStamp = d.toString().replace(":","_").replace(" ","_");
            FileUtils.copyFile(srcFile, new File("./Screenshots/" +
                    testName + "_" + TimeStamp + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

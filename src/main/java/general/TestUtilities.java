package general;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestUtilities extends TestListenerAdapter {

    private static final ThreadLocal<SoftAssert> assertGeneric = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> assertCalling = new ThreadLocal<>();

    protected void assertTrue(boolean validation, String msg){
        assertCalling.set(true);
        assertGeneric.get().assertTrue(validation,msg);
    }

    protected void assertFalse(boolean validation, String msg){
        assertCalling.set(true);
        assertGeneric.get().assertFalse(validation,msg);
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
            TestUtilities.errorsAndExceptionsManagement(e);
        }
    }

    public static void errorsAndExceptionsManagement(Exception e){
        try {
            String error = e.toString();
            boolean append = true;
            File txtObj = new File("logging.txt");
            FileWriter fileWriter = new FileWriter("logging.txt", append);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
            String dateAndTime = LocalDateTime.now().format(dateTimeFormatter);

            if (txtObj.createNewFile()) {
                System.out.println("File created. " + txtObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            fileWriter.write("\n"+"\nDate and Time: "+dateAndTime);
            fileWriter.write("\nERROR MESSAGE LOG "+ "\n" + error);
            fileWriter.close();
        } catch (Exception i) {
            System.out.println("An error has occurred\n" + i);
        }

    }

    @BeforeMethod
    protected void testStartingMsg() {
        System.out.println("[STARTING THE TEST]...\n...");
    }

    @AfterMethod
    protected void testEndingMsg() {
        System.out.println("[ENDING THE TEST]...\n...");
    }

    protected String basicErrorMsg(String msg) {
        return "[error]  -----> " + msg;
    }
}

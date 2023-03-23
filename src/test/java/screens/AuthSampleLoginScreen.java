package screens;

import general.BasePage;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AuthSampleLoginScreen extends BasePage {

    public AuthSampleLoginScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(topActionBar) && waitForMobElementToBeVisible(loginBtn);
    }

    /*
    UI ELEMENTS
     */

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/action_bar")
    private WebElement topActionBar;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/btn_login")
    private WebElement loginBtn;


    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/tvName")
    private WebElement tvName;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/tvEmail")
    private WebElement tvEmail;

    /*
    METHODS
     */

    public boolean tapOnLoginBtn() {
        boolean flag = false;
        flag = tapMobElement(loginBtn);
        return flag;
    }

    public boolean waitUntilUsersLogsInBrowserView() {
        return implicityWaitTimeOnScreenManual(10);
    }

    public boolean verifySignInState() {
        return waitForMobElementToBeVisible(tvName) && waitForMobElementToBeVisible(tvEmail);
    }

    public boolean getUsersDataInformationPrint(){
        boolean flag = false;
        try {
            System.out.println(getTextFromMobElement(tvName));
            System.out.println(getTextFromMobElement(tvEmail));
            flag = true;
        }catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    /*
    RETURN-REDIRECT PAGE CALLS
     */
    public WebViewBrowserScreen signInFromBrowser() {
        if(tapOnLoginBtn() && implicityWaitTimeOnScreen()) {
            return new WebViewBrowserScreen(driver);
        } else {return null;}
    }
}

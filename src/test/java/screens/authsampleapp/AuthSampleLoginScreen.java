package screens.authsampleapp;

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

    @AndroidFindBy(className="android.widget.TextView")
    private WebElement topActionBarTxt;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/btn_login")
    private WebElement loginBtn;

    @AndroidFindBy(id="com.android.chrome:id/close_button")
    private WebElement closeTabBtn;


    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/tvName")
    private WebElement tvName;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/tvEmail")
    private WebElement tvEmail;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/btn_logout")
    private WebElement loggedOutBtn;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/tvToken")
    private WebElement tokenInfo;

    @AndroidFindBy(id="com.openmobilehub.auth.sample:id/btn_refresh")
    private WebElement refreshBtn;

    /*
    METHODS
     */

    public boolean validateSignInUIScreenElements() {
        boolean flag = false;
        try {
            System.out.println(getTextFromMobElement(loginBtn));
            System.out.println(getTextFromMobElement(topActionBarTxt));
            flag = true;
        } catch (Exception e) {ErrorsManager.errNExpManager(e);}
        return flag;
    }

    public boolean tapOnLoginBtn() {
        boolean flag = false;
        flag = tapMobElement(loginBtn);
        return flag;
    }

    public boolean validateBrowserTab() {
        boolean flag = false;

        try {
            flag =
            waitForMobElementToBeVisible(closeTabBtn) &&
            tapMobElement(closeTabBtn) && implicityWaitTimeOnScreen() &&
            waitForMobElementToBeVisible(topActionBarTxt);
        }catch (Exception e) {ErrorsManager.errNExpManager(e);}

        return flag;
    }

    public boolean verifySignInState() {
        return implicityWaitTimeOnScreenManual(3) &&
                waitForMobElementToBeVisible(loggedOutBtn) && waitForMobElementToBeVisible(refreshBtn) &&
                waitForMobElementToBeVisible(tvName) && waitForMobElementToBeVisible(tvEmail) &&
                waitForMobElementToBeVisible(tokenInfo);
    }

    public boolean getUsersDataInformationPrint(){
        boolean flag = false;
        try {
            System.out.println(getTextFromMobElement(tvName));
            System.out.println(getTextFromMobElement(tvEmail));
            System.out.println(getTextFromMobElement(tokenInfo));
            flag = true;
        }catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }
        return flag;
    }

    public boolean signOutTheApp() {
        return tapMobElement(loggedOutBtn) && waitForMobElementToBeVisible(loginBtn);
    }

    /*
    RETURN-REDIRECT PAGE CALLS
     */
    public WebViewBrowserScreen signInFromBrowser() {
        if(tapOnLoginBtn()) {
            return new WebViewBrowserScreen(this.driver);
        } else {return null;}
    }
}

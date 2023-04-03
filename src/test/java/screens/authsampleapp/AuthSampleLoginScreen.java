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

    // logged out elements
    @AndroidFindBy(id="com.omh.android.auth.sample:id/action_bar")
    private WebElement topActionBar;

    @AndroidFindBy(className="android.widget.TextView")
    private WebElement topActionBarTxt;

    @AndroidFindBy(id="com.omh.android.auth.sample:id/btn_login")
    private WebElement loginBtn;

    // account picker popup
    @AndroidFindBy(id="com.google.android.gms:id/container")
    private WebElement pickAccount;

    // logged in elements
    @AndroidFindBy(id="com.omh.android.auth.sample:id/tvName")
    private WebElement tvName;

    @AndroidFindBy(id="com.omh.android.auth.sample:id/tvEmail")
    private WebElement tvEmail;

    @AndroidFindBy(id="com.omh.android.auth.sample:id/btn_logout")
    private WebElement loggedOutBtn;

    @AndroidFindBy(id="com.omh.android.auth.sample:id/tvToken")
    private WebElement tokenInfo;

    @AndroidFindBy(id="com.omh.android.auth.sample:id/btn_refresh")
    private WebElement refreshBtn;

    /*
    METHODS
     */

    private boolean tapOnLoginBtn() {
        boolean flag = false;
        flag = tapMobElement(loginBtn);
        return flag;
    }

    public boolean verifySignInPopUpShown() {
        boolean flag = false;
        if(tapOnLoginBtn()) {
            flag = waitForMobElementToBeVisible(pickAccount) && tapMobElement(pickAccount);
        }
        return flag;
    }


    /*
    RETURN-REDIRECT PAGE CALLS
     */
    public WebViewBrowserScreen signInFromBrowser() {
        if(tapOnLoginBtn()) {
            return new WebViewBrowserScreen(this.driver);
        } else {return null;}
    }

    /*
    AFTER SIGNED IN VALIDATIONS
     */

    public boolean verifySignInState() {
        return implicityWaitTimeOnScreen() &&
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
}

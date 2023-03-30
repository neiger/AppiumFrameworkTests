package screens.authsampleapp;

import general.BasePage;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class WebViewBrowserScreen extends BasePage {

    public WebViewBrowserScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {return false;}


    /*
    UI ELEMENTS - Since they are in browser view, the interactions will be handled as web actions rather than mobile's
     */

    @AndroidFindBy(id="com.android.chrome:id/close_button")
    @AndroidFindBy(id="com.huawei.browser:id/close_button")
    private WebElement closeTabBtn;

    @AndroidFindBy(id="com.android.chrome:id/url_bar")
    @AndroidFindBy(id="com.huawei.browser:id/url_text")
    private WebElement urlBar;

    @AndroidFindBy(xpath="//android.view.View[@content-desc=\"Neiger PK Drake neiger.drake@gmail.com\"]")
    private WebElement loggedAccount;

/*
    @AndroidFindBy(id="com.huawei.browser:id/close_button")
    private WebElement closeTabBtnNonGMS;

    @AndroidFindBy(id="com.huawei.browser:id/url_text")
    private WebElement urlBarNoGMS;
*/

//X=540 Y=700


    /*
    METHODS
     */



    public boolean verifySignPageLoads(String osType) {
        if(osType.equals("GMS")){
            return waitForMobElementToBeVisible(closeTabBtn)
                    && waitForMobElementToBeVisible(urlBar);
        } else {
            //if (closeTabBtn.getText().contains("huawei") && urlBar.getText().contains("huawei"))
            return waitForMobElementToBeVisible(closeTabBtn)
                    && waitForMobElementToBeVisible(urlBar);
        }
    }

    private boolean clickAccountLoggedIn() {
        boolean flag = false;
        try {
            loggedAccount.click();
            flag = true;
        } catch (Exception e) {ErrorsManager.errNExpManager(e);}
        return flag;
    }

    /*
    RETURN-REDIRECT PAGE CALLS
     */

    public AuthSampleLoginScreen returnAsSignInState() {
        if(clickAccountLoggedIn()) {
            return new AuthSampleLoginScreen(driver);
        } else {return null;}
    }

}

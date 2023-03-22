package screens;

import general.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

public class WebViewBrowserScreen extends BasePage {
    public WebViewBrowserScreen(AndroidDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(browserDialogScreen);
    }

    /*
    UI ELEMENTS
     */

    @AndroidFindBy(id="com.huawei.browser:id/fake_dialog_layout")
    private AndroidElement browserDialogScreen;

    @AndroidFindBy(id="") //X=553  Y=700        X=800   Y=2025
    private AndroidElement gAccountLogged;


    /*
    METHODS
     */

    private boolean tapOnXYScreenCoordinates(int getX, int getY) {
        return tapOnScreenXY(getX,getY);
    }

    /*
    RETURN-REDIRECT PAGE CALLS
     */

    public AuthSampleLoginScreen returnAsSignInState(int getX, int getY) {
        if(implicityWaitTimeOnScreen() && tapOnXYScreenCoordinates( getX, getY) && implicityWaitTimeOnScreen()) {
            return new AuthSampleLoginScreen(driver);
        } else {return null;}
    }

}

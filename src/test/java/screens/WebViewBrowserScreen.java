package screens;

import general.BasePage;
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
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(browserDialogScreen);
    }

    /*
    UI ELEMENTS
     */

    @AndroidFindBy(id="com.huawei.browser:id/fake_dialog_layout")
    private WebElement browserDialogScreen;

    @AndroidFindBy(id="") //X=553  Y=700        X=800   Y=2025
    private WebElement gAccountLogged;


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

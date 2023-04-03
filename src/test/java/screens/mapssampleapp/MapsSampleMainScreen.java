package screens.mapssampleapp;

import general.BaseScreen;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import screens.authsampleapp.WebViewBrowserScreen;

public class MapsSampleMainScreen extends BaseScreen {

    public MapsSampleMainScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(topActionBar) && waitForMobElementToBeVisible(openMapBtn);
    }

    /*
    UI MAPS ELEMENTS
     */

    @AndroidFindBy(id="com.omh.android.maps.sample:id/action_bar")
    private WebElement topActionBar;

    @AndroidFindBy(className="android.widget.TextView")
    private WebElement topActionBarTxt;

    @AndroidFindBy(id="com.omh.android.maps.sample:id/button_first")
    private WebElement openMapBtn;



    /*
    Methods
     */

    public boolean printMainMapsElements() {
        boolean flag = true;

        try {
            System.out.println(getTextFromMobElement(topActionBarTxt));
            System.out.println(getTextFromMobElement(openMapBtn));
            flag = true;
        } catch (Exception e) {ErrorsManager.errNExpManager(e);}

        return flag;
    }

    public boolean openSampleMapsScreen(){
        return tapMobElement(openMapBtn) && implicityWaitTimeOnScreen();
    }

    /*
     RETURN-REDIRECT PAGE CALLS
    */
    public MapsDemoScreen mapsDemoScreen() {
        if(openSampleMapsScreen()) {
            return new MapsDemoScreen(this.driver);
        } else {return null;}
    }

}

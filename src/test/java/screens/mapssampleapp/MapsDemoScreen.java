package screens.mapssampleapp;

import general.BaseScreen;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MapsDemoScreen extends BaseScreen {

    public MapsDemoScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(mylocBtn) && waitForMobElementToBeVisible(markerBtn) &&
                waitForMobElementToBeVisible(shareBtn);
    }

    /*
    UI MAPS ELEMENTS
     */

    @AndroidFindBy(xpath="//android.widget.ImageView[@content-desc=\"My Location\"]")
    private WebElement mylocBtn;

    @AndroidFindBy(id="com.omh.android.maps.sample:id/marker_shadow_image_view")
    private WebElement markerBtn;

    @AndroidFindBy(id="com.omh.android.maps.sample:id/fab_share_location")
    private WebElement shareBtn;

    @AndroidFindBy(id="com.omh.android.maps.sample:id/textView_location")
    private WebElement locationTxt;

    /*
    Methods
     */

    public boolean printCurrentLocation() {
        boolean flag = false;

        try{
            System.out.println(getTextFromMobElement(locationTxt));
            flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }

        return flag;
    }

}

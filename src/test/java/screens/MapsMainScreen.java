package screens;

import general.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

public class MapsMainScreen extends BasePage {

    public MapsMainScreen(AndroidDriver<MobileElement> driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /*
     *
     *  Google maps UI elements found to validate and perform actions
     *
     *
     */

    @AndroidFindBy(id="com.google.android.apps.maps:id/search_omnibox_text_box")
    protected MobileElement searchOmniboxTxt;

    @AndroidFindBy(id="com.google.android.apps.maps:id/search_omnibox_edit_text")
    protected MobileElement enableSearchOmniboxTxt;

    @AndroidFindBy(id="com.google.android.apps.maps:id/watermark_image")
    protected MobileElement googWaterMarkImage;

    @AndroidFindBy(id="com.google.android.apps.maps:id/mylocation_button")
    protected MobileElement myLocationBtn;

    @AndroidFindBy(xpath="//android.view.View[@content-desc=\"Directions to Ciudad Colón\"]")
    protected MobileElement directionsBtn;

    @Override
    public boolean verifyLoads() {
        return waitForElementToBeVisible(searchOmniboxTxt) && waitForElementToBeVisible(googWaterMarkImage)
                && waitForElementToBeVisible(myLocationBtn);
    }

    public boolean waitForAFewSecondsOnScreen() {
        return implicityWaitTimeOnScreen();
    }

    public boolean typeAnAddressInOmniBoxSearch(String location) {
        return tapElement(searchOmniboxTxt) &&
                sendTextOnCleanElement(enableSearchOmniboxTxt, location) &&
                pressKeyboardKey(Keys.ENTER);
    }

    public boolean navigateToCC(){
        return tapElement(directionsBtn);
    }
}

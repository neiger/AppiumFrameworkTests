package screens;

import general.BasePage;
import general.TestUtilities;
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

    @AndroidFindBy(id="com.google.android.apps.maps:id/explore_tab_home_bottom_sheet")
    protected MobileElement mapsViewContainer;


    @AndroidFindBy(id="com.google.android.apps.maps:id/home_bottom_sheet_container") //com.google.android.apps.maps:id/explore_tab_home_bottom_sheet
    protected MobileElement doubleTapOnMap;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@content-desc=\"Explore\"]/android.widget.FrameLayout/android.widget.ImageView")
    protected MobileElement exploreBtn;

    @AndroidFindBy(xpath="//android.support.v7.widget.RecyclerView[@content-desc=\"Explore this area\"]/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout")
    protected MobileElement exploreAreaFrame;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@content-desc=\"Photo of Central Market Curridabat\"]/android.widget.ImageView")
    protected MobileElement imageViewerScrollTo;

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

    public boolean doubleTapOnElement(){
        return doubleTapOnElement(doubleTapOnMap);
    }

    public boolean doubleTapOnScreenWithXY(int getStartX, int getStartY){
        return doubleTapOnScreenWithCoordinatesXY(doubleTapOnMap, getStartX, getStartY);
    }

    public boolean swipeOnScreenWithCoordinates(int getStartX, int getStartY, int getEndX, int getEndY) {
        return swipeOnScreenWithCoordinatesXxYy(getStartX, getStartY, getEndX, getEndY);
    }

    public boolean scrollToAnElement() {
        try {
            tapElement(exploreBtn);
            waitForAFewSecondsOnScreen();
            tapElement(exploreAreaFrame);
            waitForAFewSecondsOnScreen();
            return scrollToAnElementOnScreen(imageViewerScrollTo.toString()) && pressKeyboardKey(Keys.BACK_SPACE);
        } catch (Exception e) {
            TestUtilities.errorsAndExceptionsManagement(e); return false;
        }

    }

    public boolean zoomInzoomOuOnMapsScreen() {
        return zoomInOutOnScreen(mapsViewContainer) && waitForAFewSecondsOnScreen() &&
                zoomInOutOnScreen(mapsViewContainer) && waitForAFewSecondsOnScreen() &&
                zoomInOutOnScreen(mapsViewContainer) && waitForAFewSecondsOnScreen();
    }

}

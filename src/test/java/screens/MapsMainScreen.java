package screens;

import general.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MapsMainScreen extends BasePage {

    public MapsMainScreen(AndroidDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /*
     *
     *  Google Maps UI elements found to validate and perform actions
     *
     *
     */

    @AndroidFindBy(id="com.google.android.apps.maps:id/search_omnibox_text_box")
    private WebElement searchOmniboxTxt;

    @AndroidFindBy(id="com.google.android.apps.maps:id/search_omnibox_edit_text")
    private WebElement enableSearchOmniboxTxt;

    @AndroidFindBy(id="com.google.android.apps.maps:id/watermark_image")
    private WebElement googWaterMarkImage;

    @AndroidFindBy(id="com.google.android.apps.maps:id/mylocation_button")
    private WebElement myLocationBtn;

    @AndroidFindBy(id="com.google.android.apps.maps:id/explore_tab_home_bottom_sheet")
    private WebElement mapsViewContainer;


    @AndroidFindBy(id="com.google.android.apps.maps:id/home_bottom_sheet_container") //com.google.android.apps.maps:id/explore_tab_home_bottom_sheet
    private WebElement doubleTapOnMap;

    @AndroidFindBy(id="com.google.android.apps.maps:id/navigation_bar_item_large_label_view")
    private WebElement exploreBtnTxt;

    @AndroidFindBy(xpath="//android.widget.FrameLayout[@content-desc=\"Explore\"]/android.widget.FrameLayout/android.widget.ImageView")
    private WebElement exploreBtn;

    @AndroidFindBy(xpath="//android.support.v7.widget.RecyclerView[@content-desc=\"Explore this area\"]/android.widget.FrameLayout[1]")
    private WebElement exploreAreaFrame;

    @Override
    public boolean verifyLoads() {
        return waitForMobElementToBeVisible(searchOmniboxTxt) && waitForMobElementToBeVisible(googWaterMarkImage)
                && waitForMobElementToBeVisible(myLocationBtn);
    }

    public boolean waitForAFewSecondsOnScreen() {
        return implicityWaitTimeOnScreen();
    }

    public boolean typeAnAddressInOmniBoxSearch(String location) {
        return tapMobElement(searchOmniboxTxt) &&
                sendTextOnEmptyMobElement(enableSearchOmniboxTxt, location) &&
                pressAndroidKey(AndroidKey.ENTER);
    }

    public boolean doubleTapOnElement(){
        return doubleTapOnMobElement(doubleTapOnMap);
    }

    public boolean doubleTapOnScreenWithXY(int getStartX, int getStartY){
        return doubleTapOnScreenWithCoordinatesXY(doubleTapOnMap, getStartX, getStartY);
    }

    public boolean horizontalSwipeOnScreenWithCoordinates() {
        return horizontalSwipeOnScreenXY(mapsViewContainer);
    }

    public boolean verticalSwipeOnScreenWithCoordinates() {
        return verticalSwipeOnScreenXY(mapsViewContainer);
    }

    public boolean zoomInOnScreenCoordinates() {
        return zoomInOnScreenXY(mapsViewContainer);// && implicityWaitTimeOnScreenManual(5)
                //zoomOutOnScreenXY(mapsViewContainer);// && implicityWaitTimeOnScreenManual(5);
    }

    public boolean openExploreBtnElement(int getStartX, int getStartY, int getEndX, int getEndY) {
        return tapMobElement(exploreBtn) && tapMobElement(exploreAreaFrame) && waitForAFewSecondsOnScreen();
    }

    public boolean verifyTextDisplayedOnElement(String txt) {
        return verifyTextOnMobElement(exploreBtnTxt, txt);
    }

    public boolean getTextFromElement() {
        boolean flag = false;
        String val = getTextFromMobElement(exploreBtnTxt);
        if(!val.isEmpty()){
            System.out.println("The text retrieved is: [" + val + "]");
            flag = true;
        }
        return flag;
    }
}

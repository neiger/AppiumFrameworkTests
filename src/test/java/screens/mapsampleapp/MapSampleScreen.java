package screens.mapsampleapp;

import general.BaseScreen;
import general.ErrorsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MapSampleScreen extends BaseScreen {

    public MapSampleScreen(AndroidDriver driver) {
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

    @AndroidFindBy(id="com.omh.android.maps.sample:id/frameLayout_mapContainer")
    private WebElement mapFrameLayoutContainer;

    @AndroidFindBy(id="com.omh.android.maps.sample:id/textView_coordinate")
    private WebElement txtCoordinates;

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

    public boolean swipeOnDeviceScreen() {
        return horizontalSwipeOnScreenXY(mapFrameLayoutContainer) && verticalSwipeOnScreenXY(mapFrameLayoutContainer);
    }

    public boolean tapMyLocationBtn() {
        return tapMobElement(mylocBtn) && implicityWaitTimeOnScreen();
    }

    public boolean customXxSwipeOnScreen(int getStartX, int getEndX) {
        return customUsersSwipeXLoc(mapFrameLayoutContainer, getStartX, getEndX);
    }

    public boolean customYySwipeOnScreen(int getStartY, int getEndY) {
        return customUsersSwipeYLoc(mapFrameLayoutContainer, getStartY, getEndY);
    }

    public boolean pinMovesGetLoc(int getStartX, int getEndX) {
        boolean flag = false;

        try{
            customXxSwipeOnScreen(getStartX, getEndX);
            String loc1 = getTextFromMobElement(locationTxt);
            //System.out.println("First pin location: " + getTextFromMobElement(locationTxt));
            implicityWaitTimeOnScreen();
            customXxSwipeOnScreen(getStartX+50, getEndX-50);
            String loc2 = getTextFromMobElement(locationTxt);
            implicityWaitTimeOnScreen();
            //System.out.println("Second pin location: " + getTextFromMobElement(locationTxt));
            if(!loc1.equals(loc2)) {
                System.out.println("First pin location: " + loc1 + " does not equal " + loc2);
                flag = true;
            }
            //flag = true;
        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }

        return flag;
    }

    public boolean sharePinsLocation() {
        boolean flag = false;
        try{
            String latlong = getTextFromMobElement(locationTxt);
            tapMobElement(shareBtn);
            implicityWaitTimeOnScreen();
            if(verifyTextOnMobElement(txtCoordinates, latlong)){
                System.out.println("Share location: " + latlong + " does equal " + getTextFromMobElement(txtCoordinates));
                flag = true;
            }

        } catch (Exception e) {
            ErrorsManager.errNExpManager(e);
        }

        return flag;
    }


    public boolean doubleTapWithOneFingerXY(int getX, int getY){
        return doubleTapWithOneFingerOnScreenXY(getX, getY);
    }

    public boolean singleTapWith2FingersXY(int getX, int getY) {
        return singleTapWithTwoFingersOnScreenXY(getX, getY);
    }

    public boolean doubleTapWithOneFingerXYHoldAndSwipeUp(int getX, int getY) {
        return doubleTapWithOneFingerHoldAndSwipeUpOnScreenXY(getX, getY);
    }

    public boolean doubleTapWithOneFingerXYHoldAndSwipeDown(int getX, int getY) {
        return doubleTapWithOneFingerHoldAndSwipeDownOnScreenXY(getX, getY);
    }

    // PENDING IMPLEMENTATION

}

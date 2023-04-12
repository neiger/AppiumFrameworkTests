package tests.mapsampleapp;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.mapsampleapp.MapSampleScreen;
import screens.mapsampleapp.MapSampleHomeScreen;

public class MapSampleScreenTests extends MobileDriverManager {

    private MapSampleHomeScreen mapSampleHomeScreen;
    private MapSampleScreen mapSampleScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        mapSampleHomeScreen = new MapSampleHomeScreen(getDriver());
    }

    @Test
    public void FW_13_verifyThatMapSampleAppOpensCorrectly() {
        assertTrue(mapSampleHomeScreen.verifyLoads(), basicErrorMsg("Unable to load the main screen"));
        assertTrue(mapSampleHomeScreen.printMainMapsElements(), basicErrorMsg("Unable to print the elements"));
        assertTrue(mapSampleHomeScreen.openMapSampleScreen(), basicErrorMsg("Maps screen can't be opened"));
        assertAll();
    }

    @Test
    public void FW_33_verifyThatDeviceSupportSwipeGestures() {
     mapSampleScreen = mapSampleHomeScreen.tapAndOpenMapSampleScreen();
     assertTrue(mapSampleScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
     assertTrue(mapSampleScreen.printCurrentLocation(), basicErrorMsg("Unable to print current location"));
     assertTrue(mapSampleScreen.swipeOnDeviceScreen(), basicErrorMsg("Unable to swipe on map screen"));
     assertAll();
    }

    @Test
    public void FW_53_verifyTappingMyLocationBtnMovesToUsersLocation() {
        mapSampleScreen = mapSampleHomeScreen.tapAndOpenMapSampleScreen();
        assertTrue(mapSampleScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
        assertTrue(mapSampleScreen.tapMyLocationBtn(), basicErrorMsg("Unable to tap My Location button (GPS) icon"));
        assertAll();
    }

    @Test
    public void FW_54_verifyNavigatingAndTappingMyLocationBtnMovesToUsersLocation() {
        mapSampleScreen = mapSampleHomeScreen.tapAndOpenMapSampleScreen();
        assertTrue(mapSampleScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
        assertTrue(mapSampleScreen.customXxSwipeOnScreen(1000, 100), basicErrorMsg("Unable to swipe on map screen"));
        assertTrue(mapSampleScreen.tapMyLocationBtn(), basicErrorMsg("Unable to tap My Location button (GPS) icon"));
        assertAll();
    }

    @Test
    public void FW_72_verifyThatPinGetsUpdatedLocationEachTimeMoves() {
        mapSampleScreen = mapSampleHomeScreen.tapAndOpenMapSampleScreen();
        assertTrue(mapSampleScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
        assertTrue(mapSampleScreen.pinMovesGetLoc(100, 1000), basicErrorMsg("Unable to swipe on map screen"));
        assertAll();
    }

    @Test
    public void FW_73_verifyThatPinLocationMatchesWhenShares() {
        mapSampleScreen = mapSampleHomeScreen.tapAndOpenMapSampleScreen();
        assertTrue(mapSampleScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
        assertTrue(mapSampleScreen.sharePinsLocation(), basicErrorMsg("Both locations do not match"));
        assertAll();
    }
}
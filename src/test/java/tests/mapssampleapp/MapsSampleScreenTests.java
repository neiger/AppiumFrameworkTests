package tests.mapssampleapp;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.mapssampleapp.MapsDemoScreen;
import screens.mapssampleapp.MapsSampleMainScreen;

public class MapsSampleScreenTests extends MobileDriverManager {

    private MapsSampleMainScreen mapsSampleMainScreen;
    private MapsDemoScreen mapsDemoScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        mapsSampleMainScreen = new MapsSampleMainScreen(getDriver());
    }

    @Test
    public void verifyThatMapsLaunchCorrectly() {
        assertTrue(mapsSampleMainScreen.verifyLoads(), basicErrorMsg("Unable to load the main screen"));
        assertTrue(mapsSampleMainScreen.printMainMapsElements(), basicErrorMsg("Unable to print the elements"));
        assertTrue(mapsSampleMainScreen.openSampleMapsScreen(), basicErrorMsg("Maps screen can't be opened"));
        assertAll();
    }

    @Test
    public void verifyThatMapsViewIsDisplayedCorrectly() {
     mapsDemoScreen = mapsSampleMainScreen.mapsDemoScreen();
     assertTrue(mapsDemoScreen.verifyLoads(), basicErrorMsg("The maps screen was not loaded correctly"));
     assertTrue(mapsDemoScreen.printCurrentLocation(), basicErrorMsg("Unable to print current location"));

     assertAll();
    }

}
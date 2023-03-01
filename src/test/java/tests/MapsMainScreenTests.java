package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.MapsMainScreen;

public class MapsMainScreenTests extends MobileDriverManager {

    private MapsMainScreen mapsMainScreen;

    public MapsMainScreenTests() { }

    @BeforeMethod
    public void setMapsMainScreen() {
        mapsMainScreen = new MapsMainScreen(getDriver());
        assertTrue(mapsMainScreen.verifyLoads(), "[]  ----->  Maps Screen does not loaded");
    }

    @Test
    public void verifyMapsMainScreen() {
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Curridabat"), "[]  ----->  The field was not filled");
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Ciudad Colon"), "[]  ----->  The field was not filled");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }

    @Test
    public void verifySearchMapsWithLatAndLong() {
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("37.3732324,-121.9654005"), "[]  ----->  The field was not filled");
        assertFalse(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }
}

package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import screens.MapsMainScreen;

public class MapsMainScreenTests extends MobileDriverManager {

    private MapsMainScreen mapsMainScreen;
    private SoftAssert softAssert;

    public MapsMainScreenTests() {
        softAssert = new SoftAssert();
    }

    @BeforeMethod
    public void setMapsMainScreen() {
        mapsMainScreen = new MapsMainScreen(getDriver());
        softAssert.assertTrue(mapsMainScreen.verifyLoads(), "[]  ----->  Maps Screen does not loaded");
    }

    @Test
    public void verifyMapsMainScreen() {
        softAssert.assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Curridabat"), "[]  ----->  The field was not filled");
        softAssert.assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Ciudad Colon"), "[]  ----->  The field was not filled");
        softAssert.assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        softAssert.assertAll();
    }

    @Test
    public void verifySearchMapsWithLatAndLong() {
        softAssert.assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("37.3732324,-121.9654005"), "[]  ----->  The field was not filled");
        softAssert.assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        softAssert.assertAll();
    }
}

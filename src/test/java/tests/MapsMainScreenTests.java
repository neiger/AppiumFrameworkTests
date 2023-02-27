package tests;

import general.MobileDriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import screens.MapsMainScreen;

public class MapsMainScreenTests extends MobileDriverManager {

    private MapsMainScreen mapsMainScreen;

    public MapsMainScreenTests() {
    }

    @Test
    public void verifyMapsMainScreen() {
        mapsMainScreen = new MapsMainScreen(getDriver());
        Assert.assertTrue(mapsMainScreen.verifyLoads(), "[ERROR]    Maps Screen does not loaded");
        Assert.assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Curridabat"), "[ERROR]    The field was not filled");
        Assert.assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch("San Jose, Ciudad Colon"), "[ERROR]    The field was not filled");
        Assert.assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[ERROR]    The app did not halt on screen");
    }
}

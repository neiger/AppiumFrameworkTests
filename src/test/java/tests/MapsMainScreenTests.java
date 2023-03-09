package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
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

    @Test
    public void verifyDoubleTapOnMap() {
        System.out.println("[STARTING THE TEST]...");
        assertTrue(mapsMainScreen.doubleTapOnElement(), "[]  ----->  Double tap was not performed");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }

    @Parameters({"getStartX", "getStartY"})
    @Test
    public void verifyDoubleTapOnMapScreen(int getStartX, int getStartY) {
        System.out.println("[STARTING THE TEST]...");
        assertTrue(mapsMainScreen.doubleTapOnScreenWithXY(getStartX, getStartY), "[]  ----->  Double tap was not performed");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }

    @Parameters({"getStartX", "getStartY", "getEndX", "getEndY"})
    @Test
    public void verifySwipeOnScreenWithCoordinatesXxYy(int getStartX, int getStartY, int getEndX, int getEndY) {
        System.out.println("[STARTING THE TEST]...");
        assertTrue(mapsMainScreen.swipeOnScreenWithCoordinates(getStartX, getStartY, getEndX, getEndY), "[]  ----->  Drag N Drop was not performed");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertTrue(mapsMainScreen.swipeOnScreenWithCoordinates(getStartX, getStartY, getEndX, getEndY), "[]  ----->  Drag N Drop was not performed");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }

    @Test
    public void verifyScrollToAnElementOnScreen() {
        System.out.println("[STARTING THE TEST]...");
        assertTrue(mapsMainScreen.scrollToAnElement(), "[] ---->  Scroll down can't be performed");
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), "[]  ----->  The app did not halt on screen");
        assertAll();
    }

    @Test
    public void verifyZoomInAndZoomOutOnMapsScreen() {
        System.out.println("[STARTING THE TEST]...");
        assertTrue(mapsMainScreen.zoomInzoomOuOnMapsScreen(), "[ZOOM FEATURE] it fails");
        assertAll();
    }

}

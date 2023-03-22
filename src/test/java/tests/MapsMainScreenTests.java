package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import screens.MapsMainScreen;

import java.util.Arrays;
import java.util.List;

public class MapsMainScreenTests extends MobileDriverManager {

    private MapsMainScreen mapsMainScreen;
    private List<String> stringList;
    public MapsMainScreenTests() { }

    @BeforeMethod @Parameters({"address"})
    public void setMapsMainScreen(String address) {
        mapsMainScreen = new MapsMainScreen(getDriver());
        assertTrue(mapsMainScreen.verifyLoads(), basicErrorMsg("Maps Screen does not loaded"));
        stringList = Arrays.asList(address.split(";"));
    }

    @Test
    public void verifyMapsMainScreen() {
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch(stringList.get(0)), basicErrorMsg("The field was not filled"));
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch(stringList.get(1)), basicErrorMsg("The field was not filled"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Test
    public void verifySearchMapsWithLatAndLong() {
        assertTrue(mapsMainScreen.typeAnAddressInOmniBoxSearch(stringList.get(2)), basicErrorMsg("The field was not filled"));
        assertFalse(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Test
    public void verifyDoubleTapOnMap() {
        assertTrue(mapsMainScreen.doubleTapOnElement(), basicErrorMsg("Double tap was not performed"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Parameters({"getStartX", "getStartY"})
    @Test
    public void verifyDoubleTapOnMapScreen(int getStartX, int getStartY) {
        assertTrue(mapsMainScreen.doubleTapOnScreenWithXY(getStartX, getStartY), basicErrorMsg("Double tap was not performed"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Parameters({"getStartX", "getStartY", "getEndX", "getEndY"})
    @Test
    public void verifySwipeOnScreenWithCoordinatesXxYy(int getStartX, int getStartY, int getEndX, int getEndY) {
        assertTrue(mapsMainScreen.swipeOnScreenWithCoordinates(getStartX, getStartY, getEndX, getEndY), basicErrorMsg("Drag N Drop was not performed"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertTrue(mapsMainScreen.swipeOnScreenWithCoordinates(getStartX, getStartY, getEndX, getEndY), basicErrorMsg("Drag N Drop was not performed"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Test     @Parameters({"getStartX", "getStartY", "getEndX", "getEndY"})
    public void verifyUsersCanSwipeWhileExploring(int getStartX, int getStartY, int getEndX, int getEndY) {
        assertTrue(mapsMainScreen.openExploreBtnElement(getStartX, getStartY, getEndX, getEndY), basicErrorMsg("Scroll down can't be performed"));
        assertTrue(mapsMainScreen.waitForAFewSecondsOnScreen(), basicErrorMsg("The app did not halt on screen"));
        assertAll();
    }

    @Test
    public void verifyMultiTouchOnMapsScreen() {
        assertTrue(mapsMainScreen.multiTouchOnMapsScreen(), basicErrorMsg("Zoom feature fails"));
        assertAll();
    }

    @Test
    public void verifyTextDisplayedOnMobileElement() {
        String txt = "Explore";
        assertTrue(mapsMainScreen.verifyTextDisplayedOnElement(txt), basicErrorMsg("Unable to get the text"));
        assertTrue(mapsMainScreen.getTextFromElement(), basicErrorMsg("Unable to get the text"));
        assertAll();
    }

}

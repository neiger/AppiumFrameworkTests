package tests.authsampleapp;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import screens.authsampleapp.AuthSampleLoginScreen;
import screens.authsampleapp.WebViewBrowserScreen;

public class AuthSampleLoginScreenTests extends MobileDriverManager {

    private AuthSampleLoginScreen authSampleLoginScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        authSampleLoginScreen = new AuthSampleLoginScreen(getDriver());
        assertTrue(authSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        assertAll();
    }

    @Test
    public void FW_6_verifyThatAuthButtonIsDisplayed() {
        assertTrue(authSampleLoginScreen.verifySignInBtnDisplayed(), basicErrorMsg("Unable to get the button info"));
        assertAll();
    }


    @Test @Parameters({"deviceType"})
    public void FW_11_verifyThatUserIsReturnedToSampleAppInLoggedInState(String deviceType) {
        if(deviceType.equals("nonGMS")) {
            WebViewBrowserScreen webViewBrowserScreen = authSampleLoginScreen.signInFromBrowser();
            assertTrue(webViewBrowserScreen.verifySignPageLoads(), basicErrorMsg("The signIn web view was not loaded correctly"));
            assertTrue(webViewBrowserScreen.clickLoggedInAccountXY(540,700), basicErrorMsg("Unable to click on the XY location given"));
            authSampleLoginScreen = webViewBrowserScreen.returnAsSignInState(800,2025);
        } else {
            assertTrue(authSampleLoginScreen.verifySignInPopUpShown(), basicErrorMsg("Unable to shown the pop up account"));
        }
        assertTrue(authSampleLoginScreen.verifySignInState(), basicErrorMsg("The signed in state fails the validation"));
        //assertTrue(authSampleLoginScreen.getUsersDataInformationPrint(),basicErrorMsg("The data from the signed in user fails to be retrieved"));
        //assertTrue(authSampleLoginScreen.signOutTheApp(), basicErrorMsg("Unable to tap on LOGOUT button"));
        assertAll();
    }

    @Test
    public void FW_35_verifyThatUserDataIsRetrievedCorrectly() {
        assertTrue(authSampleLoginScreen.getUsersDataInformationPrint(),basicErrorMsg("The data from the signed in user fails to be retrieved"));
        assertAll();
    }

    @Test
    public void FW_12_verifyTheUserIsAbleToLogOutFromTheSession() {
        assertTrue(authSampleLoginScreen.signOutTheApp(), basicErrorMsg("Unable to tap on LOGOUT button"));
        assertAll();
    }

    @Test
    public void validateTapOusideModal() {
        assertTrue(authSampleLoginScreen.tapOutsideModal(), basicErrorMsg("It can't be tapped outside"));
        assertAll();
    }

}

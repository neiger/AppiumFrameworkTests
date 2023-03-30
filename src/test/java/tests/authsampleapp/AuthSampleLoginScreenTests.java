package tests.authsampleapp;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.authsampleapp.AuthSampleLoginScreen;
import screens.authsampleapp.WebViewBrowserScreen;

public class AuthSampleLoginScreenTests extends MobileDriverManager {

    private AuthSampleLoginScreen authSampleLoginScreen;
    private WebViewBrowserScreen webViewBrowserScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        authSampleLoginScreen = new AuthSampleLoginScreen(getDriver());
        assertTrue(authSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        assertAll();
    }

    @Test
    public void verifyUserCanSignedInTheAppFromLoggedAccountInABrowserTab() {
        webViewBrowserScreen = authSampleLoginScreen.signInFromBrowser();
        assertTrue(webViewBrowserScreen.verifySignPageLoads("nonGMS"), basicErrorMsg("The signIn web view was not loaded correctly"));
        authSampleLoginScreen = webViewBrowserScreen.returnAsSignInState();
        assertTrue(authSampleLoginScreen.verifySignInState(), basicErrorMsg("The signed in state fails the validation"));
        assertTrue(authSampleLoginScreen.getUsersDataInformationPrint(),basicErrorMsg("The data from the signed in user fails to be retrieved"));
        assertTrue(authSampleLoginScreen.signOutTheApp(), basicErrorMsg("Unable to tap on LOGOUT button"));
        assertAll();
    }

}

package tests;

import general.MobileDriverManager;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AuthSampleLoginScreen;
import screens.WebViewBrowserScreen;

public class AuthSampleLoginScreenTests extends MobileDriverManager {

    private AuthSampleLoginScreen authSampleLoginScreen;
    private WebViewBrowserScreen webViewBrowserScreen;

    @BeforeMethod
    public void setAuthSampleLoginScreen() {
        authSampleLoginScreen = new AuthSampleLoginScreen(getDriver());
        assertTrue(authSampleLoginScreen.verifyLoads(), basicErrorMsg("Unable to load the screen"));
        assertTrue(authSampleLoginScreen.tapOnLoginBtn(), basicErrorMsg("Unable to tap on the button"));
        //webViewBrowserScreen = authSampleLoginScreen.signInFromBrowser();
        assertAll();
    }

    @Test
    public void verifyUserCanSignInFromWebViewBrowserScreen() {
        assertTrue(authSampleLoginScreen.waitUntilUsersLogsInBrowserView(), "Halt fails in the app");
        //authSampleLoginScreen = webViewBrowserScreen.returnAsSignInState(470, 770);
        assertTrue(authSampleLoginScreen.verifySignInState(), basicErrorMsg("Unable to load the screen"));
        assertTrue(authSampleLoginScreen.getUsersDataInformationPrint(), basicErrorMsg("An error has occurred to retrieve the data"));
        assertAll();
    }
}

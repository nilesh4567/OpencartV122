package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {


@Test(groups = {"Sanity","Master"})
    public void verify_login(){

          logger.info("**********Statring  TC002_LoginTest******************");

          // Homepage
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            //Loginpage

            LoginPage lp = new LoginPage(driver);
            lp.setTxtEmail(p.getProperty("email"));
            lp.setpassword(p.getProperty("password"));
            lp.clickLogin();

            //My Account

            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetpage = macc.isMyAccountPageExits();

             Assert.assertEquals(targetpage,true,"Login failed");
           // Assert.assertTrue(targetpage);
        } catch (Exception e) {
                Assert.fail();
        }
    logger.info("**********Finished_TC002_LoginTest******************");
    }

}

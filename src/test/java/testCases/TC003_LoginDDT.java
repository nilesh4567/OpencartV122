package testCases;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
  Data is valid------- login sucess----Test pass---------logout
*                       login failed----Test fail
* Data is Invalid------- login sucess----Test fail---------logout
 *                       login failed----Test Pass
 *  */

public class TC003_LoginDDT extends BaseClass {
@Test(dataProvider = "LoginData",dataProviderClass = DataProviders.class,groups = "Datadriven")
    public void verify_loginDDT(String email,String pwd,String exp){

    logger.info("************Starting_TC003_LoginDDT*************** ");
     try {
         HomePage hp = new HomePage(driver);
         hp.clickMyAccount();
         hp.clickLogin();

         //Loginpage

         LoginPage lp = new LoginPage(driver);
         lp.setTxtEmail(email);
         lp.setpassword(pwd);
         lp.clickLogin();

         //My Account

         MyAccountPage macc = new MyAccountPage(driver);
         boolean targetpage = macc.isMyAccountPageExits();

         if (exp.equalsIgnoreCase("Valid")) {
             if (targetpage == true) {
                 macc.clickLogout();
                 Assert.assertTrue(true);

             } else {
                 Assert.assertTrue(false);
             }
         }
         if (exp.equalsIgnoreCase("Invalid")) {
             if (targetpage == true) {
                 macc.clickLogout();
                 Assert.assertTrue(false);
             } else {

             }
         }
     } catch (RuntimeException e) {
         Assert.fail();;
     }
    logger.info("************Finished_TC003_LoginDDT***************");
    }

}

package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression","Master"})
   public void verify_AccountRegistration(){
        logger.info("***********Starting_TC_001_AccountRegistrationTest**************");
        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            logger.info("clicked on myaccount link");
            hp.clickRegister();
            logger.info("clicked on register link");
            AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
            logger.info("cproviding customer details");
            regpage.setFirstName(randomString().toUpperCase());
            regpage.setLastName(randomString().toUpperCase());
            regpage.setEmail(randomString() + "@gmail.com");// randomly generated the email
            regpage.setTelephone(randomNumber());

            String password = alphaNumeric();

            regpage.setPassword(password);
            regpage.setConfirmPassword(password);

            regpage.setPrivacyPolicy();
            regpage.clickContinue();

            logger.info("Validating expected message");

            String confmsg = regpage.getConfirmationMsg();
            if(confmsg.equals("Your Account Has Been Created!!")){
                Assert.assertTrue(true);
            }else{
                logger.error("Test failed");
                logger.debug("Debug logs");
                Assert.assertFalse(false);

            }
            Assert.assertEquals(confmsg, "Your Account Has Been Created!");
        } catch (RuntimeException e) {

            Assert.fail();
        }
        logger.info("***********Finished_TC_001_AccountRegistrationTest**************");

    }

}


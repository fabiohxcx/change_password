package fabiohideki.com.resetpassword;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by hidek on 14/03/2018.
 */
public class UtilsTest {


    /*  passwords that doesn't match
        "zxc12"; //too short
        "zxcvbnm123456789456"; // too long
        "qwerty"; // digit is required
        "qwerty*"; // special symbol not allow here*/
    String[] passError = {"zxc12", "zxcvbnm123456789456", "qwerty", "qwerty*"};


    //password that match
/*   "111111";
    "fabio1";*/
    String[] passValid = {"111111", "fabio1"};

    @Test
    public void isPasswordValid() throws Exception {

        for (String pass : passError) {
            boolean valid = Utils.isPasswordValid(pass);
            System.out.println("Password is valid : " + pass + " , " + valid);
            Assert.assertFalse(valid);
        }

        for (String pass : passValid) {
            boolean valid = Utils.isPasswordValid(pass);
            System.out.println("Password is valid : " + pass + " , " + valid);
            Assert.assertTrue(valid);
        }


    }

}
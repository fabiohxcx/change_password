package fabiohideki.com.resetpassword;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hidek on 12/03/2018.
 */

public class Utils {

    //at least one number, at least 6 characters and max 18
    private static final String PASSWORD_PATTERN = "(?=.*[0-9]).{6,18}";

    public static boolean isPasswordValid(String password) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();

    }

}

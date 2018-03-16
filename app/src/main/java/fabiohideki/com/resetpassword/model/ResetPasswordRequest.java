package fabiohideki.com.resetpassword.model;

/**
 * Created by hidek on 15/03/2018.
 */

public class ResetPasswordRequest {

    private String userId;
    private String password;
    private String confirmPassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

package fabiohideki.com.resetpassword.network;

import fabiohideki.com.resetpassword.model.ResetPasswordResponse;

/**
 * Created by hidek on 17/03/2018.
 */

public interface ResetPasswordCallback {

    void onResult(ResetPasswordResponse response);

}

package fabiohideki.com.resetpassword.network;

import fabiohideki.com.resetpassword.model.ResetPasswordResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hidek on 12/03/2018.
 */

public interface LoginService {

    @POST("/resetPassword")
    @FormUrlEncoded
    Call<ResetPasswordResponse> resetPassword(@Field("userId") String userId, @Field("newPassword") String newPassword);

}

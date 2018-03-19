package fabiohideki.com.resetpassword.repository;

import android.util.Log;

import fabiohideki.com.resetpassword.model.ResetPasswordRequest;
import fabiohideki.com.resetpassword.model.ResetPasswordResponse;
import fabiohideki.com.resetpassword.network.APIClient;
import fabiohideki.com.resetpassword.network.LoginService;
import fabiohideki.com.resetpassword.network.ResetPasswordCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hidek on 17/03/2018.
 */

public class LoginRepository {

    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest, final ResetPasswordCallback callback) {

        final ResetPasswordResponse[] resetPasswordResponse = new ResetPasswordResponse[1];


        LoginService loginService = APIClient.getRetrofitInstance().create(LoginService.class);

        Call<ResetPasswordResponse> call = loginService.resetPassword(resetPasswordRequest.getUserId(), resetPasswordRequest.getPassword());

        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                Log.d("ChangePasswordViewModel", "onResponse: " + ((ResetPasswordResponse) response.body()).getResult());

                resetPasswordResponse[0] = response.body();
                callback.onResult(resetPasswordResponse[0]);


            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

                resetPasswordResponse[0] = null;
                callback.onResult(resetPasswordResponse[0]);

            }
        });

        return resetPasswordResponse[0];
    }

}

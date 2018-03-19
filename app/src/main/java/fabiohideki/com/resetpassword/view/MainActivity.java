package fabiohideki.com.resetpassword.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindString;
import butterknife.ButterKnife;
import fabiohideki.com.resetpassword.R;
import fabiohideki.com.resetpassword.databinding.ActivityMainBinding;
import fabiohideki.com.resetpassword.model.ResetPasswordResponse;
import fabiohideki.com.resetpassword.viewmodel.ResetPasswordViewModel;

public class MainActivity extends AppCompatActivity {

    //Strings
    @BindString(R.string.error_service)
    String mErrorService;

    @BindString(R.string.error_network_or_service)
    String mErrorNetworkOrService;


    @BindString(R.string.retry)
    String mRetry;

    private String userId = "1000";

    private static final String RESULT_OK = "Ok";

    private ResetPasswordViewModel mViewModel;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        binding.setViewModel(mViewModel);

        mViewModel.resetPasswordResponseLiveData().observe(this, new Observer<ResetPasswordResponse>() {
            @Override
            public void onChanged(@Nullable ResetPasswordResponse resetPasswordResponse) {

                if (resetPasswordResponse != null && resetPasswordResponse.getResult().equalsIgnoreCase(RESULT_OK)) {
                    Intent intent = new Intent(MainActivity.this, CompletedRegistrationActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Snackbar snackbar = Snackbar.make(binding.svContent, mErrorService, Snackbar.LENGTH_LONG);

                    snackbar.setAction(mRetry, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mViewModel.onBtnChangePasswordClick();
                        }
                    });

                    snackbar.show();
                }

            }
        });

        binding.btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.onBtnChangePasswordClick();
            }
        });

    }


    /*  private void doLogin(String userId, String password) {

          LoginService loginService = APIClient.getRetrofitInstance().create(LoginService.class);

          Call<ResetPasswordResponse> call = loginService.resetPassword(userId, password);

          call.enqueue(new Callback<ResetPasswordResponse>() {
              @Override
              public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {

                  ResetPasswordResponse resetPasswordResponse = response.body();

                  if (resetPasswordResponse != null && resetPasswordResponse.getResult().equalsIgnoreCase(RESULT_OK)) {
                      Intent intent = new Intent(MainActivity.this, CompletedRegistrationActivity.class);
                      startActivity(intent);
                      finish();
                  } else {
                      mFrameLayoutProgressBar.setVisibility(View.GONE);

                      Snackbar snackbar = Snackbar.make(binding.svContent, mErrorService, Snackbar.LENGTH_LONG);

                      snackbar.setAction(mRetry, new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              confirm(null);
                          }
                      });

                      snackbar.show();
                  }


              }

              @Override
              public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {

                  mFrameLayoutProgressBar.setVisibility(View.GONE);

                  Snackbar snackbar = Snackbar.make(binding.svContent, mErrorNetworkOrService, Snackbar.LENGTH_LONG);

                  snackbar.setAction(mRetry, new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          confirm(null);
                      }
                  });

                  snackbar.show();

              }
          });
      }
  */

}

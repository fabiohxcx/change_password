package fabiohideki.com.resetpassword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fabiohideki.com.resetpassword.R;
import fabiohideki.com.resetpassword.Utils;
import fabiohideki.com.resetpassword.model.ResetPassword;
import fabiohideki.com.resetpassword.network.APIClient;
import fabiohideki.com.resetpassword.network.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.et_new_password)
    TextInputEditText mNewPassword;

    @BindView(R.id.til_new_password)
    TextInputLayout mNewPasswordInputLayout;

    @BindView(R.id.et_new_password_confirm)
    EditText mNewPasswordConfirm;

    @BindView(R.id.til_new_password_confirm)
    TextInputLayout mNewPasswordConfirmInputLayout;

    @BindView(R.id.iv_lock)
    ImageView mIvLock;

    @BindView(R.id.progressBarHolder)
    FrameLayout mFrameLayoutProgressBar;

    @BindView(R.id.sv_content)
    ScrollView mScrollViewContent;

    @BindView(R.id.bt_confirm)
    Button mBtConfirm;

    //Strings
    @BindString(R.string.error_empty_field)
    String mErrorEmptyFielfd;

    @BindString(R.string.error_password_rules)
    String mErrorPasswordRules;

    @BindString(R.string.error_passwords_not_match)
    String mErrorPasswordsNotMatch;

    @BindString(R.string.error_service)
    String mErrorService;

    @BindString(R.string.error_network_or_service)
    String mErrorNetworkOrService;


    @BindString(R.string.retry)
    String mRetry;

    private String userId = "1000";

    private static final String RESULT_OK = "Ok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNewPassword.addTextChangedListener(this);
        mNewPasswordConfirm.addTextChangedListener(this);

    }


    @OnClick(R.id.bt_confirm)
    public void confirm(View view) {

        String password = mNewPassword.getText().toString();
        String passwordConfirm = mNewPasswordConfirm.getText().toString();

        if (isPasswordsOk(password, passwordConfirm, true)) {

            hideErrors();

            mFrameLayoutProgressBar.setVisibility(View.VISIBLE);

            doLogin(userId, password);

        }
    }

    private boolean isPasswordsOk(String password, String passwordConfirm, boolean showError) {

        //check empty fields
        if (showError) {
            if (TextUtils.isEmpty(password)) {
                mNewPasswordInputLayout.setError(mErrorEmptyFielfd);
            }

            if (TextUtils.isEmpty(passwordConfirm)) {
                mNewPasswordConfirmInputLayout.setError(mErrorEmptyFielfd);
            }
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            return false;
        }

        if (!Utils.isPasswordValid(password)) {
            if (showError) {
                mNewPasswordInputLayout.setError(mErrorPasswordRules);
            }
            return false;
        }

        if (!password.equals(passwordConfirm)) {
            if (showError) {
                mNewPasswordInputLayout.setError(mErrorPasswordsNotMatch);
            }
            return false;
        }
        return true;
    }

    private void hideErrors() {
        mNewPasswordInputLayout.setError(null);
        mNewPasswordConfirmInputLayout.setError(null);

    }

    private void doLogin(String userId, String password) {

        LoginService loginService = APIClient.getRetrofitInstance().create(LoginService.class);

        Call<ResetPassword> call = loginService.resetPassword(userId, password);

        call.enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                ResetPassword resetPassword = response.body();

                if (resetPassword.getResult().equalsIgnoreCase(RESULT_OK)) {
                    Intent intent = new Intent(MainActivity.this, CompletedRegistrationActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    mFrameLayoutProgressBar.setVisibility(View.GONE);

                    Snackbar snackbar = Snackbar.make(mScrollViewContent, mErrorService, Snackbar.LENGTH_LONG);

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
            public void onFailure(Call<ResetPassword> call, Throwable t) {

                mFrameLayoutProgressBar.setVisibility(View.GONE);

                Snackbar snackbar = Snackbar.make(mScrollViewContent, mErrorNetworkOrService, Snackbar.LENGTH_LONG);

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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        String password = mNewPassword.getText().toString();
        String passwordConfirm = mNewPasswordConfirm.getText().toString();

        mNewPasswordInputLayout.setError(null);
        mNewPasswordConfirmInputLayout.setError(null);

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            mBtConfirm.setEnabled(false);
        } else {
            mBtConfirm.setEnabled(true);
        }

        if (isPasswordsOk(mNewPassword.getText().toString(), mNewPasswordConfirm.getText().toString(), false)) {
            mIvLock.setImageDrawable(getDrawable(R.drawable.ic_password_green));

        } else {
            mIvLock.setImageDrawable(getDrawable(R.drawable.ic_password));
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

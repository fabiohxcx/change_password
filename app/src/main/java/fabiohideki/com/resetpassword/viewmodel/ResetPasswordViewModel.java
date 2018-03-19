package fabiohideki.com.resetpassword.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import fabiohideki.com.resetpassword.R;
import fabiohideki.com.resetpassword.Utils;
import fabiohideki.com.resetpassword.model.ResetPasswordRequest;
import fabiohideki.com.resetpassword.model.ResetPasswordResponse;
import fabiohideki.com.resetpassword.network.ResetPasswordCallback;
import fabiohideki.com.resetpassword.repository.LoginRepository;

/**
 * Created by hidek on 15/03/2018.
 */

public class ResetPasswordViewModel extends AndroidViewModel implements ResetPasswordCallback {

    public final ObservableField<String> password = new ObservableField<>("");
    public final ObservableField<String> passwordConfirm = new ObservableField<>("");

    public final ObservableField<String> passwordError = new ObservableField<>();
    public final ObservableField<String> passwordConfirmError = new ObservableField<>();

    public ObservableBoolean isPasswordOk = new ObservableBoolean();

    public ObservableBoolean enableBtConfirm = new ObservableBoolean();

    public ObservableBoolean enableProgressbar = new ObservableBoolean(false);


    private MutableLiveData<ResetPasswordResponse> resetPasswordResponse;

    private String mErrorEmptyField;
    private String mErrorPasswordRules;
    private String mErrorPasswordsNotMatch;

    private Context context;

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);

        context = application.getApplicationContext();

        resetPasswordResponse = new MutableLiveData<>();

        mErrorEmptyField = context.getString(R.string.error_empty_field);
        mErrorPasswordRules = context.getString(R.string.error_password_rules);
        mErrorPasswordsNotMatch = context.getString(R.string.error_passwords_not_match);

    }


    public void onBtnChangePasswordClick() {

        if (isPasswordsOk(password.get(), passwordConfirm.get(), true)) {

            enableProgressbar.set(true);

            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
            resetPasswordRequest.setUserId("1000");
            resetPasswordRequest.setPassword(password.get());

            hideErrors();

            LoginRepository loginRepository = new LoginRepository();

            loginRepository.resetPassword(resetPasswordRequest, this);

        }

    }

    private boolean isPasswordsOk(String password, String passwordConfirm, boolean showError) {

        Log.d("ResetPasswordViewModel", "isPasswordsOk: " + password + " - " + passwordConfirm);
        //check empty fields
        if (showError) {
            if (TextUtils.isEmpty(password)) {
                passwordError.set(mErrorEmptyField);
            }

            if (TextUtils.isEmpty(passwordConfirm)) {
                passwordConfirmError.set(mErrorEmptyField);
            }
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfirm)) {
            return false;
        }

        if (!Utils.isPasswordValid(password)) {
            if (showError) {
                passwordError.set(mErrorPasswordRules);
            }
            return false;
        }

        if (!password.equals(passwordConfirm)) {
            if (showError) {
                passwordError.set(mErrorPasswordsNotMatch);
            }
            return false;
        }

        return true;

    }

    public LiveData<ResetPasswordResponse> resetPasswordResponseLiveData() {
        return resetPasswordResponse;
    }


    private void hideErrors() {
        passwordError.set(null);
        passwordConfirmError.set(null);
    }

    public void onTextChangedPassword(CharSequence charSequence, int i, int i1, int i2) {

        String p1 = charSequence.toString();
        String p2 = passwordConfirm.get();

        checkPasswordFields(p1, p2);

        Log.d("ResetPasswordViewModel", "checkPasswordFields: p1: " + p1 + " p2: " + p2 + "\n" + charSequence.toString());


    }

    public void onTextChangedPasswordConfirm(CharSequence charSequence, int i, int i1, int i2) {

        String p1 = password.get();
        String p2 = charSequence.toString();

        checkPasswordFields(p1, p2);

        Log.d("ResetPasswordViewModel", "checkPasswordFields: p1: " + p1 + " p2: " + p2 + "\n" + charSequence.toString());

    }

    public void checkPasswordFields(String p1, String p2) {
        hideErrors();

        if (TextUtils.isEmpty(p1) || TextUtils.isEmpty(p2)) {
            enableBtConfirm.set(false);
            //btConfirm.setEnabled(false);
        } else {
            enableBtConfirm.set(true);
            //btConfirm.setEnabled(true);
        }

        if (isPasswordsOk(p1, p2, false)) {
            isPasswordOk.set(true);
        } else {
            isPasswordOk.set(false);
        }

    }


    @Override
    public void onResult(ResetPasswordResponse response) {
        enableProgressbar.set(false);
        resetPasswordResponse.setValue(response);
    }
}

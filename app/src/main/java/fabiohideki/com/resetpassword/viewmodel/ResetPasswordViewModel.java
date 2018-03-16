package fabiohideki.com.resetpassword.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import fabiohideki.com.resetpassword.R;
import fabiohideki.com.resetpassword.Utils;
import fabiohideki.com.resetpassword.model.ResetPasswordResponse;

/**
 * Created by hidek on 15/03/2018.
 */

public class ResetPasswordViewModel extends AndroidViewModel {

    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> passwordConfirm = new ObservableField<>();

    public final ObservableField<String> passwordError = new ObservableField<>();
    public final ObservableField<String> passwordConfirmError = new ObservableField<>();

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

            hideErrors();

            //doLogin(userId, password);

        }

    }

    private boolean isPasswordsOk(String password, String passwordConfirm, boolean showError) {

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

    public void checkPasswordFields(Button btConfirm, ImageView ivLock) {
        hideErrors();

        if (TextUtils.isEmpty(password.get()) || TextUtils.isEmpty(passwordConfirm.get())) {
            btConfirm.setEnabled(false);
        } else {
            btConfirm.setEnabled(true);
        }

        if (isPasswordsOk(password.get(), passwordConfirm.get(), false)) {
            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
            ivLock.setImageDrawable(context.getDrawable(R.drawable.ic_password_green));
        } else {
            ivLock.setImageDrawable(context.getDrawable(R.drawable.ic_password));
        }

    }

}

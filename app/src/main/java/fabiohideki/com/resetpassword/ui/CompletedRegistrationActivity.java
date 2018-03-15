package fabiohideki.com.resetpassword.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import fabiohideki.com.resetpassword.R;

public class CompletedRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_registration);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @OnClick(R.id.bt_ok)
    public void OkAction(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

}

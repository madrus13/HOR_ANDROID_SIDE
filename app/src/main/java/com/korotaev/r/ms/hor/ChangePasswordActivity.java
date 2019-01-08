package com.korotaev.r.ms.hor;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.korotaev.r.ms.hor.WebServices.WebServiceMainService;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.testormlite.data.Entity.Session;

import static android.app.Activity.RESULT_CANCELED;

public class ChangePasswordActivity extends AppCompatActivity implements ServiceActivity {

    // UI references.
    private Button mSaveButton;
    private Button mCancelButton;
    private EditText mNewPassword;
    private EditText mNewPasswordRepeat;

    private static WebServiceMainService service = new WebServiceMainService();
    Session currentSession = null;

    public  void initViews(View v) {
        mSaveButton = findViewById(R.id.save_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mNewPassword = findViewById(R.id.new_password);
        mNewPasswordRepeat = findViewById(R.id.new_password_repeat);
    }


    public void OnClickListenerInit()
    {
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();

            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNewPassword.setError(null);
                mNewPasswordRepeat.setError(null);
                String newPassword = mNewPassword.getText().toString();
                String newPasswordRepeat = mNewPasswordRepeat.getText().toString();

                boolean cancel = false;
                View focusView = null;

                if (!TextUtils.isEmpty(newPassword) && !LoginActivity.isPasswordValid(newPassword) && cancel == false) {
                    mNewPassword.setError(getString(R.string.error_invalid_password));
                    focusView = mNewPassword;
                    cancel = true;
                }

                if (!TextUtils.isEmpty(newPasswordRepeat) && !LoginActivity.isPasswordValid(newPasswordRepeat) && cancel == false) {
                    mNewPasswordRepeat.setError(getString(R.string.error_invalid_password));
                    focusView = mNewPasswordRepeat;
                    cancel = true;
                }

                if (cancel == false && !newPasswordRepeat.equals(newPassword)) {
                    mNewPassword.setError(getString(R.string.error_passwords_not_repeated));
                    focusView = mNewPassword;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                }
                else {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initViews(null);
        OnClickListenerInit();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}

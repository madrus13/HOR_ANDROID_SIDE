package com.korotaev.r.ms.hor;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ComponentName;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.WebServices.ServiceObjectHelper;
import com.korotaev.r.ms.hor.WebServices.WebServiceMainService;
import com.korotaev.r.ms.hor.WebServices.serviceResult;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.testormlite.data.Entity.Session;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements ServiceActivity {


    private static final int REQUEST_READ_CONTACTS = 0;
    private  static  WebServiceMainService service = new WebServiceMainService();
    private static  Session currentSession;

    private UserLoginTask mAuthTask = null;
    private GetRegionsTask mGetRegionTask = null;


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox mAutoSignCheckBox;
    private Button mEmailSignInButton;
    private Button mRegistrationButton;

    private MyDBHelper myDBHelper = new MyDBHelper(this);

    /// Инициализация настроек по входу в систему
    private void initLogonInfoFromSavedPrefs()
    {
        String autoSignIn = Preferences.loadObjInPrefs(LoginActivity.this, Preferences.SAVED_AutoSignInState);

        mAutoSignCheckBox.setChecked(false);
        if (!autoSignIn.isEmpty() && autoSignIn.equals("1"))
        {
            mAutoSignCheckBox.setChecked(true);
        }

        if (mAutoSignCheckBox.isChecked()) {
            String login = Preferences.loadObjInPrefs(LoginActivity.this, Preferences.SAVED_Login);
            String pass = Preferences.loadObjInPrefs(LoginActivity.this, Preferences.SAVED_Pass);
            if (!login.isEmpty() && !pass.isEmpty()) {
                mEmailView.setText(login);
                mPasswordView.setText(pass);
            }
        }
    }

    public  void initViews(View v) {
        // Set up the login form.
        mAutoSignCheckBox = findViewById(R.id.autoSignCheckBox);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mRegistrationButton = findViewById(R.id.registration_in_button);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    public void OnClickListenerInit()
    {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mAutoSignCheckBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String autoSignState = "0";
                if (mAutoSignCheckBox.isChecked()) {
                    autoSignState = "1";
                }
                else {
                    mPasswordView.setText("");
                    Preferences.saveObjInPrefs(LoginActivity.this, Preferences.SAVED_Pass, mPasswordView.getText().toString());
                }

                Preferences.saveObjInPrefs(LoginActivity.this, Preferences.SAVED_AutoSignInState, autoSignState);
            }
        });

        mRegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetRegionTask = new GetRegionsTask();
                mGetRegionTask.execute((Void) null);
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews(null);
        OnClickListenerInit();
        populateAutoComplete();
        initLogonInfoFromSavedPrefs();

        if (mAutoSignCheckBox.isChecked()) {
            attemptLogin();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (mAutoSignCheckBox.isEnabled() && !email.isEmpty() && !password.isEmpty()) {
            Preferences.saveObjInPrefs(LoginActivity.this, Preferences.SAVED_Login, email);
            Preferences.saveObjInPrefs(LoginActivity.this, Preferences.SAVED_Pass, password);
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ViewHelper.showProgress(LoginActivity.this,mLoginFormView, mProgressView,true );
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 7;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }




    public class GetRegionsTask extends AsyncTask<Void, Void, Boolean> {

        public GetRegionsTask() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ServiceObjectHelper.getAllRegions(LoginActivity.this, "");
            return null;
        }
        @Override
        protected void onPostExecute(final Boolean success) {

        }

        @Override
        protected void onCancelled() {

        }
    }


        /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            myDBHelper.getHelper().addLog("INFO", "_____________________________");
            myDBHelper.getHelper().addLog("INFO", "LoginActivity START");
            serviceResult result;
            result = service.getSessionToken(mEmail,mPassword);

            if (result!=null && result.isSuccess) {
                //Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
                //Session newMyObj = gson.fromJson(result.resultObjectJSON, Session.class);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    currentSession = mapper.readValue(result.resultObjectJSON, Session.class);
                    if (currentSession!=null && !currentSession.getToken().isEmpty()) {
                        Preferences.saveObjInPrefs(LoginActivity.this, Preferences.SAVED_Session, currentSession.getToken());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    return true;
                } catch (IOException e) {

                }
            }
            // TODO: register the new account here.
            return false;
        }



        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            ViewHelper.showProgress(LoginActivity.this,mLoginFormView, mProgressView,false );

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            ViewHelper.showProgress(LoginActivity.this,mLoginFormView, mProgressView,false );
        }
    }
}


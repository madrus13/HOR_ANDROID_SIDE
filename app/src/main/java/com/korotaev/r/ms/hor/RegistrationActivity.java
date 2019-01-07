package com.korotaev.r.ms.hor;

import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.korotaev.r.ms.hor.AppHelpers.AdapterHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.WebServices.WebServiceMainService;
import com.korotaev.r.ms.hor.WebServices.serviceResult;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Session;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static WebServiceMainService service = new WebServiceMainService();
    Session currentSession = null;
    ArrayList<String> dataRegions = new ArrayList<String>();
    List<Region> regionList = null;
    Region selectedRegion = null;
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegistrationTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mPhoneView;
    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private Button mRegistrationButton;

    private View mProgressView;
    private View mLoginFormView;
    private Spinner mRegion;


    public  void initViews() {
        // Set up the form
        mLoginView = (AutoCompleteTextView) findViewById(R.id.login_name);
        mPhoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mRegistrationButton = findViewById(R.id.try_registration_in_button);
        mLoginFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.login_progress);
        mRegion = findViewById(R.id.regionList);

    }
    public void oOnClickListenerInit()
    {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        mRegistrationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        mRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (regionList.size() >= mRegion.getSelectedItemId()) {
                    selectedRegion = regionList.get((int) mRegion.getSelectedItemId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        oOnClickListenerInit();
        populateAutoComplete();

        regionList = Preferences.loadAllRegions(RegistrationActivity.this);

        for (Region item: regionList
             ) {
            dataRegions.add(item.getName());
        }

        AdapterHelper.adapterSimpleDataInit(
                this,
                mRegion,
                getString(R.string.regionSpinnerTitle),
                dataRegions,
                0);

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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mLoginView.setError(null);
        mPhoneView.setError(null);
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String login = mLoginView.getText().toString();
        long regionId = 0;

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !LoginActivity.isPasswordValid(password)) {
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
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError("Please enter phone number");
            focusView = mPhoneView;
            cancel = true;
        }
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError("Please enter the login name");
            focusView = mLoginView;
            cancel = true;
        }

        if (selectedRegion!=null && selectedRegion.getId() >= 0)
        {
            regionId = selectedRegion.getId();
        }
        else {
            cancel = true;
            focusView = mRegion;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ViewHelper.showProgress(this,mLoginFormView, mProgressView,true );
            mAuthTask = new UserRegistrationTask(login, regionId, phone, email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
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
                new ArrayAdapter<>(RegistrationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mPhone;
        private final String mLoginName;
        private final long   mRegionId;

        UserRegistrationTask(String login,long regionId, String phone, String email, String password) {
            mEmail = email;
            mPassword = password;
            mPhone = phone;
            mLoginName = login;
            mRegionId = regionId;
        }
        public  Boolean userRegistrationService()
        {
            User currentUser;
            serviceResult result;
            Boolean resOut = false;
            ViewHelper.showProgress(RegistrationActivity.this,mLoginFormView, mProgressView,true );

            result = service.insertUser(this.mLoginName,this.mRegionId, false,this.mPassword,this.mEmail,this.mPhone );
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    currentUser = mapper.readValue(result.resultObjectJSON, User.class);
                    if (
                            currentUser.getId() > 0 &&
                            currentUser.getEmail().equals(this.mEmail) &&
                            currentUser.getPhone().equals(this.mPhone) &&
                            currentUser.getName().equals(this.mLoginName)
                            ) {
                       currentSession =  userGetToken(mLoginName, mPassword);
                        if (currentSession!=null && !currentSession.getToken().isEmpty()) {
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    ViewHelper.showProgress(RegistrationActivity.this,mLoginFormView, mProgressView,false );
                }
            }
            return resOut;
        }

        public  Session userGetToken(String login, String password)
        {

            serviceResult result;
            Boolean resOut = false;
            result = service.getSessionToken(login,password );
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    currentSession = mapper.readValue(result.resultObjectJSON, Session.class);
                    if (!currentSession.getToken().isEmpty()) {
                        Preferences.saveObjInPrefs(RegistrationActivity.this, Preferences.SAVED_Session, currentSession.getToken());
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return currentSession;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                userRegistrationService();
            } catch (Exception e) {
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            ViewHelper.showProgress(RegistrationActivity.this,mLoginFormView, mProgressView,false );

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
            ViewHelper.showProgress(RegistrationActivity.this,mLoginFormView, mProgressView,false );
        }
    }
}


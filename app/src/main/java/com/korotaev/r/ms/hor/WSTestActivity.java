package com.korotaev.r.ms.hor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.korotaev.r.ms.hor.WebServices.WebServiceMainService;
import com.korotaev.r.ms.hor.WebServices.serviceResult;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Session;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class WSTestActivity extends AppCompatActivity {

    public static final String TEST = "test";
    private WSTestActivity.TestTask mTestTask = null;
    private static WebServiceMainService service = null;
    private static Session currentSession;
    public static int testIterationCounter = 0;
    public TextView textResult = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wstest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        service = new WebServiceMainService();

        mTestTask = new WSTestActivity.TestTask();
        mTestTask.execute((Void) null);
    }
    public  void myLogger (final String prefix,final String str)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(prefix, str);
                textResult.append(str + "\r\n" );
            }
        });

    }

    public class TestTask extends AsyncTask<Void, Void, Boolean> {



        TestTask() {

        }

        public void testToken(String name, String pass, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getSessionToken(name,pass);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    currentSession = mapper.readValue(result.resultObjectJSON, Session.class);
                    if (!currentSession.getToken().isEmpty()) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testToken:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testToken:: Fail");
            }
            testIterationCounter++;
        }


        public void testMessageType(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllMessageTypes(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Messagetype> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Messagetype[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testMessageType:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testMessageType:: Fail");
            }
            testIterationCounter++;
        }


        public void testRegions(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllRegions(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Region> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Region[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testRegions:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testRegions:: Fail");
            }
            testIterationCounter++;
        }

        public void testAchievmenttype(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllAchievmenttype(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Achievmenttype> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Achievmenttype[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testAchievmenttype:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testAchievmenttype:: Fail");
            }
            testIterationCounter++;
        }

        public void testRequestType(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllRequestType(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Requesttype> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Requesttype[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testRequestType:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testRequestType:: Fail");
            }
            testIterationCounter++;
        }

        public void testToolType(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllToolType(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Tooltypes> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, Tooltypes[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testToolType:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testToolType:: Fail");
            }
            testIterationCounter++;
        }

        public void testTransmissionType(String token, Boolean correctResult) {
            serviceResult result;
            Boolean resOut = false;
            result = service.getAllTransmissionType(token, null);
            if (result!=null && result.isSuccess) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<TransmissionType> msgTypesList = Arrays.asList(mapper.readValue(result.resultObjectJSON, TransmissionType[].class));

                    if (msgTypesList.size() > 0) {
                        resOut =  true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (resOut == correctResult) {
                myLogger(TEST, testIterationCounter + ") testTransmissionType:: Pass");
            }
            else {
                myLogger(TEST, testIterationCounter + ") testTransmissionType:: Fail");
            }
            testIterationCounter++;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Boolean resOut = false;

            testToken("mad@bad1.ru", "pass12345", false);
            testToken("mad@bad21.ru", "pass12345", false);
            testToken("mad@bad2.ru", "pass1234", false);
            testToken("madruskor@gmail.com", "000000", true);

            if (currentSession!=null) {
                testMessageType(currentSession.getToken(), true);
                testMessageType("INVALID_TOKEN", false);

                testRegions(currentSession.getToken(), true);
                testRegions("INVALID_TOKEN", false);

                testAchievmenttype(currentSession.getToken(), true);
                testAchievmenttype("INVALID_TOKEN", false);

                testRequestType(currentSession.getToken(), true);
                testRequestType("INVALID_TOKEN", false);

                testToolType(currentSession.getToken(), true);
                testToolType("INVALID_TOKEN", false);

                testTransmissionType(currentSession.getToken(), true);
                testTransmissionType("INVALID_TOKEN", false);
            }



           return resOut;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                finish();
            } else {

            }
        }

        @Override
        protected void onCancelled() {

        }
    }

}

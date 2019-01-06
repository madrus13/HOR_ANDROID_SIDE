package com.korotaev.r.ms.hor.fragment.ui.settings;

import android.app.LoaderManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.korotaev.r.ms.hor.ChangePasswordActivity;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.ListViewLoader;
import com.korotaev.r.ms.hor.MyDBHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.ActivityActions;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;
import static com.korotaev.r.ms.testormlite.data.ActivityActions.Pick_One_Item;
import static com.korotaev.r.ms.testormlite.data.ActivityActions.Pick_image;
import static com.korotaev.r.ms.testormlite.data.ActivityActions.Pick_tools;

public class SettingsFragment extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>,ServiceConnection, View.OnClickListener  {

    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;
    /**Target we publish for clients to send messages to IncomingHandler */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private SettingsViewModel mViewModel;
    private ImageView imageView;
    private Button loginView;
    private Button phoneView;
    private Button emailView;
    private Button passwordEdit;
    private View mProgressView;
    private View mMainView;
    private Spinner mRegion;
    private Spinner mTrType;
    private TextView mToolType;
    private TextView mCarModel;


    public static ArrayList<String> dataRegions = new ArrayList<String>();
    public static List<Region> regionList = null;
    Region selectedRegion = null;
    public int selectedRegionIndex = -1;


    ArrayList<String> dataTrType = new ArrayList<String>();
    public static List<TransmissionType> trTypeList = null;
    TransmissionType selectedtrType = null;
    public int selectedtrTypeIndex = -1;

    ArrayList<String> dataTools = new ArrayList<String>();
    public static List<Tooltypes> allToolTypesList ;

    public static User user;
    public static Auto auto;
    public static List<Tool> tools;


    public ArrayList<String> selectedToolsIds = new ArrayList<String>();


    static ObjectMapper mapper = new ObjectMapper();

    private MyDBHelper myDBHelper = new MyDBHelper(SettingsFragment.this.getContext());

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            msg.getData();
            if (msg.replyTo == mMessenger) {
                switch (msg.what) {
                    case SrvCmd.CMD_RegisterIntentServiceClientResp:

                        break;
                    case SrvCmd.CMD_EntitySyncResp:
                        break;

                    case SrvCmd.CMD_EntitySetUserInfoResp:

                        if(SettingsFragment.this.getContext()!=null)
                            Toast.makeText(SettingsFragment.this.getContext(), R.string.SuccessSave, Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        super.handleMessage(msg);
                }
            }

        }
    }


    public void sendComandToIntentService(int command)
    {
        // We want to monitor the service for as long as we are
        // connected to it.
        try {
            if (mService!=null) {
                Message msg = Message.obtain(null, command);
                msg.replyTo = mMessenger;
                mService.send(msg);
            }
        } catch (RemoteException e) {
            Toast.makeText(SettingsFragment.this.getContext(), R.string.remote_service_crashed,
                    Toast.LENGTH_SHORT).show();
            //showProgress(false);
        }
    }


    public void sendSetUserInfoComandToIntentService(int command,
                                                     long regionId,
                                                     String password,
                                                     String fileName,
                                                     String file,
                                                     long transmissionType,
                                                     String nameAuto,
                                                     long haveCable,
                                                     String toolTypeIds
    )
    {
        // We want to monitor the service for as long as we are
        // connected to it.
        try {
            if (mService!=null) {
                Message msg = Message.obtain(null, command);
                msg.replyTo = mMessenger;
                Bundle b = new Bundle();
                b.putLong("region", regionId );
                b.putString("password", password);
                b.putString("fileName", fileName);
                b.putString("file", file);

                b.putLong("transmissionType", transmissionType);
                b.putString("nameAuto", nameAuto);
                b.putLong("haveCable", haveCable);
                b.putString("toolTypeIds", toolTypeIds);

                msg.setData(b);
                mService.send(msg);
            }

        } catch (RemoteException e) {
            Toast.makeText(SettingsFragment.this.getContext(), R.string.remote_service_crashed,
                    Toast.LENGTH_SHORT).show();
            //showProgress(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_save) {

            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageInByte = baos.toByteArray();

            sendSetUserInfoComandToIntentService(
                                                    SrvCmd.CMD_EntitySetUserInfoReq,
                                                    selectedRegion.getId(),
                                                    passwordEdit.getText().toString(),
                                                    "_fname.jpeg" ,
                                                    imageInByte.toString(),
                                                    selectedtrType.getId(),
                                                    mCarModel.getText().toString(),
                                                    1,
                                                    TextUtils.join(",", selectedToolsIds)
                    );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public  void initViews(View v)
    {
        Date startDate = new Date();

        loginView = (Button) v.findViewById(R.id.LoginVal);
        emailView = (Button) v.findViewById(R.id.EmailVal);
        phoneView = (Button) v.findViewById(R.id.PhoneVal);
        passwordEdit = (Button) v.findViewById(R.id.change_pass_button);
        mRegion = (Spinner) v.findViewById(R.id.RegionVal);
        mTrType = (Spinner) v.findViewById(R.id.TransmissionTypeVal);
        mProgressView = (View) v.findViewById(R.id.main_activity_progress);
        mMainView = (View) v.findViewById(R.id.main_layout);
        mToolType = (TextView) v.findViewById(R.id.toolTypesVal);
        mCarModel = (TextView) v.findViewById(R.id.carModelVal);
        imageView = (ImageView) v.findViewById(R.id.UserImageView);

        userAutoInit();
        userInfoInit();
        userToolsInit();
        myDBHelper.getHelper().addLog(CODE_INFO,"initViews->" + ((new Date()).getTime() - startDate.getTime()));

    }

    public void userAutoInit()
    {
        auto = Preferences.loadCurrentUserAuto(getContext());

        if (auto!=null) {
            mCarModel.setText(auto.getName());

        }
    }


    public void userInfoInit()
    {
        Date startDate = new Date();
        user = Preferences.loadCurrentUserInfo(getContext());
        if (user!=null) {
            loginView.setText(user.getName());
            emailView.setText(user.getEmail());
            phoneView.setText(user.getPhone());
        }
        myDBHelper.getHelper().addLog(CODE_INFO,"userInfoInit->" + ((new Date()).getTime() - startDate.getTime()));
    }


    public void oOnClickListenerInit()
    {
        Date startDate = new Date();
        if (mToolType!=null) {
            mToolType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    start_activity_select_toolTypes();
                }
            });
        }

        if (passwordEdit!=null) {
            passwordEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SettingsFragment.this.getContext(), ChangePasswordActivity.class);
                    startActivityForResult(intent, ActivityActions.Pick_Change_password);
                }
            });
        }
        if (imageView!=null)  {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    //Тип получаемых объектов - image:
                    photoPickerIntent.setType("image/*");
                    //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                    startActivityForResult(photoPickerIntent, ActivityActions.Pick_image);
                }
            });
        }

        if (mTrType!=null) {
            mTrType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (trTypeList.size() >= mTrType.getSelectedItemId()) {
                        selectedtrType = trTypeList.get((int) mTrType.getSelectedItemId());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        if (mRegion!=null) {
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

        myDBHelper.getHelper().addLog(CODE_INFO,"oOnClickListenerInit->" + ((new Date()).getTime() - startDate.getTime()));
    }


    public void userToolsInit()
    {
        Date startDate = new Date();

        String toolTypeStr =  Preferences.loadObjInPrefs(this.getContext(), Preferences.SAVED_ToolType);


        try {
            allToolTypesList = Arrays.asList(mapper.readValue(toolTypeStr, Tooltypes[].class));
            for (Tooltypes el: allToolTypesList
                    ) {
                dataTools.add(el.getName());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        tools = Preferences.loadCurrentUserTools(getContext());
        ArrayList<String> toolsStr = new ArrayList<String>();

        if (tools!=null) {
            for (Tool el: tools
                    ) {
                //Tooltypes finded = allToolTypesList.stream().findFirst().filter(x -> x.getId().equals(el.getType().intValue())).get();
                //toolsStr.add(finded.getName());
            }
            mToolType.setText(TextUtils.join(" ", toolsStr));
        }

        myDBHelper.getHelper().addLog(CODE_INFO,"userToolsInit->" + ((new Date()).getTime() - startDate.getTime()));
    }


    public void initRegionView()
    {
        Date startDate = new Date();

        String regionsPrev =  Preferences.loadObjInPrefs(this.getContext(), Preferences.SAVED_Region);

        try {
            regionList = Arrays.asList(mapper.readValue(regionsPrev, Region[].class));
            dataRegions.clear();
            int currentIndex = 0;
            for (Region item: regionList
                    ) {
                dataRegions.add(item.getName());
                if (user!=null && (item.getId() == user.getRegion())) {
                    selectedRegionIndex = currentIndex;
                }
                currentIndex++;
            }

            // адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, dataRegions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mRegion.setAdapter(adapter);
            mRegion.setPrompt(getString(R.string.regionSpinnerTitle));
            mRegion.setSelection(selectedRegionIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }
        myDBHelper.getHelper().addLog(CODE_INFO,"initRegionView->" + ((new Date()).getTime() - startDate.getTime()));
    }


    private void start_activity_select_toolTypes() {
        Date startDate = new Date();

        if (dataTools.size() > 0) {
            Intent intent = new Intent(SettingsFragment.this.getContext(), ListViewLoader.class);
            intent.putExtra(ActivityActions.EXTRA_TITLE_LIST, R.string.selectToolTypes);
            intent.putStringArrayListExtra(ActivityActions.EXTRA_DATA_LIST, dataTools);
            intent.putExtra(ActivityActions.EXTRA_SELECT_MODE_CHOICE_TYPE, ListView.CHOICE_MODE_MULTIPLE);
            startActivityForResult(intent, ActivityActions.Pick_tools);
        }
        myDBHelper.getHelper().addLog(CODE_INFO,"start_activity_select_toolTypes->" + ((new Date()).getTime() - startDate.getTime()));
    }


    public void initTransmissionView()
    {
        Date startDate = new Date();
        String objPrev =  Preferences.loadObjInPrefs(this.getContext(), Preferences.SAVED_TransmissionType);

        try {
            trTypeList = Arrays.asList(mapper.readValue(objPrev, TransmissionType[].class));
            int currentIndex = 0;
            for (TransmissionType item: trTypeList
                    ) {
                dataTrType.add(item.getName());
              if (auto.getTransmissionType() == item.getId()) {
                  selectedtrTypeIndex = currentIndex;
              }
                currentIndex++;
            }

            // адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, dataTrType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mTrType.setAdapter(adapter);
            mTrType.setPrompt(getString(R.string.trTypeSpinnerTitle));
            mTrType.setSelection(selectedtrTypeIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }

        myDBHelper.getHelper().addLog(CODE_INFO,"initTransmissionView->" + ((new Date()).getTime() - startDate.getTime()));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Date startDate = new Date();
        View v =  inflater.inflate(R.layout.settings_fragment, container, false);
        setHasOptionsMenu(true);
        initViews(v);
        initRegionView();
        initTransmissionView();
        oOnClickListenerInit();

        Intent i = new Intent(SettingsFragment.this.getContext(), CmdService.class);
        getActivity().bindService(i,  SettingsFragment.this, Context.BIND_AUTO_CREATE);

        myDBHelper.getHelper().addLog(CODE_INFO,"onCreateView->" + ((new Date()).getTime() - startDate.getTime()));

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case Pick_image: {
                if (resultCode == RESULT_OK) {
                    try {

                        Uri selectedImageUri = data.getData();
                        imageView.setImageURI(selectedImageUri);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                break;
            }
            case Pick_One_Item: {
                if (resultCode == RESULT_OK) {
                    long selectedId = data.getLongExtra(ActivityActions.EXTRA_SELECTED_ID, -1);
                    if (selectedId != -1) {
                        selectedRegion = regionList.get((int) selectedId);
                        //mRegion.setText(selectedRegion.getName()  + getString(R.string.change_field));
                        // mRegion.setSelection(0);
                    }
                }
                break;
            }
            case Pick_tools: {
                if (resultCode == RESULT_OK)
                {
                    ArrayList<Integer> selectedId = data.getIntegerArrayListExtra(ActivityActions.EXTRA_SELECTED_ID);
                    if (selectedId.size() > 0) {
                        String selectedTools = "";
                        ArrayList<String> toolsStr = new ArrayList<String>();

                        for (Integer el : selectedId
                                )
                        {
                            if (allToolTypesList.size() > el)
                            {
                                selectedTools += allToolTypesList.get(el).getName() + " ";
                                selectedToolsIds.add(allToolTypesList.get(el).getId().toString());
                                toolsStr.add(allToolTypesList.get(el).getName());
                            }
                        }
                        mToolType.setText(TextUtils.join(" ", toolsStr));
                    } else {
                        myDBHelper.getHelper().addLog(CODE_INFO, "IncomingHandler-> Pick_Tools NO SELECTED ITEM");
                    }
                }
                break;

                }
                }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unbindService(SettingsFragment.this);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu_activity_main, menu);

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        // TODO Auto-generated method stub
       Date startDate = new Date();
        if (service!=null && mService == null) {
            mService = new Messenger(service);
            sendComandToIntentService(SrvCmd.CMD_RegisterIntentServiceClientReq);
        }
        myDBHelper.getHelper().addLog(CODE_INFO,"onServiceConnected->" + ((new Date()).getTime() - startDate.getTime()));
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}

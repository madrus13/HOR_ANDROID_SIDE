package com.korotaev.r.ms.hor.fragment.ui.fragment.ui.new_request;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.SpinnerAdapterHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;



public class NewRequestFragment extends Fragment implements ServiceActivity {

    private MyDBHelper myDBHelper = new MyDBHelper(getContext());
    private Spinner mReqType;
    private EditText mDescriptSituation;

    private NewRequestViewModel mViewModel;
    ArrayList<String> dataTrType = new ArrayList<String>();
    public static List<Requesttype> trTypeList = null;
    Requesttype selectedtrType = null;
    public int selectedtrTypeIndex = -1;
    static ObjectMapper mapper = new ObjectMapper();


    public static NewRequestFragment newInstance() {
        return new NewRequestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_request_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewRequestViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void initViews(View v) {

        mReqType = v.findViewById(R.id.RequestTypeVal);
        mDescriptSituation = v.findViewById(R.id.Description);

        initReqTypesView();
    }

    @Override
    public void OnClickListenerInit() {
        if (mReqType!=null) {
            mReqType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (trTypeList.size() >= mReqType.getSelectedItemId()) {
                        selectedtrType = trTypeList.get((int) mReqType.getSelectedItemId());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    public void initReqTypesView()
    {
        Date startDate = new Date();
        String objPrev =  Preferences.loadObjInPrefs(this.getContext(), Preferences.SAVED_RequestType);

        try {
            trTypeList = Arrays.asList(mapper.readValue(objPrev, Requesttype[].class));
            int currentIndex = 0;
            for (Requesttype item: trTypeList
            ) {
                dataTrType.add(item.getName());
                currentIndex++;
            }

            SpinnerAdapterHelper.adapterSimpleDataInit(this.getContext(), mReqType,getString(R.string.trTypeSpinnerTitle), dataTrType,selectedtrTypeIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }

        myDBHelper.getHelper().addLog(CODE_INFO,"initTransmissionView->" + ((new Date()).getTime() - startDate.getTime()));
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

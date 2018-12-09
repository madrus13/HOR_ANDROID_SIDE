package com.korotaev.r.ms.hor.fragment.ui.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private ImageView imageView;
    private TextView loginView;
    private TextView phoneView;
    private TextView emailView;


    ArrayList<String> dataRegions = new ArrayList<String>();
    List<Region> regionList = null;
    Region selectedRegion = null;
    private Spinner mRegion;
    private final int Pick_image = 1;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.settings_fragment, container, false);

        loginView = (TextView) v.findViewById(R.id.LoginVal);
        emailView = (TextView) v.findViewById(R.id.EmailVal);
        phoneView = (TextView) v.findViewById(R.id.PhoneVal);
        mRegion = (Spinner) v.findViewById(R.id.RegionValSpinner);

        String regionsPrev =  Preferences.loadObjInPrefs(this.getContext(), Preferences.SAVED_Region);
        ObjectMapper mapper = new ObjectMapper();
        try {
            regionList = Arrays.asList(mapper.readValue(regionsPrev, Region[].class));

            for (Region item: regionList
                    ) {
                dataRegions.add(item.getName());
            }

            // адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, dataRegions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mRegion.setAdapter(adapter);
            mRegion.setPrompt(getString(R.string.regionSpinnerTitle));
            mRegion.setSelection(0);
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        User user = Preferences.loadCurrentUserInfo(this.getContext());
        if (user!=null) {
            loginView.setText(user.getName());
            emailView.setText(user.getEmail());
            phoneView.setText(user.getPhone());
        }

        imageView = (ImageView) v.findViewById(R.id.UserImageView);
        if (imageView!=null)  {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    //Тип получаемых объектов - image:
                    photoPickerIntent.setType("image/*");
                    //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                    startActivityForResult(photoPickerIntent, Pick_image);
                }
            });
        }



        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK) {
                    try {

                        Uri selectedImageUri = data.getData();
                        imageView.setImageURI(selectedImageUri);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel

    }

}

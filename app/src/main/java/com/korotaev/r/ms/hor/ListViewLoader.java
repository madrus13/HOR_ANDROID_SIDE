package com.korotaev.r.ms.hor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.testormlite.data.Entity.Region;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewLoader extends Activity implements View.OnClickListener

{

   ListView lvMain;
   String[] names;
   SimpleCursorAdapter mAdapter;
    ArrayList<String> dataRegions = new ArrayList<String>();
    List<Region> regionList = null;
    Region selectedRegion = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_loader);

        String regionsPrev =  Preferences.loadObjInPrefs(this, Preferences.SAVED_Region);
        ObjectMapper mapper = new ObjectMapper();
        try {
            regionList = Arrays.asList(mapper.readValue(regionsPrev, Region[].class));

        for (Region item: regionList
                ) {
            dataRegions.add(item.getName());
        }
        lvMain = (ListView) findViewById(R.id.object_list);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.single_list_item, dataRegions);

        lvMain.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Button btnChecked = (Button) findViewById(R.id.btnChecked);
       // btnChecked.setOnClickListener((View.OnClickListener) this);

    }


    @Override
    public void onClick(View view) {

    }
}
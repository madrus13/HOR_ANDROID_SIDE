package com.korotaev.r.ms.hor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.korotaev.r.ms.testormlite.data.ActivityActions;

import java.util.ArrayList;

public class ListViewLoader extends Activity implements View.OnClickListener

{

   ListView lvMain;
    ArrayList<String> dataList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_loader);
        lvMain = findViewById(R.id.object_list);


        Intent intent = getIntent();

        String Title = intent.getStringExtra(ActivityActions.EXTRA_TITLE_LIST);
        dataList = intent.getStringArrayListExtra(ActivityActions.EXTRA_DATA_LIST);
        int selectType = intent.getIntExtra(ActivityActions.EXTRA_SELECT_MODE_CHOICE_TYPE, ListView.CHOICE_MODE_SINGLE );
        lvMain.setChoiceMode(selectType);

        setTitle(Title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.single_list_item, dataList);
        lvMain.setAdapter(adapter);

        Button btnChecked = (Button) findViewById(R.id.buttonChecked);
        btnChecked.setOnClickListener((View.OnClickListener) this);

    }


    @Override
    public void onClick(View view) {

    }
}
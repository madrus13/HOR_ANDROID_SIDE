package com.korotaev.r.ms.hor.AppHelpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class AdapterHelper {

    public static void adapterSimpleDataInit(Context context, Spinner spinner, String title, ArrayList<String> data, int selectIndex)
    {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt(title);
        spinner.setSelection( selectIndex);
    }
}

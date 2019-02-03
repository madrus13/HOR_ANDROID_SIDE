package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.content.Context;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.Request;

import java.util.ArrayList;

public class RequestStorage {
    private static MyDBHelper myDBHelper;
    public RequestStorage(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    public ArrayList<Request> getData(Long region, int requestedStartPosition, int requestedLoadSize) {
        ArrayList<Request> objList = new ArrayList<>();

        objList.addAll(myDBHelper.getHelper().getRequestItemBlock(region,requestedStartPosition,requestedLoadSize));

         return objList.size() > 0 ? objList: null;
    }
}

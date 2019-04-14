package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.content.Context;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.Request;

import java.util.ArrayList;
import java.util.Collection;

public class CustomStorage<T> {

    private static MyDBHelper myDBHelper;

    public CustomStorage(Context context) {
        myDBHelper = new MyDBHelper(context);
    }



    public ArrayList<T> getData(Long region, int requestedStartPosition, int requestedLoadSize) {
        ArrayList<T> msgList = new ArrayList<T>();

        if (this.getClass().equals(Request.class)) {
            boolean b = msgList.addAll((Collection<? extends T>) myDBHelper.getHelper().getRequestItemBlock(region, requestedStartPosition, requestedLoadSize));
        }
        if (this.getClass().equals(Message.class)) {
            msgList.addAll((Collection<? extends T>) myDBHelper.getHelper().getMessageItemBlock(region,requestedStartPosition,requestedLoadSize));
        }


         return msgList.size() > 0 ? msgList: null;
    }
}

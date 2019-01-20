package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.content.Context;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.util.ArrayList;

public class MessageStorage {
    private static MyDBHelper myDBHelper;
    public MessageStorage(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    public ArrayList<Message> getData(Long region, int requestedStartPosition, int requestedLoadSize) {
        ArrayList<Message> msgList = new ArrayList<Message>();

         msgList.addAll(myDBHelper.getHelper().getMessageItemBlock(region,requestedStartPosition,requestedLoadSize));

         return msgList.size() > 0 ? msgList: null;
    }
}

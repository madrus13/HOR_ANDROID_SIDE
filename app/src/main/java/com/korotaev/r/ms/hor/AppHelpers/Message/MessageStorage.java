package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.content.Context;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {
    private static MyDBHelper myDBHelper;
    public MessageStorage(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    public List<Message> getData(int requestedStartPosition, int requestedLoadSize) {
        ArrayList<Message> msgList = new ArrayList<Message>();

         msgList.addAll(myDBHelper.getHelper().getMessageItemBlock(requestedStartPosition,requestedLoadSize));

         return msgList;
    }
}

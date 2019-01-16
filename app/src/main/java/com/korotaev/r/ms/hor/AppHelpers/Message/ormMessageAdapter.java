package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.ArrayList;
import java.util.List;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;


public class ormMessageAdapter extends PagedListAdapter<Message, ormMessageViewHolder> {

    public  Context context;
    private static MyDBHelper myDBHelper;
    List<Message> messages = new ArrayList<Message>();

    public ormMessageAdapter(DiffUtil.ItemCallback<Message> diffUtilCallback, Context context) {
        super(diffUtilCallback);
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    public void initMydDBHelper()
    {
        if (myDBHelper ==null) {
            myDBHelper =  new MyDBHelper(this.context);
        }
    }

    @NonNull
    @Override
    public ormMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView;


        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        if (viewType == ViewHelper.OUTPUT_MESSAGE) {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.output_message, parent, false);
        } else {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.input_message, parent, false);
        }

        ormMessageViewHolder holder = new ormMessageViewHolder(convertView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ormMessageViewHolder holder, int position) {
        holder.bind(getItem(position),getItemViewType(position));
    }

    @Override
    public int getItemViewType(int position) {
        Message message = getItem(position);
        if (message != null) {
            User user = Preferences.loadCurrentUserInfo(context);

            if (user != null && (message.getCreateUser() == user.getId())) {
                return ViewHelper.OUTPUT_MESSAGE;
            } else {
                return ViewHelper.INPUT_MESSAGE;
            }
        }
        return ViewHelper.SYSTEM_MESSAGE;
    }

    @Nullable
    @Override
    protected Message getItem(int position) {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageItem(position);
    }

    @Override
    public int getItemCount() {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageCount();
    }
}


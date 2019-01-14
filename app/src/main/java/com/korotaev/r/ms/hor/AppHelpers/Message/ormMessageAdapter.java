package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.util.ArrayList;
import java.util.List;


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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_message, parent, false);
        ormMessageViewHolder holder = new ormMessageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ormMessageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Nullable
    @Override
    protected Message getItem(int position) {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageItem(position + 1);
    }

    @Override
    public int getItemCount() {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageCount();
    }
}


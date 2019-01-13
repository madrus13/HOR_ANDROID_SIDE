package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;


public class ormMessageAdapter extends PagedListAdapter<Message, ormMessageViewHolder> {

    public ormMessageAdapter(DiffUtil.ItemCallback<Message> diffUtilCallback) {
        super(diffUtilCallback);
    }

    @NonNull
    @Override
    public ormMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.output_message, parent, false);
        ormMessageViewHolder holder = new ormMessageViewHolder(view);
        holder.messageBody.setText("sdsd");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ormMessageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

}


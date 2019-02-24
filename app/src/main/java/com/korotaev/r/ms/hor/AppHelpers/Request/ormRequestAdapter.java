package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.NetworkImageViewAdapter;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.EntityConstVariables;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.User;


public class ormRequestAdapter extends PagedListAdapter<Request, ormRequestViewHolder> {

    public  Context context;
    private static MyDBHelper myDBHelper;
    private User currentUser;
    private NetworkImageViewAdapter mImageLoader;

    public ormRequestAdapter(
                             DiffUtil.ItemCallback<Request> diffUtilCallback,
                             Context context,
                             User currentUser,
                             NetworkImageViewAdapter mImageLoader) {
        super(diffUtilCallback);
        this.context = context;
        this.currentUser = currentUser;
        this.mImageLoader = mImageLoader;
    }

    /*
    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }
    */

    public void initMydDBHelper()
    {
        if (myDBHelper ==null) {
            myDBHelper =  new MyDBHelper(this.context);
        }
    }

    @NonNull
    @Override
    public ormRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = null;

        if (viewType == ViewHelper.OUTPUT_MESSAGE) {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.output_message, parent, false);
        }
        else if (viewType == ViewHelper.INPUT_MESSAGE) {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.input_message, parent, false);
        } else if (viewType == ViewHelper.SYSTEM_MESSAGE) {
            convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.system_message, parent, false);
        }


        ormRequestViewHolder holder = new ormRequestViewHolder(convertView, mImageLoader);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ormRequestViewHolder holder, int position) {
        holder.bind(getItem(position),getItemViewType(position));
    }

    @Override
    public int getItemViewType(int position) {
        Request request = getItem(position);
        int result = ViewHelper.OUTPUT_MESSAGE;
        if (request != null) {
            User user = Preferences.loadCurrentUserInfo(context);

            if (user != null && (request.getCreationUser() == user.getId()) && request.getType() == EntityConstVariables.MESSAGE_TYPE_REGION) {
                result = ViewHelper.OUTPUT_MESSAGE;
            }
        }
        if (request.getType() == EntityConstVariables.MESSAGE_TYPE_BROADCAST) {
            result = ViewHelper.SYSTEM_MESSAGE;
        }
        return result;
    }

    @Nullable
    @Override
    protected Request getItem(int position) {
        initMydDBHelper();
        return myDBHelper.getHelper().getRequestItem(position);
    }

    @Override
    public int getItemCount() {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageCount(currentUser.getRegion());
    }
}


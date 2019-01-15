package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

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

        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.input_message, parent, false);
        ormMessageViewHolder holder = new ormMessageViewHolder(convertView);

        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = getItem (viewType);

        if (message!=null) {
            User user = Preferences.loadCurrentUserInfo(context);

            if (user!=null && (message.getCreateUser() == user.getId() )) {
                convertView = messageInflater.inflate(R.layout.output_message, null);
                holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                convertView.setTag(holder);
                holder.messageBody.setText(message.getText());
            } else {
                convertView = messageInflater.inflate(R.layout.input_message, null);
                holder.avatar = (View) convertView.findViewById(R.id.avatar);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                convertView.setTag(holder);

                holder.name.setText(message.getText());
                holder.messageBody.setText(message.getText());
                GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                drawable.setColor(Color.parseColor("yellow"));
            }
        }


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
        return myDBHelper.getHelper().getMessageItem(position);
    }

    @Override
    public int getItemCount() {
        initMydDBHelper();
        return myDBHelper.getHelper().getMessageCount();
    }
}


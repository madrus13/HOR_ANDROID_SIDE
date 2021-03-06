package com.korotaev.r.ms.hor.AppHelpers.Message;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.korotaev.r.ms.hor.AppHelpers.NetworkImageViewAdapter;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.text.SimpleDateFormat;

public class ormMessageViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView messageBody;
    public TextView message_time;
    public NetworkImageView image;
    private NetworkImageViewAdapter networkImageViewAdapter;

    public ormMessageViewHolder(View itemView, NetworkImageViewAdapter networkImageViewAdapter) {
        super(itemView);
        this.networkImageViewAdapter = networkImageViewAdapter;
    }

    public void bind(Message item, int viewType) {

        if (item == null ) {

        } else {

            messageBody = itemView.findViewById(R.id.message_body);
            message_time = itemView.findViewById(R.id.message_time);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.avatar);

            if (messageBody!=null) messageBody.setText(item.getText());

            switch (viewType)
            {
                case ViewHelper.INPUT_MESSAGE:
                    name = itemView.findViewById(R.id.name);
                    if (name!=null) name.setText(item.getCreateUserName());
                    if (message_time!=null && item.getCreationDate()!=null) {
                        message_time.setText((new SimpleDateFormat("hh:mm")).format(item.getCreationDate()));
                    }
                    break;
                case ViewHelper.OUTPUT_MESSAGE:
                    if (message_time!=null && item.getCreationDate()!=null) {
                        message_time.setText((new SimpleDateFormat("hh:mm")).format(item.getCreationDate()));
                    }

                    networkImageViewAdapter.setImageFromServicePath(item.getMessagePhotoPath(), image);
                    break;
                case ViewHelper.SYSTEM_MESSAGE:
                    break;
                default:
                    break;
            }


        }
    }
}

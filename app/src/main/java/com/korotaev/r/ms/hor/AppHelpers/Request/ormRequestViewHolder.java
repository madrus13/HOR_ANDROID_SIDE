package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.korotaev.r.ms.hor.AppHelpers.NetworkImageViewAdapter;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;

import java.text.SimpleDateFormat;

public class ormRequestViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView messageBody;
    public TextView message_time;
    public NetworkImageView image;
    private NetworkImageViewAdapter networkImageViewAdapter;

    public ormRequestViewHolder(View itemView, NetworkImageViewAdapter networkImageViewAdapter) {
        super(itemView);
        this.networkImageViewAdapter = networkImageViewAdapter;
    }

    public void bind(Request item, int viewType) {

        if (item == null ) {

        } else {

            messageBody = itemView.findViewById(R.id.message_body);
            message_time = itemView.findViewById(R.id.message_time);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.avatar);

            switch (viewType)
            {
                case ViewHelper.COMMON_ACTIVE_REQUEST:

                    if (messageBody!=null)  {
                        Requesttype type = item.getRequesttypeByType();
                        messageBody.setText(item.getDescription() + " ");
                    }
                    if (message_time!=null && item.getCreationDate()!=null) {
                        message_time.setText((new SimpleDateFormat("hh:mm")).format(item.getCreationDate()));
                    }
                    break;
                default:
                    break;
            }


        }
    }
}

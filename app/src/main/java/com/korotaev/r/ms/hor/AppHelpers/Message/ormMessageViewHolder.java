package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

    public class ormMessageViewHolder extends ViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
    public TextView message_time;

    public ormMessageViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Message item, int viewType) {

        if (item == null ) {

        } else {
            messageBody = itemView.findViewById(R.id.message_body);
            message_time = itemView.findViewById(R.id.message_time);

            if (messageBody!=null) messageBody.setText(item.getText());
            if (message_time!=null && item.getCreationDate()!=null) message_time.setText(item.getCreationDate().toString());
            switch (viewType)
            {
                case ViewHelper.INPUT_MESSAGE:
                    name = itemView.findViewById(R.id.name);
                    if (name!=null) name.setText(item.getCreateUser().toString());
                    break;
                case ViewHelper.OUTPUT_MESSAGE:
                    break;
                case ViewHelper.SYSTEM_MESSAGE:
                    break;
                default:
                    break;
            }


        }
    }
}

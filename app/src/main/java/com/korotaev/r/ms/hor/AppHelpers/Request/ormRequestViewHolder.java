package com.korotaev.r.ms.hor.AppHelpers.Request;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Message;

import java.text.SimpleDateFormat;

import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.FILE_SERVER_IP;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_COMMON;
import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.F_WEB_FILES_MESSAGE_PHOTO;

public class ormRequestViewHolder extends ViewHolder {

    public View avatar;
    public TextView name;
    public TextView messageBody;
    public TextView message_time;
    public NetworkImageView image;
    private ImageLoader mImageLoader;

    public ormRequestViewHolder(View itemView, ImageLoader  mImageLoader) {
        super(itemView);
        this.mImageLoader = mImageLoader;
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

                    String realPhotPath =  item.getMessagePhotoPath();
                    if (!realPhotPath.isEmpty() && realPhotPath.contains(F_WEB_FILES_MESSAGE_PHOTO)) {
                        realPhotPath =  FILE_SERVER_IP + realPhotPath.replace(F_WEB_FILES_COMMON,"");
                        image.setImageUrl(realPhotPath,mImageLoader);
                    }

                    break;
                case ViewHelper.SYSTEM_MESSAGE:
                    break;
                default:
                    break;
            }


        }
    }
}

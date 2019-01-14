package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.korotaev.r.ms.testormlite.data.Entity.Message;

public class ormMessageViewHolder extends ViewHolder {
    public View mainItem;
    public View avatar;
    public TextView name;
    public TextView messageBody;

    public ormMessageViewHolder(View itemView) {
        super(itemView);
        mainItem = itemView;
    }

    public void bind(Message item) {
        if (item == null) {
             name.setText("empty");
             messageBody.setText("EMPTY");

        } else {
           // name.setText("empty");
            //messageBody.setText(item.getText());
            messageBody.setText(item.getText());
        }
    }
}

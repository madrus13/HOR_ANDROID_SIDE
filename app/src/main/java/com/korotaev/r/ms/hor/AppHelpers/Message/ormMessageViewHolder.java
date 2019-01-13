package com.korotaev.r.ms.hor.AppHelpers.Message;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.korotaev.r.ms.testormlite.data.Entity.Message;

public class ormMessageViewHolder extends RecyclerView.ViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;

    public ormMessageViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(Message item) {
        if (item == null) {
             name.setText("empty");
             messageBody.setText("EMPTY");

        } else {
            name.setText("empty");
            messageBody.setText(item.getText());
        }
    }
}

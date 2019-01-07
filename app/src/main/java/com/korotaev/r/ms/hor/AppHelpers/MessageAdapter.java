package com.korotaev.r.ms.hor.AppHelpers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.korotaev.r.ms.hor.R;

import java.util.ArrayList;
import java.util.List;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;

public class MessageAdapter extends BaseAdapter {

    List<Message> messages = new ArrayList<Message>();
    Context context;
    MyDBHelper myDBHelper;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        myDBHelper =  new MyDBHelper(this.context);
        try {

            myDBHelper.getHelper().addLog(CODE_INFO, "MsgAdapt -> getView" );
            MessageViewHolder holder = new MessageViewHolder();
            LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Message message = messages.get(i);

            if (message.isBelongsToCurrentUser()) { // this message was sent by us so let's create a basic chat bubble on the right
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

                holder.name.setText(message.getData().getName());
                holder.messageBody.setText(message.getText());
                GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
                drawable.setColor(Color.parseColor(message.getData().getColor()));
            }

            return convertView;
        }
        catch (Exception ex)
        {
            myDBHelper.getHelper().addLog(CODE_INFO, "MsgAdapt -> getView Except " );
        }
        return  null;
    }

}

class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}
package com.korotaev.r.ms.hor.fragment.ui.chat;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.korotaev.r.ms.hor.AppHelpers.MemberData;
import com.korotaev.r.ms.hor.AppHelpers.Message;
import com.korotaev.r.ms.hor.AppHelpers.MessageAdapter;

import com.korotaev.r.ms.hor.R;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    MessageAdapter messageAdapter = new MessageAdapter(getContext());

    final MemberData data = new MemberData();


    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    private Button sendMsgButton;
    private EditText messageToSend;

    public  void initViews(View v)
    {
        sendMsgButton = v.findViewById(R.id.send_chat_message);
        messageToSend = v.findViewById(R.id.message_to_send);
    }
    public void oOnClickListenerInit()
    {
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Message message = new Message(messageToSend.getText().toString(), data, true);
                messageAdapter.add(message);
                //messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chat_activity, container, false);
        initViews(v);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}

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
import android.widget.ListView;

import com.korotaev.r.ms.hor.AppHelpers.MemberData;
import com.korotaev.r.ms.hor.AppHelpers.Message;
import com.korotaev.r.ms.hor.AppHelpers.MessageAdapter;

import com.korotaev.r.ms.hor.R;

import java.util.Random;

public class ChatFragment extends Fragment {

    private ChatViewModel mViewModel;
    MessageAdapter messageAdapter;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    private Button sendMsgButton;
    private EditText messageToSend;
    private ListView messagesView;

    public  void initViews(View v)
    {
        sendMsgButton = v.findViewById(R.id.send_chat_message);
        messageToSend = v.findViewById(R.id.message_to_send);
        messageAdapter = new MessageAdapter(getContext());
        messagesView = (ListView) v.findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
    }
    public void oOnClickListenerInit()
    {
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = "";

                if (messageToSend!=null && !messageToSend.getText().toString().isEmpty()) {
                    messageText = messageToSend.getText().toString();
                    try {
                        MemberData data = new MemberData(getRandomName(), getRandomColor());
                        Message message = new Message(messageText, data, true);
                        messageAdapter.add(message);
                        messagesView.setSelection(messagesView.getCount() - 1);
                    }
                    catch (Exception ex)
                    {

                    }
                }


                //messagesView.setSelection(messagesView.getCount() - 1);
            }
        });
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
                adjs[(int) Math.floor(Math.random() * adjs.length)] +
                        "_" +
                        nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chat_fragment, container, false);
        initViews(v);

        oOnClickListenerInit();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}


package com.korotaev.r.ms.hor.fragment.ui.chat;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.korotaev.r.ms.hor.AppHelpers.MemberData;
import com.korotaev.r.ms.hor.AppHelpers.Message;
import com.korotaev.r.ms.hor.AppHelpers.MessageAdapter;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;

import java.util.Random;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;

public class ChatFragment extends Fragment implements ServiceActivity {

    static boolean  stateFromMe = true;
    Messenger mService = null;
    static Messenger mMessenger = new Messenger(new ChatFragment.IncomingHandler());


    private ChatViewModel mViewModel;
    MessageAdapter messageAdapter;
    private MyDBHelper myDBHelper = new MyDBHelper(getContext());
    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    private ImageButton sendMsgButton;
    private EditText messageToSend;
    private ListView messagesView;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        if (service!=null && mService == null) {

            mService = new Messenger(service);

            ViewHelper.sendComandToIntentService(
                    ChatFragment.this.getContext(),
                    mMessenger,
                    mService,
                    null,
                    null,
                    SrvCmd.CMD_RegisterIntentServiceClientReq);


        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * Handler of incoming messages from service.
     */
    protected static class IncomingHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            msg.getData();
            if (msg.replyTo == mMessenger) {
                switch (msg.what) {
                    case SrvCmd.CMD_RegisterIntentServiceClientResp:

                        break;
                    case SrvCmd.CMD_EntitySyncResp:
                        break;

                    case SrvCmd.CMD_EntitySetUserInfoResp:

                        break;

                    default:
                        super.handleMessage(msg);
                }
            }

        }
    }


    public  void initViews(View v)
    {
        myDBHelper.getHelper().addLog(CODE_INFO, "CHF -> initViews" );
        sendMsgButton = v.findViewById(R.id.send_chat_message_button);
        messageToSend = v.findViewById(R.id.message_to_send);
        messagesView = v.findViewById(R.id.messages_view);

        messageAdapter = new MessageAdapter(getContext());
        messagesView.setAdapter(messageAdapter);
    }

    public void OnClickListenerInit()
    {
        myDBHelper.getHelper().addLog(CODE_INFO, "CHF -> OnClickListenerInit" );
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = "";

                if (messageToSend!=null && !messageToSend.getText().toString().isEmpty()) {
                    messageText = messageToSend.getText().toString();
                    try {
                        MemberData data = new MemberData(getRandomName(), getRandomColor());
                        Message message = new Message(messageText, data, stateFromMe);
                        stateFromMe = !stateFromMe;
                        messageAdapter.add(message);
                        messagesView.setSelection(messagesView.getCount() - 1);
                        messageToSend.setText("");
                    }
                    catch (Exception ex)
                    {
                        myDBHelper.getHelper().addLog(CODE_INFO, "CHF -> OnClickListenerInit" + ex.toString() );
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
        OnClickListenerInit();

        Intent i = new Intent(getContext(), CmdService.class);
        getActivity().bindService(i,  ChatFragment.this, Context.BIND_AUTO_CREATE);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}


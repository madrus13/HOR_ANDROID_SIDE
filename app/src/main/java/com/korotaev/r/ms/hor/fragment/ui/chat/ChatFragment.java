package com.korotaev.r.ms.hor.fragment.ui.chat;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
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
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.korotaev.r.ms.hor.AppHelpers.Message.MainThreadExec;
import com.korotaev.r.ms.hor.AppHelpers.Message.MessageStorage;
import com.korotaev.r.ms.hor.AppHelpers.Message.MyPositionalDataSource;
import com.korotaev.r.ms.hor.AppHelpers.Message.ormMessageAdapter;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.Random;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;

public class ChatFragment extends Fragment implements ServiceActivity {

    static boolean  stateFromMe = true;
    Messenger mService = null;
    Messenger mMessenger = new Messenger(new ChatFragment.IncomingHandler());


    private ChatViewModel mViewModel;
    ormMessageAdapter messageAdapter;
    private MyDBHelper myDBHelper = new MyDBHelper(getContext());
    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    private ImageButton sendMsgButton;
    private EditText messageToSend;
    private RecyclerView messagesView;

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
                    SrvCmd.CMD_RegisterIntentServiceClientReq, null);


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
    protected  class IncomingHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            msg.getData();
            if (msg.replyTo == mMessenger) {
                switch (msg.what) {
                    case SrvCmd.CMD_RegisterIntentServiceClientResp:
                        ViewHelper.sendComandToIntentService(
                                getContext(),
                                mMessenger,
                                mService,
                                null,
                                null,
                                SrvCmd.CMD_GetMessageByUserRegionReq, null);

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

        /*
        messageAdapter = new ormMessageAdapter(getContext());
        messagesView.setAdapter(messageAdapter);
        */
        // DataSource



        MyPositionalDataSource dataSource = new MyPositionalDataSource(
                new MessageStorage(getContext()));


// PagedList
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();

        PagedList<Message> pagedList = new PagedList.Builder<>(dataSource, config)
                .setFetchExecutor(new MainThreadExec())
                .setNotifyExecutor(new MainThreadExec())
                .build();


            // Adapter

        messageAdapter = new ormMessageAdapter(new DiffUtil.ItemCallback<Message>() {
            @Override
            public boolean areItemsTheSame(Message oldItem, Message newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(Message oldItem, Message newItem) {
                return false;
            }
        });
        messageAdapter.submitList(pagedList);
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
                        User user = Preferences.loadCurrentUserInfo(getContext());

                        Message message = new Message();
                        message.setText(messageText);
                        message.setCreateUser(user.getId());
                        message.setRegion(user.getRegion());
                        message.setType(3L);

                        stateFromMe = !stateFromMe;

                        Bundle b = new Bundle();
                        b.putString("text",messageText);
                        b.putLong("requestId", 0);
                        if (user!=null && user.getRegion() > 0) {
                            b.putLong("regionId", user.getRegion());
                        }
                        b.putLong("userRx", 0);
                        b.putLong("typeId", 3); //region message
                        b.putString("fileName",null);
                        b.putByteArray("fileImage",null);


                        ViewHelper.sendComandToIntentService(
                                ChatFragment.this.getContext(),
                                mMessenger,
                                mService,
                                null,
                                null,
                                SrvCmd.CMD_InsertMessageReq, b);

                        //messageAdapter.ad(message);
                        //messagesView.setsele(messagesView.getCount() - 1);
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
        FragmentActivity activity;
        if ((activity = getActivity())!=null) {
                getActivity().bindService(i,  ChatFragment.this, Context.BIND_AUTO_CREATE);
        }


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        // TODO: Use the ViewModel
    }

}


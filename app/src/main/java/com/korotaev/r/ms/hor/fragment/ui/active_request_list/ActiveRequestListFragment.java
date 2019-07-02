package com.korotaev.r.ms.hor.fragment.ui.active_request_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.LivePagedListBuilder;
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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.korotaev.r.ms.hor.AppHelpers.Common.CustomSourceFactory;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.NetworkImageViewAdapter;
import com.korotaev.r.ms.hor.AppHelpers.ParserHelper;
import com.korotaev.r.ms.hor.AppHelpers.Common.CustomStorage;
import com.korotaev.r.ms.hor.AppHelpers.Request.ormRequestAdapter;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.testormlite.data.Entity.EntityConstVariables;
import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.concurrent.Executors;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;
import static com.korotaev.r.ms.hor.fragment.ui.chat.ChatViewModel.VIEW_DATA_PAGE_SIZE;

public class ActiveRequestListFragment extends Fragment implements ServiceActivity {


    static boolean  stateFromMe = true;

    static boolean  registeredToServiceIntent;
    Messenger mService = null;
    Messenger mMessenger = new Messenger(new IncomingHandler());

    public static boolean isLoadNewMessagePortion = false;
    private ActiveRequestListViewModel mViewModel;
    ormRequestAdapter requestAdapter;
    private MyDBHelper myDBHelper = new MyDBHelper(getContext());
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Long fileIdToUpload = 0L;

    public ActiveRequestListFragment() {
        registeredToServiceIntent = false;
    }

    public static ActiveRequestListFragment newInstance() {
        return new ActiveRequestListFragment();
    }
    User user;

    private ImageButton sendMsgButton;
    private EditText messageToSend;
    private RecyclerView requestView;

    NetworkImageViewAdapter networkImageViewAdapter;


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        if (service!=null && mService == null) {
            mService = new Messenger(service);

            ViewHelper.sendComandToIntentService(
                    this.getContext(),
                    mMessenger,
                    mService,
                    null,
                    null,
                    SrvCmd.CMD_RegisterIntentServiceClientReq, null);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        registeredToServiceIntent = false;
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
            if (true) {
                //msg.replyTo == mMessenger
                switch (msg.what) {
                    case SrvCmd.CMD_RegisterIntentServiceClientResp:
                        ViewHelper.sendComandToIntentService(
                                getContext(),
                                mMessenger,
                                mService,
                                null,
                                null,
                                SrvCmd.CMD_GetActiveRequestByUserRegionReq, null);
                        registeredToServiceIntent = true;
                        break;

                    case SrvCmd.CMD_GetActiveRequestByUserRegionResp:
                        initAdapter();
                        mSwipeRefreshLayout.setRefreshing(false);
                        isLoadNewMessagePortion = false;
                        break;

                    case SrvCmd.CMD_InsertActiveRequestResp:
                        initAdapter();
                        break;

                    default:
                        super.handleMessage(msg);
                }
            }
        }
    }



    public void initAdapter()
    {
        if (networkImageViewAdapter == null) {
            networkImageViewAdapter = new NetworkImageViewAdapter(this.getContext());
        }

        CustomSourceFactory sourceFactory = new CustomSourceFactory<Request>(new CustomStorage<>(getContext(), Request.class),user);

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(VIEW_DATA_PAGE_SIZE)
                .setEnablePlaceholders(true)
                .build();

        requestAdapter = new ormRequestAdapter(new DiffUtil.ItemCallback<Request>() {
            @Override
            public boolean areItemsTheSame(Request oldItem, Request newItem) {
                return
                        oldItem!=null && newItem!=null &&
                                oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(Request oldItem, Request newItem) {

                return  oldItem!=null && newItem!=null &&
                        oldItem.getId().equals(newItem.getId());
            }
        },getContext(), user, networkImageViewAdapter);



        LiveData<PagedList<Request>> pagedListLiveData = new LivePagedListBuilder<>(sourceFactory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .setInitialLoadKey(0)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Request>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Request itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);

                    }

                    @Override
                    public void onItemAtEndLoaded(@NonNull Request itemAtEnd) {
                        super.onItemAtEndLoaded(itemAtEnd);
                    }
                })
                .build();

        pagedListLiveData.observe(this, requests -> {
            Log.d(CODE_INFO, "submit PagedList");
            if (requestAdapter !=null) {
                requestAdapter.submitList(requests);
                int pos = requestView.getAdapter().getItemCount();
                requestView.scrollToPosition(pos > 0 ? pos : 0);
                requestAdapter.notifyDataSetChanged();
            }

        });
        final  LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        requestView.setLayoutManager(layoutManager);

        requestView.setAdapter(requestAdapter);
        int pos = requestView.getAdapter().getItemCount();
        requestView.scrollToPosition(pos > 0 ? pos : 0);



    }

    public void getNextPage()
    {

        String val = Preferences.loadObjInPrefs(getContext(), Preferences.SAVED_LAST_REQ_ROW_IN_REGION);
        Long offset =  ParserHelper.TryParse(val);
        offset++;
        Preferences.saveObjInPrefs(getContext(),
                Preferences.SAVED_LAST_REQ_ROW_IN_REGION,String.valueOf(offset));


        ViewHelper.sendComandToIntentService(
                getContext(),
                mMessenger,
                mService,
                null,
                null,
                SrvCmd.CMD_GetActiveRequestByUserRegionReq, null);

    }
    public  void initViews(View v)
    {
        myDBHelper.getHelper().addLog(CODE_INFO, "ARF -> initViews" );
        sendMsgButton = v.findViewById(R.id.send_chat_message_button);
        messageToSend = v.findViewById(R.id.message_to_send);
        requestView = v.findViewById(R.id.messages_view);
        mSwipeRefreshLayout = v.findViewById(R.id.swipe_view);
    }

    public void OnClickListenerInit()
    {
        myDBHelper.getHelper().addLog(CODE_INFO, "ARF -> OnClickListenerInit" );
        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText = "";

                if (messageToSend!=null && !messageToSend.getText().toString().isEmpty()) {
                    messageText = messageToSend.getText().toString();
                    try {
                        Request request = new Request();
                        // TODO ADD заполнение REQUEST

                        stateFromMe = !stateFromMe;

                        Bundle b = new Bundle();
                        b.putString("text",messageText);
                        b.putLong("requestId", 0);
                        if (user!=null && user.getRegion() > 0) {
                            b.putLong("regionId", user.getRegion());
                        }
                        b.putLong("userRx", 0);
                        b.putLong("typeId", Long.valueOf(EntityConstVariables.MESSAGE_TYPE_REGION));
                        b.putLong("fileId", fileIdToUpload);
                        b.putString("fileName",null);
                        b.putByteArray("fileImage",null);


                        ViewHelper.sendComandToIntentService(
                                getContext(),
                                mMessenger,
                                mService,
                                null,
                                null,
                                SrvCmd.CMD_InsertMessageReq, b);

                        //requestAdapter.ad(message);
                        //requestView.setsele(requestView.getCount() - 1);
                        messageToSend.setText("");
                    }
                    catch (Exception ex)
                    {
                        myDBHelper.getHelper().addLog(CODE_INFO, "ARF -> OnClickListenerInit" + ex.toString() );
                    }
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNextPage();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        user = Preferences.loadCurrentUserInfo(getContext());
        View v = inflater.inflate(R.layout.chat_fragment, container, false);
        initViews(v);
        OnClickListenerInit();




        Intent i = new Intent(getContext(), CmdService.class);
        FragmentActivity activity;
        if ((activity = getActivity())!=null && registeredToServiceIntent == false) {
            activity.bindService(i,  this, Context.BIND_AUTO_CREATE);
        }


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ActiveRequestListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onPause() {
        super.onPause();
        messageToSend.clearFocus();
    }

}


package com.korotaev.r.ms.hor.fragment.ui.active_request_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import com.korotaev.r.ms.hor.fragment.ui.fragment.ui.new_request.NewRequestFragment;
import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.concurrent.Executors;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;
import static com.korotaev.r.ms.hor.fragment.ui.chat.ChatViewModel.VIEW_DATA_PAGE_SIZE;

public class ActiveRequestListFragment extends Fragment implements ServiceActivity {


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
    private FloatingActionButton addNewRequestFloatButton;

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
    public void onBindingDied(ComponentName name) {

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
        },getContext(), user, networkImageViewAdapter, ViewHelper.COMMON_ACTIVE_REQUEST);



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
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(false);
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
        addNewRequestFloatButton = v.findViewById(R.id.addNewRequestFloatButton);
    }

    public void OnClickListenerInit()
    {
        myDBHelper.getHelper().addLog(CODE_INFO, "ARF -> OnClickListenerInit" );


        mSwipeRefreshLayout.setOnRefreshListener(() -> getNextPage());

        addNewRequestFloatButton.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new NewRequestFragment()).commit();
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        user = Preferences.loadCurrentUserInfo(getContext());
        View v = inflater.inflate(R.layout.active_request_list_fragment, container, false);
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
        if (messageToSend!=null) {
            messageToSend.clearFocus();
        }
    }

}


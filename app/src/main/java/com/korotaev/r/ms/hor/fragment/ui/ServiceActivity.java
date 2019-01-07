package com.korotaev.r.ms.hor.fragment.ui;

import android.os.Handler;
import android.os.Messenger;
import android.view.View;

import com.korotaev.r.ms.hor.fragment.ui.chat.ChatFragment;

public interface ServiceActivity {
    void initViews(View v);
    void OnClickListenerInit();
    class IncomingHandler extends Handler{};
}

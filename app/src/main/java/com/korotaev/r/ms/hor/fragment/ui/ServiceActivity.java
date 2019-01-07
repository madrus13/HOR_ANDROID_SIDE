package com.korotaev.r.ms.hor.fragment.ui;

import android.content.ServiceConnection;
import android.os.Handler;
import android.view.View;

public interface ServiceActivity extends ServiceConnection {
    void initViews(View v);
    void OnClickListenerInit();
    class IncomingHandler extends Handler{};
}

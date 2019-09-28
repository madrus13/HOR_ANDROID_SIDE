package com.korotaev.r.ms.hor.fragment.ui;

import android.app.LoaderManager;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Handler;
import android.view.View;

public interface ServiceActivity extends ServiceConnection,LoaderManager.LoaderCallbacks<Cursor> {
    void initViews(View v);
    void OnClickListenerInit();
    class IncomingHandler extends Handler{}
}

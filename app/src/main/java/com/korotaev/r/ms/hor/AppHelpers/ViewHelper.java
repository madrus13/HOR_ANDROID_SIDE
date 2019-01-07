package com.korotaev.r.ms.hor.AppHelpers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.korotaev.r.ms.hor.R;

public class ViewHelper {
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(Context context, View viewForm, View progressBar, final boolean show) {

        if (context == null || viewForm == null || progressBar == null) {return;}

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

            viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
            viewForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            viewForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public static void sendComandToIntentService(Context context,
                                          Messenger mMessengerFrom,
                                          Messenger mService,
                                          View viewForm,
                                          View progressBar,
                                          int command)
    {
        // We want to monitor the service for as long as we are
        // connected to it.
        try {
            android.os.Message msg = Message.obtain(null, command);
            msg.replyTo = mMessengerFrom;
            mService.send(msg);
        } catch (RemoteException e) {
            Toast.makeText(context, R.string.remote_service_crashed,
                    Toast.LENGTH_SHORT).show();
            ViewHelper.showProgress(context, viewForm, progressBar,false );
        }
    }
}

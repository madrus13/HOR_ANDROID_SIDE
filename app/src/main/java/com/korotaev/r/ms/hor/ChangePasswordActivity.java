package com.korotaev.r.ms.hor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangePasswordActivity extends Activity {
    private Button mSaveButton;
    private Button mCancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mSaveButton = findViewById(R.id.save_button);
        mCancelButton = findViewById(R.id.cancel_button);

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();

            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.putExtra(ActivityActions.EXTRA_SELECTED_ID, selectedPos);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}

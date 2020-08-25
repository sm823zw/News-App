package com.example.footballdaily;

import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
    static final String COUNTRY_QUERY = "COUNTRY_QUERY";
    static final String NEWS_TRANSFER = "NEWS_TRANSFER";
    private static final String TAG = "MainBaseActivity";

    /* Access modifiers changed, original: 0000 */
    public void activateToolbar(boolean enableHome) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
                toolbar.setNavigationOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        BaseActivity.this.finish();
                    }
                });
            }
        }
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
    }
}

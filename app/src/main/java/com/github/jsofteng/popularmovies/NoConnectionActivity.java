package com.github.jsofteng.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoConnectionActivity extends AppCompatActivity {

    TextView mNoConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        mNoConnection = (TextView) findViewById(R.id.tv_no_internet);
    }
}

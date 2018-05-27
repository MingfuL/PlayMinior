package com.example.mingfliu.waveviewplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mingfliu.waveviewplay.mini.MinionView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        LinearLayout mainView = findViewById(R.id.main);
//        mainView.addView(new MinionView(this));
    }
}

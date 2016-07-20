package com.asynctasktest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textView);
        final String address = "http://www.baidu.com";
        Button send = (Button) findViewById(R.id.send);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyTask(MainActivity.this, new MyCallBack() {
                    @Override
                    public void onFinish(String response) {
                        textView.setText(response);
                        Log.d("TAG",response);
                    }

                    @Override
                    public void onCancle() {

                    }
                },progressBar).execute(address);
            }
        });
    }
}

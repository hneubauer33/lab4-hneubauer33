package com.example.lab4_hneubauer33;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;

    TextView downloadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
    }

    public void mockFileDownloader() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });


        for (int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress + 10) {

            downloadText = (TextView) findViewById(R.id.downloadText);

            if (stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downloadText.setText("");
                        startButton.setText("Start");

                    }
                });
                return;
            }
            if(!stopThread){
                int finalDownloadProgress = downloadProgress;
                runOnUiThread(new Runnable(){
                   @Override
                   public void run(){
                       downloadText = (TextView) findViewById(R.id.downloadText);
                       downloadText.setText("Download Progress: " + finalDownloadProgress + "%");
                   }
                });
            }


            Log.d(TAG, "Download Progress: " + downloadProgress + "%");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startButton.setText("Start");

                }
            });
    }

    public void startDownload(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void stopDownload(View view){
        stopThread = true;
    }

    public class ExampleRunnable implements Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }


    }
}


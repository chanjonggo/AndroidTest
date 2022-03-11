package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mediaPlayer = MediaPlayer.create(this, R.raw.testmp3);

        /*if (null == this.mediaPlayer){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("MediaPlayer Create Error");
            return;
        }

        this.mediaPlayer.start();

        //실제론 테스트 용도이며 더 좋은 방식의 읽기가 존재한다고 함
        this.mediaPlayer.setLooping(false);


        this.textView = (TextView) this.findViewById(R.id.helloButton);
        this.textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    textView.setText("Stop");
                } else {
                    mediaPlayer.pause();
                    textView.setText("Start");
                }
            }
        });*/
    }
}
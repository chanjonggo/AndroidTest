package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

        this.mediaPlayer = MediaPlayer.create(this, R.raw.shortmp3);

        if (null == this.mediaPlayer)
        {
            return;
        }
        this.mediaPlayer.start();

        //실제론 테스트 용도이며 더 좋은 방식의 읽기가 존재한다고 함
        this.mediaPlayer.setLooping(false);

        //동일한 mp3확장자여도 읽지 못하는 경우가 존재함
        //왜 읽지 못하는 건지는 모르겠음
        //일단 1초정도 걸리는 효과음들은 제대로 create하지 못함
        //좀 긴 음악들은 제대로 출력해주는듯?
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
        });
    }
}
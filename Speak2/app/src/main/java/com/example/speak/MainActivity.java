package com.example.speak;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            int CallType = intent.getIntExtra("CallType", 0);
                            switch (CallType) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    ArrayList<String> results = intent
                                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                                    String str = results.get(0);
                                    Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

                                    TextView tv = findViewById(R.id.txVoice);
                                    tv.setText(str);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            //VoiceTask
            VoiceTask voiceTask = new VoiceTask();
            voiceTask.execute();
        });
    }

    public class VoiceTask extends AsyncTask<String, Integer, String> {
        private String str = null;


        @Override
        protected String doInBackground(String... strings) {
            try {
                getVoice();
            } catch (Exception e) {

            }
            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {
                Log.d("onActivityResult", "getImageURL exception");
            }
        }
    }

    private void getVoice() {
        Intent intent = new Intent();
        intent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        String language = "ko-KR";

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language);
        intent.putExtra("CallType", 2);
        resultLauncher.launch(intent);
    }

}
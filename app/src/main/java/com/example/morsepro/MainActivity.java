package com.example.morsepro;

import android.speech.tts.TextToSpeech;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private TextView result;
    Button toMorseBtn;
    Button toAlphaBtn;
    Button speakBtn;
    TextToSpeech textToSpeech;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.txt);
        result = (TextView) findViewById(R.id.result);
        toMorseBtn = (Button) findViewById(R.id.toMorseBtn);
        toAlphaBtn = (Button) findViewById(R.id.toAlphaBtn);
        speakBtn = findViewById(R.id.speakbtn);

        // text to speech
        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            public boolean textToSpeechIsInitialized = false;
            @Override
            public void onInit(int status) {
                // if there is no error
                if(status == TextToSpeech.SUCCESS){
                    textToSpeechIsInitialized = true;
                   int result = textToSpeech.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                }

                else{
                    Log.e("TTS", "Initilization Failed");
                }
            }
        });

        toMorseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtToConvert = txt.getText().toString();
                String convertedTxt = morsecode.alphaToMorse(txtToConvert);
                result.setText(convertedTxt);
            }
        });

        toAlphaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtToConvert = txt.getText().toString();
                String convertedTxt = morsecode.morseToAlpha(txtToConvert);
                result.setText(convertedTxt);
            }
        });

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // using the text in result
                String text = result.toString();
                // converts text to speech
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }


    @Override
    protected void onPause() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
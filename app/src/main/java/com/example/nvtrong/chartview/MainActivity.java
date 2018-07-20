package com.example.nvtrong.chartview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nvtrong.chartview.face.EmotionalFaceView;

public class MainActivity extends AppCompatActivity {

    EmotionalFaceView btn_left, btn_right;
    EmotionalFaceView face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        face = findViewById(R.id.face);
        btn_left = findViewById(R.id.happyButton);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                face.setHappinessState(EmotionalFaceView.HAPPY);
            }
        });
        btn_right = findViewById(R.id.sadButton);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                face.setHappinessState(EmotionalFaceView.SAD);
            }
        });
    }
}

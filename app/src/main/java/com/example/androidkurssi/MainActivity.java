package com.example.androidkurssi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity{

    private Button sButton;
    private Button gameButton;
    public static final String TAG = "MainActivity";
    private View contentView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        contentView = findViewById(R.id.textView);

        sButton = (Button) findViewById(R.id.startButton);
        sButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        gameButton = (Button) findViewById(R.id.gameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

    }

    public void handleOnClickEvents(View v){

        switch(v.getId()){
            case R.id.startButton:
                Log.d(TAG, "User tapped the startButton");

                if(contentView.getVisibility() == View.INVISIBLE){
                    contentView.setVisibility(View.VISIBLE);
                }else{
                    contentView.setVisibility(View.INVISIBLE);
                }

                break;
            case R.id.gameButton:
                Log.d(TAG, "User tapped the gameButton");
                break;


        }


    }
}
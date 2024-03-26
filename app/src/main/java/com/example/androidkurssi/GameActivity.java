package com.example.androidkurssi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

//import io.reactivex.rxjava3.core.Single;
import io.reactivex.Single;


public class GameActivity extends AppCompatActivity {

    public static final String TAG = "GameActivity";
    Random rand = new Random();
    private ImageButton velhoButton1;
    private ImageButton velhoButton2;
    private ImageButton velhoButton3;
    private ImageButton velhoButton4;
    private FloatingActionButton restartButton;
    private TextView scoreText;

    private View contentView;
    int rand_int1;
    int score = 0;
    String scoreTeksti = "";
    private static final String KEY_HS = "HighestScore";
    RxDataStore<Preferences> dataStoreRX;
    DataStoreHelper dataStoreHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rand_int1 = rand.nextInt(4);


        //dataStore = new RxPreferenceDataStoreBuilder(this, "gameScoreStore").build();

        DataStore dataStore = DataStore.getInstance();
        if (dataStore.getDataStore() == null) {
            dataStoreRX = new RxPreferenceDataStoreBuilder(this, TAG).build();
        } else {
            dataStoreRX = dataStore.getDataStore();
        }
        dataStore.setDataStore(dataStoreRX);
        dataStoreHelper = new DataStoreHelper();

        scoreTeksti = (getResources().getString(R.string.gameScore));
        // PELIN PISTEET-----------------------------------------------
        if (getStringValue("gamescore") != null) {
            Log.d(TAG,"getstringvaluen arvo on: "+ getStringValue("gamescore"));
            score = Integer.valueOf(getStringValue("gamescore"));
        }
        scoreText = (TextView) findViewById(R.id.textView3);
        scoreText.setText(scoreTeksti + score);
        // PELIN PISTEET -----------------------------------------------

        //BACK NAVIGOINTI -----------------------------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.gameToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //BACK NAVIGOINTI -----------------------------------------------

        //PELIN NAPIT-----------------------------------------------
        velhoButton1 = (ImageButton) findViewById(R.id.velho1);
        velhoButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        velhoButton2 = (ImageButton) findViewById(R.id.velho2);
        velhoButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        velhoButton3 = (ImageButton) findViewById(R.id.velho3);
        velhoButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });
        velhoButton4 = (ImageButton) findViewById(R.id.velho4);
        velhoButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        restartButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });
        //PELIN NAPIT-----------------------------------------------
    }

    public void handleOnClickEvents(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation1);
        switch (v.getId()) {
            case R.id.velho1:
                Log.d(TAG, "User tapped the velhoButton1" + " randnro=" +  rand_int1);
                if (rand_int1 == 0 && velhoButton1.isClickable()){
                    Log.d(TAG, "Aivan oikein Velho1 oli oikein");
                    velhoButton1.startAnimation(animation);
                    velhoButton1.setImageResource(R.drawable.ic_launcher_foreground);
                    velhoButton1.setClickable(false);
                    updateScore();

                }


                break;
            case R.id.velho2:
                Log.d(TAG, "User tapped the velhoButton2" + " randnro=" +  rand_int1);
                if (rand_int1 == 1 && velhoButton2.isClickable()) {
                    velhoButton2.startAnimation(animation);
                    velhoButton2.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Aivan oikein Velho2 oli oikein");
                    velhoButton2.setClickable(false);
                    updateScore();

                }
                break;
            case R.id.velho3:
                Log.d(TAG, "User tapped the velhoButton3" + " randnro=" +  rand_int1);
                if (rand_int1 == 2 && velhoButton3.isClickable()) {
                    velhoButton3.startAnimation(animation);
                    velhoButton3.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Aivan oikein Velho3 oli oikein");
                    velhoButton3.setClickable(false);
                    updateScore();

                }

                break;
            case R.id.velho4:
                Log.d(TAG, "User tapped the velhoButton4" + " randnro=" +  rand_int1);
                if (rand_int1 == 3 && velhoButton4.isClickable()) {
                    velhoButton4.startAnimation(animation);
                    velhoButton4.setImageResource(R.drawable.ic_launcher_foreground);
                    Log.d(TAG, "Aivan oikein Velho4 oli oikein");
                    velhoButton4.setClickable(false);
                    updateScore();

                }
                break;

            case R.id.floatingActionButton:
                Log.d(TAG, "User tapped floatingActionButton / restart button");
                finish();
                startActivity(getIntent());
                break;

        }



    }

    private void updateScore() {
        score++;
        TextView scoreTextView = findViewById(R.id.textView3);
        scoreTextView.setText(scoreTeksti + score);
        putStringValue("gamescore",Integer.toString(score));
    }



    public void putStringValue(String Key, String value){
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<Preferences> updateResult =  dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        });
    }

    String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("0");
        return value.blockingGet();
    }
}
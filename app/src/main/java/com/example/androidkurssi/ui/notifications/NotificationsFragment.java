package com.example.androidkurssi.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidkurssi.R;
import com.example.androidkurssi.databinding.FragmentNotificationsBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private NumberPicker picker;
    private String[] displayedValues;
    private MaterialButtonToggleGroup buttonToggleGroup;
    private TextView countTime;
    private CountDownTimer cTimer;
    int time;
    boolean paused = false;
    long timeLeft;
    Animation animation;
    Ringtone currentRingtone;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation2);
        Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getActivity()
                .getApplicationContext(), RingtoneManager.TYPE_ALARM);
        currentRingtone = RingtoneManager.getRingtone(getActivity(), currentRintoneUri);

        picker = root.findViewById(R.id.numberpicker1);
        picker.setMaxValue(60);
        picker.setMinValue(1);
        displayedValues = new String[61];
        for (int i = 0; i < 61; i++) {
            displayedValues[i] = String.valueOf(i+1);
        }
        picker.setDisplayedValues(displayedValues);

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker = picker.getValue();
                Log.d("picker value", displayedValues[valuePicker-1]);
            }
        });

        buttonToggleGroup = root.findViewById(R.id.toggleButton);
        countTime = root.findViewById(R.id.countTime);
        buttonToggleGroup.findViewById(R.id.button_pause).setEnabled(false);
        buttonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.button_start) {
                        // start timing here
                        startTiming();
                    } else if (checkedId == R.id.button_pause){
                        // pause timing here
                        pauseTiming();
                    } else if (checkedId == R.id.button_stop) {
                        //stop timing here
                        stopTiming();
                    }
                }
            }
        });






        return root;
    }

    public void startTiming(){

        buttonToggleGroup.findViewById(R.id.button_start).setEnabled(false);
        buttonToggleGroup.findViewById(R.id.button_pause).setEnabled(true);

        time = Integer.valueOf(picker.getValue()*1000+1000);
        cTimer = new CountDownTimer (time, 1000){

            public void onTick(long millisUntilFinished) {
                countTime.setText((millisUntilFinished / 1000) + " s" );
                timeLeft = millisUntilFinished;
                //pause = false;
            }

            public void onFinish() {
                countTime.setText("done!");
                countTime.startAnimation(animation);
                currentRingtone.play();
                //buttonToggleGroup.findViewById(R.id.button_start).setEnabled(true);

            }
        }.start();

    }

    public void pauseTiming(){

        paused = !paused;
        //paused = true;
        Log.d("pause",Boolean.toString(paused));
        if (paused) {
            Log.d("pauseTiming", "Paused is true");
            if (cTimer != null) {
                cTimer.cancel();
                Log.d("pauseTiming", "CountDownTimer is cancelled");
            } else {
                Log.d("pauseTiming", "CountDownTimer is null");
            }
        } else {
            Log.d("pauseTiming", "Paused is false");
            cTimer = new CountDownTimer (timeLeft, 1000){

                public void onTick(long millisUntilFinished) {
                    countTime.setText((millisUntilFinished / 1000) + " s" );
                    timeLeft = millisUntilFinished;
                    //pause = false;
                }

                public void onFinish() {
                    countTime.setText("done!");
                    countTime.startAnimation(animation);
                    currentRingtone.play();
                    //buttonToggleGroup.findViewById(R.id.button_start).setEnabled(true);
                }
            }.start();

        }


    }

    public void stopTiming(){
        cTimer.cancel();
        currentRingtone.stop();
        countTime.setText("-");
        countTime.clearAnimation();
        buttonToggleGroup.findViewById(R.id.button_start).setEnabled(true);
        buttonToggleGroup.findViewById(R.id.button_pause).setEnabled(false);

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
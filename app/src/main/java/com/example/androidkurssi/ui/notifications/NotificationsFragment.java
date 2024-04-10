package com.example.androidkurssi.ui.notifications;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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
        buttonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.button_start) {
                        // start timing here
                        int time = Integer.valueOf(picker.getValue()*1000);
                        new CountDownTimer(time, 1000){

                            public void onTick(long millisUntilFinished) {
                                countTime.setText("seconds remaining: " + millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                countTime.setText("done!");
                            }
                        }.start();
                    } else if (checkedId == R.id.button_pause){
                        // pause timing here
                    } else if (checkedId == R.id.button_stop) {
                        //stop timing here
                    }
                }
            }
        });






        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
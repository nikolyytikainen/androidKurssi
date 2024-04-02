package com.example.androidkurssi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidkurssi.GameActivity;
import com.example.androidkurssi.MainActivity;
import com.example.androidkurssi.R;
import com.example.androidkurssi.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button sButton;
    private Button gameButton;
    public static final String TAG = "HomeFragment";
    private View contentView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        contentView = root.findViewById(R.id.textView);

        sButton = (Button) root.findViewById(R.id.startButton);
        sButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        gameButton = (Button) root.findViewById(R.id.gameButton);
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                Intent i = new Intent(getActivity(), GameActivity.class);
                startActivity(i);
                break;


        }


    }
}
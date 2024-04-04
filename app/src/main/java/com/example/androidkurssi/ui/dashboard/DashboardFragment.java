package com.example.androidkurssi.ui.dashboard;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidkurssi.R;
import com.example.androidkurssi.databinding.FragmentDashboardBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements LocationListener {

    private FragmentDashboardBinding binding;
    public static final String TAG = "DashboardFragment";
    Button showMapButton;

    private TextInputEditText textLat;
    private TextInputEditText textLg;
    private TextInputEditText textLocation;
    Location lastLocation;
    Location location;
    Intent mapIntent;
    Uri gmmIntentUri;

    Double lat;
    Double lg;

    LocationManager locationManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        locationManager =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        showMapButton = (Button) root.findViewById(R.id.showOnMapButton);
        showMapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        textLat = (TextInputEditText) root.findViewById(R.id.textLatitude);
        textLg = (TextInputEditText) root.findViewById(R.id.textLongitude);
        textLocation = (TextInputEditText) root.findViewById(R.id.textAddress);



        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "Ei sijainti tietolupaa");
            return root;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0, this);
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation != null) {
            lat = lastLocation.getLatitude();
            lg = lastLocation.getLongitude();

            Log.e(TAG, "lat: " + String.valueOf(lat) + "long: " + String.valueOf(lg));


            textLat.setText(String.valueOf(lat));
            textLg.setText(String.valueOf(lg));
            getAddress(lastLocation);
            gmmIntentUri = Uri.parse("geo:"+lat+","+lg);
            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
        }



        //final TextView textView = binding.textDashboard;
        // dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    public void handleOnClickEvents(View v) {

        switch (v.getId()) {
            case R.id.showOnMapButton:
                Log.d(TAG, "testi");
                startActivity(mapIntent);
                break;

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStop() {
        //Tätä metodia kutsutaan, kun fragmentti ei enää näy käyttäjälle. ja lopettaa sijainninpäivityksen
        super.onStop();
        locationManager.removeUpdates(this);
    }

    public void getAddress(Location l){
        Geocoder geocoder;
        List<Address> addresses;
        Locale finnish = new Locale("fi", "FI");
        geocoder = new Geocoder(getContext(), finnish);
        try {
            addresses = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1); // Here 1 represent maxResults
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assert addresses != null;
        String address = addresses.get(0).getAddressLine(0); // getAddressLine returns a line of the address
        // numbered by the given index
        String city = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        textLocation.setText(address);


    }


    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                String.valueOf(location.getLatitude()) + ", " +
                String.valueOf(location.getLongitude());
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        lastLocation = location;

        if (lastLocation != null) {
            textLat.setText(String.valueOf(lastLocation.getLatitude()));
            textLg.setText(String.valueOf(lastLocation.getLongitude()));
            getAddress(lastLocation);
            gmmIntentUri = Uri.parse("geo:"+lastLocation.getLatitude()+","+lastLocation.getLongitude());
            mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");


        }
    }


}
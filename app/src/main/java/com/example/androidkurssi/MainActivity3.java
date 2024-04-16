package com.example.androidkurssi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "MainActivity3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





    }

    public static void searchCompanies(Context context, String query) {
        // URL, johon tehdään API-kutsu
        String apiUrl = "https://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=10&resultsFrom=0&name="+query+"&companyRegistrationFrom=2014-02-28";

        // Luo uusi RequestQueue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Luo JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Käsittely onnistuneen vastauksen saadessa
                        Log.d(TAG, "Vastaus: " + response.toString());

                        // Tässä voit käsitellä vastauksen ja päivittää käyttöliittymää

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Käsittely virheellisen vastauksen saadessa
                        Log.e(TAG, "Virhe: " + error.getMessage());

                        // Näytä virheilmoitus käyttäjälle
                        Toast.makeText(context, "Virhe haettaessa tietoja", Toast.LENGTH_SHORT).show();
                    }
                });

        // Määritä pyynnölle timeout
        request.setRetryPolicy(new DefaultRetryPolicy(
                20 * 1000, // timeout: 20 sekuntia
                1, // yritykset
                1.0f // kertoimet
        ));

        // Lisää pyyntö jonoon
        queue.add(request);
    }





}
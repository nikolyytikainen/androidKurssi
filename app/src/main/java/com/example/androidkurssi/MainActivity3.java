package com.example.androidkurssi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity3 extends AppCompatActivity {

    private RecyclerView recyclerView;
    // private RecyclerView.Adapter adapter;

    public MyAdapter adapter;
    ArrayList<JSONObject> resultsList = new ArrayList<>();
    String companyName;
    String url;
    ProgressBar pB;
    SearchView searchViewi;
    JSONArray companyArray;

    public class Company {
        private String businessId;
        private String name;
        private String registrationDate;
        private String companyForm;
        private String detailsUri;

        public Company(String businessId, String name, String registrationDate, String companyForm, String detailsUri) {
            this.businessId = businessId;
            this.name = name;
            this.registrationDate = registrationDate;
            this.companyForm = companyForm;
            this.detailsUri = detailsUri;
        }
        public String getBusinessId() {
            return businessId;
        }

        public  String getName() {
            return name;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public String getCompanyForm() {
            return companyForm;
        }

        public String getDetailsUri() {
            return detailsUri;
        }


        @Override
        public String toString() {
            return "Company{" +
                    "businessId='" + businessId + '\'' +
                    ", name='" + name + '\'' +
                    ", registrationDate='" + registrationDate + '\'' +
                    ", companyForm='" + companyForm + '\'' +
                    ", detailsUri='" + detailsUri + '\'' +
                    '}';
        }
    }

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

        pB = (ProgressBar) findViewById(R.id.progressBar);
        pB.setVisibility(View.VISIBLE);

        companyName = getIntent().getStringExtra("COMPANY_NAME");
        if (companyName != null) {
            Log.d("MainActivity3", "Company Name: " + companyName);


            RequestQueue queue = Volley.newRequestQueue(this);
            url = "https://avoindata.prh.fi/bis/v1?totalResults=false&maxResults=30&resultsFrom=0&name="+companyName+"&companyRegistrationFrom=2014-02-28";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("MainActivity3","Response: " + response.toString());
                            try {
                                companyArray = response.getJSONArray("results");
                                //
                                Map<String, List<Company>> companiesByDate = new TreeMap<>();
                                //
                                for (int i = 0; i < companyArray.length(); i++) {
                                    JSONObject resultObject = companyArray.getJSONObject(i);
                                    resultsList.add(resultObject);
                                }
                                Log.d("MainActivity3","tämä on lista resultseista" + resultsList.toString());
                                for (JSONObject result : resultsList) {
                                    String businessId = result.getString("businessId");
                                    String name = result.getString("name");
                                    String registrationDate = result.getString("registrationDate");
                                    String companyForm = result.getString("companyForm");
                                    String detailsUri = result.getString("detailsUri");

                                    Log.d("MainActivity3","Business ID: " + businessId);
                                    Log.d("MainActivity3","Name: " + name);
                                    Log.d("MainActivity3","Registration Date: " + registrationDate);
                                    Log.d("MainActivity3","Company Form: " + companyForm);

                                    Company company = new Company(businessId, name, registrationDate, companyForm, detailsUri);

                                    if (!companiesByDate.containsKey(registrationDate)) {
                                        companiesByDate.put(registrationDate, new ArrayList<>());
                                    }
                                    companiesByDate.get(registrationDate).add(company);

                                }
                                // Mappi listaksi company objekteista
                                List<Company> resultsList = new ArrayList<>();
                                for (List<Company> companies : companiesByDate.values()) {
                                    resultsList.addAll(companies);
                                }

                                // Ilmoita adapterille data muutoksista
                                adapter.setCompanies(resultsList);
                                adapter.notifyDataSetChanged();

                                pB.setVisibility(View.INVISIBLE);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.d("MainActivity3","jsonRequest ei toimi");
                        }
                    });

            // lisää kutsun kutsujonoon
            queue.add(jsonObjectRequest);
        } else {
            Log.d("MainActivity3", "Company Name ei löydy! ongelma!");
        }


        sRecyclerView();

        searchViewi = (SearchView)findViewById(R.id.srchVw);



        searchViewi.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("newText1",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("newText",newText);
                adapter.getFilter().filter(newText);
                return false;
            }
        });


    }
    private void sRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(resultsList);
        recyclerView.setAdapter(adapter);
    }




}
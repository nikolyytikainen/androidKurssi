package com.example.androidkurssi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    static List<JSONObject> resultsList;
    static List<JSONObject> filteredList;
    private List<MainActivity3.Company> companies;

    public void setCompanies(List<MainActivity3.Company> companies) {
        this.companies = companies;
    }
    public MyAdapter( List<JSONObject> resultsList) {

        this.resultsList = resultsList;
        this.filteredList = resultsList;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView companyNameTv;
        TextView companyIdTv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTv = itemView.findViewById(R.id.companyNameTextView);
            companyIdTv = itemView.findViewById(R.id.companyIdTextView);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);
        context = parent.getContext();
        Log.d("testi","toimiiko adapter");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MainActivity3.Company company = companies.get(position);

        holder.companyNameTv.setText(company.getName());
        holder.companyIdTv.setText("Y-Tunnus: "+company.getBusinessId() + "\n" + "Rekisteröity: "+company.getRegistrationDate()
                + "\n" + "Yritysmuoto: "+company.getCompanyForm()); // + "\n" +company.getDetailsUri()

        holder.companyIdTv.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (holder.companyIdTv.getVisibility()==View.VISIBLE){
                    holder.companyIdTv.setVisibility(View.GONE);
                }else{
                    holder.companyIdTv.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

public Filter getFilter() {
    return Searched_Filter;
}
    private Filter Searched_Filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<JSONObject> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(resultsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Log.d("FILTERPATTERN",filterPattern);
                for (JSONObject item : resultsList) {
                    try {
                        if (item.getString("name").toLowerCase().contains(filterPattern)) {
                            Log.d("ITEMNIMI",item.getString("name"));
                            filteredList.add(item);
                            Log.d("filtterilista",filteredList.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            resultsList.clear();
            resultsList.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

}
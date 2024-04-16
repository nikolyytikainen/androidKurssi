import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder> {
    private List<Company> companyList;
    private Context context;

    public CompanyAdapter(List<Company> companyList, Context context) {
        this.companyList = companyList;
        this.context = context;
    }

    @NonNull
    @Override
    public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyViewHolder holder, int position) {
        Company company = companyList.get(position);
        holder.companyNameTextView.setText(company.getName());
        holder.companyIdTextView.setText(company.getBusinessId());
        holder.companyTypeTextView.setText(company.getCompanyForm());
        holder.registrationDateTextView.setText(company.getRegistrationDate());
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public class CompanyViewHolder extends RecyclerView.ViewHolder {
        public TextView companyNameTextView;
        public TextView companyIdTextView;
        public TextView companyTypeTextView;
        public TextView registrationDateTextView;

        public CompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            companyIdTextView = itemView.findViewById(R.id.companyIdTextView);
            companyTypeTextView = itemView.findViewById(R.id.companyTypeTextView);
            registrationDateTextView = itemView.findViewById(R.id.registrationDateTextView);
        }
    }
}

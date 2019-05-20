package ukk.cikal.inventaris.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.models.ListOperator;

public class AdapterOperator extends RecyclerView.Adapter<AdapterOperator.ViewHolder> {

    private List<ListOperator> list_operator;

    public AdapterOperator(List<ListOperator> list_operator) {
        this.list_operator = list_operator;
    }

    @NonNull
    @Override
    public AdapterOperator.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_operator, viewGroup, false);
        return new AdapterOperator.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOperator.ViewHolder viewHolder, int i) {
        ListOperator lisData = list_operator.get(i);

        final String id = lisData.getIdPetugas();
        final String nama = lisData.getNamaPetugas();
        final String username = lisData.getUsername();
        viewHolder.txtNamaOperator.setText(nama);
        viewHolder.txtUsernameOperator.setText(username);

    }

    @Override
    public int getItemCount() {
        return list_operator.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaOperator, txtUsernameOperator;
        private ImageView imgInfoOperator;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamaOperator = itemView.findViewById(R.id.txtNamaOperator);
            txtUsernameOperator = itemView.findViewById(R.id.txtUsernameOperator);
            imgInfoOperator = itemView.findViewById(R.id.imgInfoOperator);
        }
    }
}

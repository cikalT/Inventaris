package ukk.cikal.inventaris.adapters;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.models.ListReport;

public class AdapterReport extends RecyclerView.Adapter<AdapterReport.ViewHolder> {

    private List<ListReport> list_report;
    public AdapterReport(List<ListReport> list_report) {
        this.list_report = list_report;
    }


    @NonNull
    @Override
    public AdapterReport.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_report, viewGroup, false);
        return new AdapterReport.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReport.ViewHolder viewHolder, int i) {
        ListReport lisData = list_report.get(i);

        final String tgl = lisData.getTglPinjam();
        final String namaInvent = lisData.getNamaInvent();
        final String namaPegawai = lisData.getNamaPegawai();
        final String jumlah = lisData.getJumlah();
        final String status = lisData.getStatusPinjam();

        viewHolder.txtNamaInvent.setText(namaInvent);
        viewHolder.txtJumlahInvent.setText(jumlah);
        viewHolder.txtNamaPeminjam.setText(namaPegawai);

        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-d", Locale.US);
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            Date dateFromDate = inputFormatter.parse(tgl);
            viewHolder.txtTgl.setText(String.format(outputFormatter.format(dateFromDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch (status) {
            case "1" :
                viewHolder.imgStatusPinjam.setImageResource(R.drawable.ic_hourglass_full_yellow);
                break;
            case "2" :
                viewHolder.imgStatusPinjam.setImageResource(R.drawable.ic_check_circle_green);
                break;
            case "3" :
                viewHolder.imgStatusPinjam.setImageResource(R.drawable.ic_cancel_red);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list_report.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTgl, txtNamaInvent, txtJumlahInvent, txtNamaPeminjam;
        private ImageView imgStatusPinjam;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTgl = itemView.findViewById(R.id.txtTgl);
            txtNamaInvent = itemView.findViewById(R.id.txtNamaInvent);
            txtJumlahInvent = itemView.findViewById(R.id.txtJumlahInvent);
            txtNamaPeminjam = itemView.findViewById(R.id.txtNamaPeminjam);
            imgStatusPinjam = itemView.findViewById(R.id.imgStatusPinjam);
        }
    }
}

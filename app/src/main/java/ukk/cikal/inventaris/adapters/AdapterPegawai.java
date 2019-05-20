package ukk.cikal.inventaris.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.models.ListPegawai;

public class AdapterPegawai extends RecyclerView.Adapter<AdapterPegawai.ViewHolder> {

    private List<ListPegawai> list_pegawai;

    public AdapterPegawai(List<ListPegawai> list_pegawai) {
        this.list_pegawai = list_pegawai;
    }

    @NonNull
    @Override
    public AdapterPegawai.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_pegawai, viewGroup, false);
        return new AdapterPegawai.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPegawai.ViewHolder viewHolder, int i) {
        ListPegawai lisData = list_pegawai.get(i);

        final String id = lisData.getIdPegawai();
        final String nama = lisData.getNamaPegawai();
        final String nip = lisData.getNip();
        final String alamat = lisData.getAlamat();

        viewHolder.txtNamaPegawai.setText(nama);
        if (nip.isEmpty() || nip.equals("")) {
            viewHolder.txtNipPegawai.setText("Tidak ada NIP");
        } else {
            viewHolder.txtNipPegawai.setText(nip);
        }
        viewHolder.txtAlamatPegawai.setText(alamat);
    }

    @Override
    public int getItemCount() {
        return list_pegawai.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaPegawai, txtNipPegawai, txtAlamatPegawai;
        private ImageView imgInfoPegawai;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamaPegawai = itemView.findViewById(R.id.txtNamaPegawai);
            txtNipPegawai = itemView.findViewById(R.id.txtNipPegawai);
            txtAlamatPegawai = itemView.findViewById(R.id.txtAlamatPegawai);
            imgInfoPegawai = itemView.findViewById(R.id.imgInfoPegawai);
        }
    }
}

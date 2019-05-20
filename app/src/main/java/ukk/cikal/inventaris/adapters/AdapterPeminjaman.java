package ukk.cikal.inventaris.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.models.ListInvent;
import ukk.cikal.inventaris.views.peminjaman.FormPeminjamanActivity;

public class AdapterPeminjaman extends RecyclerView.Adapter<AdapterPeminjaman.ViewHolder> {

    private List<ListInvent> list_invent;

    public AdapterPeminjaman(List<ListInvent> list_invent) {
        this.list_invent = list_invent;
    }

    @NonNull
    @Override
    public AdapterPeminjaman.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_invent, viewGroup, false);
        return new AdapterPeminjaman.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPeminjaman.ViewHolder viewHolder, int i) {
        ListInvent listData = list_invent.get(i);

        final String id = listData.getIdInvent();
        final String nama = listData.getNamaInvent();
        final String idkondisi = listData.getKondisiInvent();
        final String idket = listData.getKetInvent();
        final String jumlah = listData.getJumlahInvent();
        final String jenis = listData.getNamaJenis();
        final String ruang = listData.getNamaRuang();

        viewHolder.txtNamaInvent.setText(nama);
        viewHolder.txtJumlahInvent.setText(jumlah);

        switch (idkondisi) {
            case "1" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi1);
                break;
            case "2" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi2);
                break;
            case "3" :
                viewHolder.txtKondisiInvent.setText(R.string.kondisi3);
                break;
        }

        switch (idket) {
            case "1" :
                viewHolder.txtKetInvent.setText(R.string.ket1);
                break;
            case "2" :
                viewHolder.txtKetInvent.setText(R.string.ket2);
                break;
        }

        viewHolder.imgDetailInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FormPeminjamanActivity.class);
                intent.putExtra("idinvent", id);
                intent.putExtra("nama", nama);
                intent.putExtra("kondisi", idkondisi);
                intent.putExtra("ket", idket);
                intent.putExtra("jumlah", jumlah);
                intent.putExtra("jenis", jenis);
                intent.putExtra("ruang", ruang);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_invent.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNamaInvent, txtJumlahInvent, txtKondisiInvent, txtKetInvent;
        private ImageView imgDetailInvent;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamaInvent = itemView.findViewById(R.id.txtNamaInvent);
            txtJumlahInvent = itemView.findViewById(R.id.txtJumlahInvent);
            txtKondisiInvent = itemView.findViewById(R.id.txtKondisiInvent);
            txtKetInvent = itemView.findViewById(R.id.txtKetInvent);
            imgDetailInvent = itemView.findViewById(R.id.imgDetailInvent);
        }
    }
}

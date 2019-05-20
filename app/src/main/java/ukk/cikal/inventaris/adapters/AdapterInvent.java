package ukk.cikal.inventaris.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ukk.cikal.inventaris.views.admin.FormDetailInventActivity;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.models.ListInvent;

public class AdapterInvent extends RecyclerView.Adapter<AdapterInvent.ViewHolder> {

    private List<ListInvent> list_invent;

    public AdapterInvent(List<ListInvent> list_invent) {
        this.list_invent = list_invent;
    }

    @NonNull
    @Override
    public AdapterInvent.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_invent, viewGroup, false);
        return new AdapterInvent.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterInvent.ViewHolder viewHolder, int i) {
        ListInvent listData = list_invent.get(i);

        final String id = listData.getIdInvent();
        final String nama = listData.getNamaInvent();
        final String idkondisi = listData.getKondisiInvent();
        final String idket = listData.getKetInvent();
        final String jumlah = listData.getJumlahInvent();
        final String idjenis = listData.getIdJenis();
        final String idruang = listData.getIdRuang();

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
                Intent intent = new Intent(v.getContext(), FormDetailInventActivity.class);
                intent.putExtra("idinvent", id);
                intent.putExtra("nama", nama);
                intent.putExtra("kondisi", idkondisi);
                intent.putExtra("ket", idket);
                intent.putExtra("jumlah", jumlah);
                intent.putExtra("jenis", idjenis);
                intent.putExtra("ruang", idruang);
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

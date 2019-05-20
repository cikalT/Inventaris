package ukk.cikal.inventaris.views.operator;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.ListPegawaiActivity;
import ukk.cikal.inventaris.views.MainActivity;
import ukk.cikal.inventaris.views.peminjaman.PeminjamanActivity;

public class OperatorActivity extends AppCompatActivity {

    @BindView(R.id.imgPendaftaran)
    ImageView imgPendaftaran;
    @BindView(R.id.llList)
    LinearLayout llList;
    @BindView(R.id.imgPeminjaman)
    ImageView imgPeminjaman;
    @BindView(R.id.imgPengembalian)
    ImageView imgPengembalian;
    @BindView(R.id.llOperator)
    LinearLayout llOperator;
    @BindView(R.id.btnLogOut)
    Button btnLogOut;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
    }

    @OnClick({R.id.imgPendaftaran, R.id.imgPeminjaman, R.id.imgPengembalian, R.id.btnLogOut})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgPendaftaran:
                Intent intent1 = new Intent(OperatorActivity.this, ListPegawaiActivity.class);
                startActivity(intent1);
//                finish(); //WARNING
                break;
            case R.id.imgPeminjaman:
                Intent intent2 = new Intent(OperatorActivity.this, PeminjamanActivity.class);
                startActivity(intent2);
//                finish(); //WARNING
                break;
            case R.id.imgPengembalian:
                dialogPengembalian();
                break;
            case R.id.btnLogOut:
                dialogLogOut();
                break;
        }
    }

    private void dialogPengembalian() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(OperatorActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_pengembalian, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.setTitle("Masukkan kode peminjaman");

        final EditText edtKodePeminjaman = dialogView.findViewById(R.id.edtKodePeminjaman);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String kode = edtKodePeminjaman.getText().toString();
                if (!kode.isEmpty()) {
                    cekKode(kode);
                } else {
                    Toast.makeText(OperatorActivity.this, "Masukkan kode peminjaman!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void cekKode(final String kode) {
        final Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        final String tglSkrg = simpleDateFormat.format(date);
        Log.i("TAG", "cekKode: " + Api.getCekKode(kode, tglSkrg));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getCekKode(kode, tglSkrg), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("kode");
                    if (success.equals("1")) {
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            String kode, status, tgl;
                            kode = object.getString("KodePinjaman_");
                            status = object.getString("StatusPinjam_");
                            tgl = object.getString("TglKembali_");
                            Log.i("TAG", "onResponse: " + kode + " " + status + " " + tgl);

                            if (status.equals("2") || status.equals("3")) {
                                Toast.makeText(OperatorActivity.this, "Tidak Valid", Toast.LENGTH_SHORT).show();
                            }
                            if (status.equals("1")) {
                                prosesPengembalian(kode);
                                Toast.makeText(OperatorActivity.this, "Barang dikembalikan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    if (success.equals("0")) {
                        Toast.makeText(OperatorActivity.this, "Kode Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void prosesPengembalian(String kode) {
        Log.i("TAG", "prosesPengembalian: " + Api.getPengembalian(kode));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getPengembalian(kode), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        Log.i("VolleyResponse", "Success: " + jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
        Toast.makeText(OperatorActivity.this, "Barang dikembalikan", Toast.LENGTH_SHORT).show();
    }

    private void dialogLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OperatorActivity.this);
        builder.setTitle("Log out");
        builder.setMessage("Anda yakin ingin logout?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOut();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void logOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utils.sharedId, "");
        editor.putString(Utils.sharedNama, "");
        editor.putString(Utils.sharedNip, "");
        editor.putString(Utils.sharedLevel, "");
        editor.putString(Utils.sharedAlamat, "");
        editor.putString(Utils.sharedUsername, "");
        editor.putString(Utils.sharedPassword, "");
        editor.apply();

        Intent intent = new Intent(OperatorActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

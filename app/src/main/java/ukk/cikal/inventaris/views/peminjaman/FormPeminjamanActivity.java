package ukk.cikal.inventaris.views.peminjaman;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.admin.AdminActivity;
import ukk.cikal.inventaris.views.operator.OperatorActivity;
import ukk.cikal.inventaris.views.pegawai.PegawaiActivity;

public class FormPeminjamanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.autoNamaPegawai)
    AutoCompleteTextView autoNamaPegawai;
    @BindView(R.id.txtNamaInvent)
    TextView txtNamaInvent;
    @BindView(R.id.imgInfoInvent)
    ImageView imgInfoInvent;
    @BindView(R.id.edtJumlahPinjaman)
    EditText edtJumlahPinjaman;
    @BindView(R.id.btnTglPinjam)
    Button btnTglPinjam;
    @BindView(R.id.txtTglPinjam)
    TextView txtTglPinjam;
    @BindView(R.id.btnTglKembali)
    Button btnTglKembali;
    @BindView(R.id.txtTglKembali)
    TextView txtTglKembali;
    @BindView(R.id.btnTambahPinjaman)
    Button btnTambahPinjaman;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    Calendar calendar;
    Date date;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat;

    ArrayList<String> pegawai;
    String idInvent, namaInvent, jumlahInvent, kondisiInvent, ketInvent, jenisInvent, ruangInvent, namaPegawai, idPegawai, jumlahPinjam, tglPinjam, tglKembali;

    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_peminjaman);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
        getSetExtra();

        calendar = Calendar.getInstance();
        date = Calendar.getInstance().getTime();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        tglPinjam = simpleDateFormat.format(date);
        txtTglPinjam.setText(tglPinjam);

        pegawai = new ArrayList<>();
        autoNamaPegawai.setOnItemSelectedListener(this);
        getPegawai();

        level = sharedPreferences.getString(Utils.sharedLevel, "");
        assert level != null;
        if (level.equals("3")) {
            String nama = sharedPreferences.getString(Utils.sharedNama, "");
            autoNamaPegawai.setText(nama);
            autoNamaPegawai.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            autoNamaPegawai.setEnabled(false);
        }

    }

    @OnClick({R.id.imgInfoInvent, R.id.btnTglPinjam, R.id.btnTglKembali, R.id.btnTambahPinjaman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgInfoInvent:
                dialogInfoInvent();
                break;
            case R.id.btnTglPinjam:
                datePinjam();
                break;
            case R.id.btnTglKembali:
                dateKembali();
                break;
            case R.id.btnTambahPinjaman:
                getIdPegawai();
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void getSetExtra() {
        idInvent = getIntent().getStringExtra("idinvent");
        namaInvent = getIntent().getStringExtra("nama");
        jumlahInvent = getIntent().getStringExtra("jumlah");
        kondisiInvent = getIntent().getStringExtra("kondisi");
        ketInvent = getIntent().getStringExtra("ket");
        jenisInvent = getIntent().getStringExtra("jenis");
        ruangInvent = getIntent().getStringExtra("ruang");
        txtNamaInvent.setText(namaInvent);
    }

    private void getPegawai() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getPegawai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    setPegawai(jsonArray);
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

    private void setPegawai(JSONArray jsonArray) {
        for (int i=0; i<jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                pegawai.add(object.getString("NamaPegawai_"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, pegawai) {

        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoNamaPegawai.setAdapter(spinnerAdapter);
        autoNamaPegawai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    private void dialogInfoInvent() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(FormPeminjamanActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_info_invent, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final TextView txtNamaInvent, txtKondisiInvent, txtKetInvent, txtJenisInvent, txtRuangInvent;
        txtNamaInvent = dialogView.findViewById(R.id.txtNamaInvent);
        txtKondisiInvent = dialogView.findViewById(R.id.txtKondisiInvent);
        txtKetInvent = dialogView.findViewById(R.id.txtKetInvent);
        txtJenisInvent = dialogView.findViewById(R.id.txtJenisInvent);
        txtRuangInvent = dialogView.findViewById(R.id.txtRuangInvent);

        txtNamaInvent.setText(namaInvent);
        switch (kondisiInvent) {
            case "1" :
                txtKondisiInvent.setText(R.string.kondisi1);
                break;
            case "2" :
                txtKondisiInvent.setText(R.string.kondisi2);
                break;
            case "3" :
                txtKondisiInvent.setText(R.string.kondisi3);
                break;
        }
        switch (ketInvent) {
            case "1" :
                txtKetInvent.setText(R.string.ket1);
                break;
            case "2" :
                txtKetInvent.setText(R.string.ket2);
                break;
        }
        txtKetInvent.setText(namaInvent);
        txtJenisInvent.setText(jenisInvent);
        txtRuangInvent.setText(ruangInvent);

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void datePinjam() {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);

                tglPinjam = simpleDateFormat.format(calendar.getTime());
                txtTglPinjam.setText(tglPinjam);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void dateKembali() {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);

                tglKembali = simpleDateFormat.format(calendar.getTime());
                txtTglKembali.setText(tglKembali);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getIdPegawai() {
        namaPegawai = autoNamaPegawai.getText().toString();
        namaPegawai = namaPegawai.replaceAll(" ", "%20");
        Log.i("TAG", "getIdPegawai: " + Api.getIdPegawai(namaPegawai));
        if (!namaPegawai.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getIdPegawai(namaPegawai), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("pegawai");
                        if (success.equals("1")) {
                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                idPegawai = object.getString("IDPegawai_");
                                validasiData(idPegawai);
                            }
                        }
                        if (success.equals("0")) {
                            Toast.makeText(FormPeminjamanActivity.this, "Pegawai tidak terdaftar", Toast.LENGTH_SHORT).show();
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
        } else {
            Toast.makeText(this, "Masukkan nama pegawai!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void validasiData(String idd) {
//        Toast.makeText(this, idd, Toast.LENGTH_SHORT).show();
        jumlahPinjam = edtJumlahPinjaman.getText().toString();
        tglKembali = txtTglKembali.getText().toString();

        if (jumlahPinjam.isEmpty() || tglKembali.equals("Date Selected")) {
            Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
        } else {
            int Tjumlahpinjam = Integer.parseInt(jumlahPinjam);
            int Tjumlahinvent = Integer.parseInt(jumlahInvent);

            String validasiJumlah = "true";
            if (Tjumlahpinjam>Tjumlahinvent) {
                validasiJumlah = "false";
            }

            if (validasiJumlah.equals("false")) {
                Toast.makeText(this, "Jumlah pinjaman melebihi stok yang ada", Toast.LENGTH_SHORT).show();
            } else {
                addPinjaman(idd);
            }
        }
    }

    private void addPinjaman(String idd) {
        String app = Api.getAddPeminjaman(idd, idInvent, jumlahPinjam, tglPinjam, tglKembali);
        Log.i("TAG", "validasiData: " + app);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Api.getAddPeminjaman(idd, idInvent, jumlahPinjam, tglPinjam, tglKembali), new Response.Listener<String>() {
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
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(stringRequest);
        Toast.makeText(this, "Berhasil meminjam " + jumlahPinjam + " " + namaInvent, Toast.LENGTH_SHORT).show();

        switch (level) {
            case "1" :
                Intent intent1 = new Intent(FormPeminjamanActivity.this, AdminActivity.class);
                startActivity(intent1);
                finish();
                break;
            case "2" :
                Intent intent2 = new Intent(FormPeminjamanActivity.this, OperatorActivity.class);
                startActivity(intent2);
                finish();
                break;
            case "3" :
                Intent intent3 = new Intent(FormPeminjamanActivity.this, PegawaiActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }
}

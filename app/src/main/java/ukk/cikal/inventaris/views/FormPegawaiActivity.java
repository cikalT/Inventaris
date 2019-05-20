package ukk.cikal.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;

public class FormPegawaiActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaPegawai)
    EditText edtNamaPegawai;
    @BindView(R.id.cbxNip)
    CheckBox cbxNip;
    @BindView(R.id.edtNipPegawai)
    EditText edtNipPegawai;
    @BindView(R.id.edtAlamatPegawai)
    EditText edtAlamatPegawai;
    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.cbxPassword)
    CheckBox cbxPassword;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnTambah)
    Button btnTambah;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_pegawai);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        cbxNip.setChecked(true);
        cbxPassword.setChecked(true);

    }

    @OnClick(R.id.btnTambah)
    public void onViewClicked() {
        validasiData();
    }

    private void validasiData() {
        if (cbxNip.isChecked()) {
            status = "Nip";
            edtNipPegawai.setEnabled(true);
        } else {
            status = "NoNip";
        }

        String nama = edtNamaPegawai.getText().toString();
        String nip = edtNipPegawai.getText().toString();
        String alamat = edtAlamatPegawai.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        switch (status) {
            case "Nip" :
                if (!nama.isEmpty() && !nip.isEmpty() && !alamat.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    insertWithNip(nama, nip, alamat, username, password);
                    Toast.makeText(this, "HALOOOO", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
                }

                break;
            case "NoNip" :
                if (!nama.isEmpty() && !alamat.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    insertWithoutNip(nama, alamat, username, password);
                    Toast.makeText(this, "HALOOOO TANPA NIP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void insertWithNip(String nama, String nip, String alamat, String username, String password) {
        nama = nama.replace(" ", "%20");
        Log.i("TAG", "insertWithNip: " + Api.getAddPegawai(nama, nip, alamat, username, password));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getAddPegawai(nama, nip, alamat, username, password), new Response.Listener<String>() {
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
        Intent intent = new Intent(FormPegawaiActivity.this, ListPegawaiActivity.class);
        startActivity(intent);
        finish();
    }

    private void insertWithoutNip(String nama, String alamat, String username, String password) {
        nama = nama.replace(" ", "%20");
        Log.i("TAG", "insertWithoutNip: " + Api.getAddPegawaiNoNip(nama, alamat, username, password));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getAddPegawaiNoNip(nama, alamat, username, password), new Response.Listener<String>() {
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
        Intent intent = new Intent(FormPegawaiActivity.this, ListPegawaiActivity.class);
        startActivity(intent);
        finish();
    }
}

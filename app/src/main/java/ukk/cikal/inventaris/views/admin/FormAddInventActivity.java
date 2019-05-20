package ukk.cikal.inventaris.views.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;

public class FormAddInventActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaInvent)
    EditText edtNamaInvent;
    @BindView(R.id.spnKondisiInvent)
    Spinner spnKondisiInvent;
    @BindView(R.id.spnKetInvent)
    Spinner spnKetInvent;
    @BindView(R.id.edtJumlahInvent)
    EditText edtJumlahInvent;
    @BindView(R.id.spnJenisInvent)
    Spinner spnJenisInvent;
    @BindView(R.id.spnRuangInvent)
    Spinner spnRuangInvent;
    @BindView(R.id.btnTambah)
    Button btnTambah;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    List<String> dataJenis;
    List<String> dataRuang;

    String namaInvent, kondisiInvent, ketInvent, jumlahInvent, jenisInvent, ruangInvent, idPetugas;
    long idkondisi, idket, idjenis, idruang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_add_invent);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        dataJenis = new ArrayList<>();
        dataRuang = new ArrayList<>();
        getJenis();
        getRuang();
    }

    @OnClick(R.id.btnTambah)
    public void onViewClicked() {
        getData();
        addInvent();
    }

    private void getJenis() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getJenis, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    setSpnJenis(jsonArray);
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

    private void setSpnJenis(JSONArray jsonArray) {
        dataJenis.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                dataJenis.add(object.getString("NamaJenis_"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dataJenis);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnJenisInvent.setAdapter(spinnerAdapter);
    }

    private void getRuang() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getRuang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    setSpnRuang(jsonArray);
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

    private void setSpnRuang(JSONArray jsonArray) {
        dataRuang.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                dataRuang.add(object.getString("NamaRuang_"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, dataRuang);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRuangInvent.setAdapter(spinnerAdapter);
    }

    private void getData() {
        idkondisi = spnKondisiInvent.getSelectedItemId() + 1;
        idket = spnKetInvent.getSelectedItemId() + 1;
        idjenis = spnJenisInvent.getSelectedItemId() + 1;
        idruang = spnRuangInvent.getSelectedItemId() + 1;

        namaInvent = edtNamaInvent.getText().toString();
        jumlahInvent = edtJumlahInvent.getText().toString();
        kondisiInvent = String.valueOf(idkondisi);
        ketInvent = String.valueOf(idkondisi);
        jenisInvent = String.valueOf(idjenis);
        ruangInvent = String.valueOf(idruang);
        idPetugas = sharedPreferences.getString(Utils.sharedId, "");
    }

    private void addInvent() {
        if (!namaInvent.isEmpty() && !jumlahInvent.isEmpty()) {
            namaInvent = namaInvent.replaceAll(" ", "%20");
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getAddInvent(
                    namaInvent, kondisiInvent, ketInvent, jumlahInvent, jenisInvent, ruangInvent, idPetugas), new Response.Listener<String>() {
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
            Log.i("VolleyResponse", "addInvent: " + stringRequest);
            requestQueue.add(stringRequest);
            Toast.makeText(this, "Data berhasil masuk", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FormAddInventActivity.this, AdminActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
        }
    }
}

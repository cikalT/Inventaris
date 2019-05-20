package ukk.cikal.inventaris.views.admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.FormPegawaiActivity;
import ukk.cikal.inventaris.views.ListPegawaiActivity;

public class FormOperatorActivity extends AppCompatActivity {

    @BindView(R.id.edtNamaOperator)
    EditText edtNamaOperator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_operator);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        cbxPassword.setChecked(true);
    }

    @OnClick({R.id.cbxPassword, R.id.btnTambah})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cbxPassword:
                break;
            case R.id.btnTambah:
                validasiData();
                break;
        }
    }

    private void validasiData() {
        String nama = edtNamaOperator.getText().toString();
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (!nama.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            addOperator(nama, username, password);
        } else {
            Toast.makeText(this, "Isi data dengan lengkap!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addOperator(String nama, String username, String password) {
        Log.i("TAG", "addOperator: " + Api.getAddOperator(nama, username, password));
        nama = nama.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getAddOperator(nama, username, password), new Response.Listener<String>() {
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
        Intent intent = new Intent(FormOperatorActivity.this, ListOperatorActivity.class);
        startActivity(intent);
        finish();
    }
}

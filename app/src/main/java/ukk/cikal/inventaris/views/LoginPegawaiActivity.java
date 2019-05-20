package ukk.cikal.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.admin.AdminActivity;
import ukk.cikal.inventaris.views.operator.OperatorActivity;
import ukk.cikal.inventaris.views.pegawai.PegawaiActivity;

public class LoginPegawaiActivity extends AppCompatActivity {

    @BindView(R.id.edtUsername)
    EditText edtUsername;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pegawai);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
        validasi();
    }

    private void validasi() {
        String username, password;
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();

        if (!username.isEmpty() && !password.isEmpty()) {
            login(username, password);
        } else {
            Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String username, String password) {
        Log.i("TAG", "login: " + Api.getLoginPegawai(username, password));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getLoginPegawai(username, password), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if (success.equals("1")) {
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Utils.sharedId, object.getString("IDPegawai_"));
                            editor.putString(Utils.sharedNama, object.getString("NamaPegawai_"));
                            editor.putString(Utils.sharedNip, object.getString("Nip_"));
                            editor.putString(Utils.sharedAlamat, object.getString("Alamat_"));
                            editor.putString(Utils.sharedLevel, "3");
                            editor.putString(Utils.sharedUsername, object.getString("Username_"));
                            editor.putString(Utils.sharedPassword, object.getString("Password_"));
                            editor.apply();

                            String nama = object.getString("NamaPegawai_");
                            Intent intent = new Intent(LoginPegawaiActivity.this, PegawaiActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginPegawaiActivity.this, "Selamat datang - " + nama, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (success.equals("0")) {
                        Toast.makeText(LoginPegawaiActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
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
}

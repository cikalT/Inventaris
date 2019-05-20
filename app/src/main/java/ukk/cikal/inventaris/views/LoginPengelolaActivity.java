package ukk.cikal.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginPengelolaActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_login_pengelola);
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getLoginPengelola(username, password), new Response.Listener<String>() {
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
                            editor.putString(Utils.sharedId, object.getString("IDPetugas_"));
                            editor.putString(Utils.sharedNama, object.getString("NamaPetugas_"));
                            editor.putString(Utils.sharedLevel, object.getString("Level_"));
                            editor.putString(Utils.sharedUsername, object.getString("Username_"));
                            editor.putString(Utils.sharedPassword, object.getString("Password_"));
                            editor.apply();

                            String level = object.getString("Level_");
                            String nama = object.getString("NamaPetugas_");
                            switch (level) {
                                case "1" :
                                    Intent intent1 = new Intent(LoginPengelolaActivity.this, AdminActivity.class);
                                    startActivity(intent1);
                                    finish();
                                    Toast.makeText(LoginPengelolaActivity.this, "Selamat datang - " + nama, Toast.LENGTH_SHORT).show();
                                    break;
                                case "2" :
                                    Intent intent2 = new Intent(LoginPengelolaActivity.this, OperatorActivity.class);
                                    startActivity(intent2);
                                    finish();
                                    Toast.makeText(LoginPengelolaActivity.this, "Selamat datang - " + nama, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                    if (success.equals("0")) {
                        Toast.makeText(LoginPengelolaActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
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

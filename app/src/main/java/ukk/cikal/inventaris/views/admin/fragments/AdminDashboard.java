package ukk.cikal.inventaris.views.admin.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.models.ListPegawai;
import ukk.cikal.inventaris.views.ListPegawaiActivity;
import ukk.cikal.inventaris.views.MainActivity;
import ukk.cikal.inventaris.views.admin.ListOperatorActivity;
import ukk.cikal.inventaris.views.admin.ReportActivity;

public class AdminDashboard extends Fragment {

    @BindView(R.id.txtNamaUser)
    TextView txtNamaUser;
    @BindView(R.id.txtJumlahOperator)
    TextView txtJumlahOperator;
    @BindView(R.id.llOperator)
    LinearLayout llOperator;
    @BindView(R.id.txtJumlahPegawai)
    TextView txtJumlahPegawai;
    @BindView(R.id.llPegawai)
    LinearLayout llPegawai;
    @BindView(R.id.txtJumlahPinjaman)
    TextView txtJumlahPinjaman;
    @BindView(R.id.llReport)
    LinearLayout llReport;
    @BindView(R.id.btnLogout)
    Button btnLogout;
    Unbinder unbinder;

    Context context;
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    String tOperator, tPegawai, tReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = this.getActivity();
        sharedPreferences = context.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(context);

        String nama = sharedPreferences.getString(Utils.sharedNama, "");
        txtNamaUser.setText(nama);

        getDataCount();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llOperator, R.id.llPegawai, R.id.llReport, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llOperator:
                Intent intent1 = new Intent(context, ListOperatorActivity.class);
                startActivity(intent1);
                break;
            case R.id.llPegawai:
                Intent intent2 = new Intent(context, ListPegawaiActivity.class);
                startActivity(intent2);
                break;
            case R.id.llReport:
                Intent intent3 = new Intent(context, ReportActivity.class);
                startActivity(intent3);
                break;
            case R.id.btnLogout:
                dialogLogOut();
                break;
        }
    }

    private void getDataCount() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getCount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("total");
                    if (success.equals("1")) {
                        for (int i=0; i<jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            tOperator = object.getString("JumlahOperator_");
                            tPegawai = object.getString("JumlahPegawai_");
                            tReport = object.getString("JumlahPinjaman_");

                            txtJumlahOperator.setText(tOperator);
                            txtJumlahPegawai.setText(tPegawai);
                            txtJumlahPinjaman.setText(tReport);
                        }
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

    private void dialogLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        this.getActivity().finish();
    }
}

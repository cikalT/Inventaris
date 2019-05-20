package ukk.cikal.inventaris.views.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.adapters.AdapterReport;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.models.ListReport;

public class ReportActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.btnPickDate)
    Button btnPickDate;
    @BindView(R.id.btnDetail)
    Button btnDetail;
    @BindView(R.id.rvListReport)
    RecyclerView rvListReport;
    @BindView(R.id.swipeListReport)
    SwipeRefreshLayout swipeListReport;

    RequestQueue requestQueue;
    AdapterReport adapterReport;
    List<ListReport> list_report;
    String tglAwal, tglAkhir, rangeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);
        rvListReport.setLayoutManager(new LinearLayoutManager(this));
        list_report = new ArrayList<>();
        adapterReport = new AdapterReport(list_report);

        swipeListReport.setOnRefreshListener(this);

        getListReport();
    }

    @OnClick({R.id.btnPickDate, R.id.btnDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPickDate:
                pickDate();
                break;
            case R.id.btnDetail:
                getCount(tglAwal, tglAkhir);
                break;
        }
    }

    @Override
    public void onRefresh() {
        btnPickDate.setText("Rentang Waktu");
        btnDetail.setVisibility(View.INVISIBLE);
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        getListReport();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        monthOfYear += 1;
        monthOfYearEnd += 1;
        tglAwal = year + "-" + monthOfYear + "-" + dayOfMonth;
        tglAkhir = yearEnd + "-" + monthOfYearEnd + "-" + dayOfMonthEnd;

        int dayPlus = dayOfMonthEnd + 1;
        String dToSend = yearEnd + "-" + monthOfYearEnd + "-" + dayPlus;

//        getReport(dFrom, dToSend);

        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-d", Locale.US);
            SimpleDateFormat outputFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            Date dateFromDate = inputFormatter.parse(tglAwal);
            Date dateToDate = inputFormatter.parse(tglAkhir);
            rangeDate = String.format("%s - %s", outputFormatter.format(dateFromDate), outputFormatter.format(dateToDate));
            btnPickDate.setText(rangeDate);
            filterReport(tglAwal, tglAkhir);
            btnDetail.setVisibility(View.VISIBLE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void pickDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                ReportActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setStartTitle("Dari");
        dpd.setEndTitle("Sampai");
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    private void getListReport() {
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        swipeListReport.setRefreshing(true);
        Log.i("TAG", "getListReport: " + Api.getReport);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getReport, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListReport listData = new ListReport(
                                object.getString("IDPeminjaman_"),
                                object.getString("IDInvent_"),
                                object.getString("IDPegawai_"),
                                object.getString("Jumlah_"),
                                object.getString("TglPinjam_"),
                                object.getString("TglKembali_"),
                                object.getString("StatusPinjam_"),
                                object.getString("KodePinjaman_"),
                                object.getString("NamaInvent_"),
                                object.getString("NamaPegawai_")
                        );
                        list_report.add(listData);
                    }
                    rvListReport.setAdapter(adapterReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterReport.notifyDataSetChanged();
                swipeListReport.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListReport.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void filterReport(String awal, String akhir) {
        list_report.clear();
        adapterReport.notifyDataSetChanged();
        swipeListReport.setRefreshing(true);
        Log.i("TAG", "getListReport: " + Api.getFilterReport(awal, akhir));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getFilterReport(awal, akhir), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListReport listData = new ListReport(
                                object.getString("IDPeminjaman_"),
                                object.getString("IDInvent_"),
                                object.getString("IDPegawai_"),
                                object.getString("Jumlah_"),
                                object.getString("TglPinjam_"),
                                object.getString("TglKembali_"),
                                object.getString("StatusPinjam_"),
                                object.getString("KodePinjaman_"),
                                object.getString("NamaInvent_"),
                                object.getString("NamaPegawai_")
                        );
                        list_report.add(listData);
                    }
                    rvListReport.setAdapter(adapterReport);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterReport.notifyDataSetChanged();
                swipeListReport.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListReport.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void getCount(String awal, String akhir) {
        Log.i("TAG", "getCount: " + Api.getFilterCount(awal, akhir));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getFilterCount(awal, akhir), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String total, kembali, dipinjam, terlambat;
                        total = object.getString("Total_");
                        dipinjam = object.getString("Dipinjam_");
                        kembali = object.getString("Dikembalikan_");
                        terlambat = object.getString("Terlambat_");
//                        Toast.makeText(ReportActivity.this, total, Toast.LENGTH_SHORT).show();
                        dialogReport(total, dipinjam, kembali, terlambat);
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

    private void dialogReport(String total, String dipinjam, String kembali, String terlambat) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ReportActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_report, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        final TextView txtTglReport, txtTotal, txtPinjam, txtKembali, txtTerlambat;

        txtTglReport = dialogView.findViewById(R.id.txtTglReport);
        txtTotal = dialogView.findViewById(R.id.txtTotal);
        txtPinjam = dialogView.findViewById(R.id.txtPinjam);
        txtKembali = dialogView.findViewById(R.id.txtKembali);
        txtTerlambat = dialogView.findViewById(R.id.txtTerlambat);

        txtTglReport.setText(rangeDate);
        txtTotal.setText(total);
        txtPinjam.setText(dipinjam);
        txtKembali.setText(kembali);
        txtTerlambat.setText(terlambat);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}

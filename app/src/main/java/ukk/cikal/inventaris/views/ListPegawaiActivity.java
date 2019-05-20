package ukk.cikal.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

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
import ukk.cikal.inventaris.adapters.AdapterPegawai;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.models.ListPegawai;

public class ListPegawaiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener{

    @BindView(R.id.svPegawai)
    SearchView svPegawai;
    @BindView(R.id.rvListPegawai)
    RecyclerView rvListPegawai;
    @BindView(R.id.swipeListPegawai)
    SwipeRefreshLayout swipeListPegawai;
    @BindView(R.id.fabAddPegawai)
    FloatingActionButton fabAddPegawai;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    AdapterPegawai adapterPegawai;
    List<ListPegawai> list_pegawai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pegawai);
        ButterKnife.bind(this);
        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        rvListPegawai.setLayoutManager(new LinearLayoutManager(this));
        list_pegawai = new ArrayList<>();
        adapterPegawai = new AdapterPegawai(list_pegawai);

        swipeListPegawai.setOnRefreshListener(this);
        svPegawai.setOnQueryTextListener(this);

        getListPegawai();

        svPegawai.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                swipeListPegawai.setRefreshing(true);
                getListPegawai();
                return false;
            }
        });
    }

    @OnClick({R.id.svPegawai, R.id.fabAddPegawai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.svPegawai:
                svPegawai.setIconified(false);
                break;
            case R.id.fabAddPegawai:
                Intent intent = new Intent(ListPegawaiActivity.this, FormPegawaiActivity.class);
                startActivity(intent);
//                finish(); //WARNING
                break;
        }
    }

    @Override
    public void onRefresh() {
        list_pegawai.clear();
        adapterPegawai.notifyDataSetChanged();
        getListPegawai();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchPegawai(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.equals("")) {
            swipeListPegawai.setEnabled(true);
            getListPegawai();
        } else {
            swipeListPegawai.setRefreshing(false);
            swipeListPegawai.setEnabled(false);
            searchPegawai(query);
            Log.i("TAG", "onQueryTextChange: " + Api.getSearchPegawai(query));
        }
        return false;
    }

    private void getListPegawai() {
        list_pegawai.clear();
        adapterPegawai.notifyDataSetChanged();
        swipeListPegawai.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getPegawai, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListPegawai listData = new ListPegawai(
                                object.getString("IDPegawai_"),
                                object.getString("NamaPegawai_"),
                                object.getString("Nip_"),
                                object.getString("Alamat_"),
                                object.getString("Username_"),
                                object.getString("Password_")
                        );
                        list_pegawai.add(listData);
                    }
                    rvListPegawai.setAdapter(adapterPegawai);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterPegawai.notifyDataSetChanged();
                swipeListPegawai.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListPegawai.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void searchPegawai(String nama) {
        list_pegawai.clear();
        adapterPegawai.notifyDataSetChanged();
        swipeListPegawai.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getSearchPegawai(nama), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListPegawai listData = new ListPegawai(
                                object.getString("IDPegawai_"),
                                object.getString("NamaPegawai_"),
                                object.getString("Nip_"),
                                object.getString("Alamat_"),
                                object.getString("Username_"),
                                object.getString("Password_")
                        );
                        list_pegawai.add(listData);
                    }
                    rvListPegawai.setAdapter(adapterPegawai);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterPegawai.notifyDataSetChanged();
                swipeListPegawai.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListPegawai.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }
}

package ukk.cikal.inventaris.views.admin;

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
import ukk.cikal.inventaris.adapters.AdapterOperator;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.models.ListOperator;

public class ListOperatorActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener{

    @BindView(R.id.svOperator)
    SearchView svOperator;
    @BindView(R.id.rvListOperator)
    RecyclerView rvListOperator;
    @BindView(R.id.swipeListOperator)
    SwipeRefreshLayout swipeListOperator;
    @BindView(R.id.fabAddOperator)
    FloatingActionButton fabAddOperator;

    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    AdapterOperator adapterOperator;
    List<ListOperator> list_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_operator);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        rvListOperator.setLayoutManager(new LinearLayoutManager(this));
        list_operator = new ArrayList<>();
        adapterOperator = new AdapterOperator(list_operator);

        swipeListOperator.setOnRefreshListener(this);
        svOperator.setOnQueryTextListener(this);

        getListOperator();

        svOperator.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                swipeListOperator.setRefreshing(true);
                getListOperator();
                return false;
            }
        });
    }

    @OnClick({R.id.svOperator, R.id.fabAddOperator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.svOperator:
                svOperator.setIconified(false);
                break;
            case R.id.fabAddOperator:
                Intent intent = new Intent(ListOperatorActivity.this, FormOperatorActivity.class);
                startActivity(intent);
//                finish(); //WARNING
                break;
        }
    }

    @Override
    public void onRefresh() {
        list_operator.clear();
        adapterOperator.notifyDataSetChanged();
        getListOperator();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchOperator(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.equals("")) {
            swipeListOperator.setEnabled(true);
            getListOperator();
        } else {
            swipeListOperator.setRefreshing(false);
            swipeListOperator.setEnabled(false);
            searchOperator(query);
            Log.i("TAG", "onQueryTextChange: " + Api.getSearchPegawai(query));
        }
        return false;
    }

    private void getListOperator() {
        Log.i("TAG", "getListOperator: " + Api.getOperator);
        list_operator.clear();
        adapterOperator.notifyDataSetChanged();
        swipeListOperator.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getOperator, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListOperator listData = new ListOperator(
                                object.getString("IDPetugas_"),
                                object.getString("NamaPetugas_"),
                                object.getString("Username_"),
                                object.getString("Password_")
                        );
                        list_operator.add(listData);
                    }
                    rvListOperator.setAdapter(adapterOperator);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterOperator.notifyDataSetChanged();
                swipeListOperator.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListOperator.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void searchOperator(String nama) {
        Log.i("TAG", "searchOperator: " + Api.getSearchOperator(nama));
        list_operator.clear();
        adapterOperator.notifyDataSetChanged();
        swipeListOperator.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getSearchOperator(nama), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListOperator listData = new ListOperator(
                                object.getString("IDPetugas_"),
                                object.getString("NamaPetugas_"),
                                object.getString("Username_"),
                                object.getString("Password_")
                        );
                        list_operator.add(listData);
                    }
                    rvListOperator.setAdapter(adapterOperator);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterOperator.notifyDataSetChanged();
                swipeListOperator.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListOperator.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }
}

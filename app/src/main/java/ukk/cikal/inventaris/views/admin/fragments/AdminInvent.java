package ukk.cikal.inventaris.views.admin.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import butterknife.Unbinder;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.adapters.AdapterInvent;
import ukk.cikal.inventaris.helpers.Api;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.models.ListInvent;
import ukk.cikal.inventaris.views.admin.FormAddInventActivity;

public class AdminInvent extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener {

    @BindView(R.id.svInvent)
    SearchView svInvent;
    @BindView(R.id.rvListInvent)
    RecyclerView rvListInvent;
    @BindView(R.id.swipeListInvent)
    SwipeRefreshLayout swipeListInvent;
    @BindView(R.id.fabAddInvent)
    FloatingActionButton fabAddInvent;
    Unbinder unbinder;

    Context context;
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;

    AdapterInvent adapterInvent;
    List<ListInvent> list_invent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_invent, container, false);
        context = this.getActivity();
        sharedPreferences = context.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(context);
        unbinder = ButterKnife.bind(this, view);

        rvListInvent.setLayoutManager(new LinearLayoutManager(context));
        list_invent = new ArrayList<>();
        adapterInvent = new AdapterInvent(list_invent);
        svInvent.setOnQueryTextListener(this);
        swipeListInvent.setOnRefreshListener(this);

        getListInvent();

        svInvent.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                swipeListInvent.setRefreshing(true);
                getListInvent();
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        getListInvent();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchInvent(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.equals("")){
            swipeListInvent.setEnabled(true);
            getListInvent();
        } else {
            swipeListInvent.setRefreshing(false);
            swipeListInvent.setEnabled(false);
            searchInvent(query);
            Log.i("TAG", "onQueryTextChange: " + Api.getSearchInvent(query));
        }
        return false;
    }

    @OnClick({R.id.svInvent, R.id.fabAddInvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.svInvent:
                svInvent.setIconified(false);
                break;
            case R.id.fabAddInvent:
                Intent intent = new Intent(context, FormAddInventActivity.class);
                startActivity(intent);
//                this.getActivity().finish(); //WARNING
                break;
        }
    }

    private void getListInvent() {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        swipeListInvent.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getInvent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++ ){
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListInvent listData = new ListInvent(
                                object.getString("IDInvent_"),
                                object.getString("NamaInvent_"),
                                object.getString("KondisiInvent_"),
                                object.getString("KetInvent_"),
                                object.getString("Jumlah_"),
                                object.getString("IDJenis_"),
                                object.getString("IDRuang_"),
                                object.getString("TglRegis_"),
                                object.getString("KodeInvent_"),
                                object.getString("NamaJenis_"),
                                object.getString("NamaRuang_")
                        );
                        list_invent.add(listData);
                    }
                    rvListInvent.setAdapter(adapterInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterInvent.notifyDataSetChanged();
                swipeListInvent.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListInvent.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }

    private void searchInvent(String query) {
        list_invent.clear();
        adapterInvent.notifyDataSetChanged();
        swipeListInvent.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.getSearchInvent(query), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i=0; i<jsonArray.length(); i++ ){
                        JSONObject object = jsonArray.getJSONObject(i);
                        ListInvent listData = new ListInvent(
                                object.getString("IDInvent_"),
                                object.getString("NamaInvent_"),
                                object.getString("KondisiInvent_"),
                                object.getString("KetInvent_"),
                                object.getString("Jumlah_"),
                                object.getString("IDJenis_"),
                                object.getString("IDRuang_"),
                                object.getString("TglRegis_"),
                                object.getString("KodeInvent_"),
                                object.getString("NamaJenis_"),
                                object.getString("NamaRuang_")
                        );
                        list_invent.add(listData);
                    }
                    rvListInvent.setAdapter(adapterInvent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterInvent.notifyDataSetChanged();
                swipeListInvent.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeListInvent.setRefreshing(false);
            }
        });
        requestQueue.add(stringRequest);
    }
}

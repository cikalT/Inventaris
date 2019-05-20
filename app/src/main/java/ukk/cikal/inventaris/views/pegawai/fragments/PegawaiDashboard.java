package ukk.cikal.inventaris.views.pegawai.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.MainActivity;
import ukk.cikal.inventaris.views.operator.OperatorActivity;

public class PegawaiDashboard extends Fragment {

    Button btnLogOut;
    Context context;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pegawai_dashboard, container, false);
        btnLogOut = view.findViewById(R.id.btnLogout);
        context = this.getActivity();
        sharedPreferences = context.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogOut();
            }
        });
        return view;
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

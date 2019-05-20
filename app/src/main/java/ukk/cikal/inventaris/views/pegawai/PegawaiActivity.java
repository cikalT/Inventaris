package ukk.cikal.inventaris.views.pegawai;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.views.pegawai.fragments.PegawaiDashboard;
import ukk.cikal.inventaris.views.pegawai.fragments.PegawaiInvent;
import ukk.cikal.inventaris.views.pegawai.fragments.PegawaiStatus;

public class PegawaiActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        loadFragment(new PegawaiInvent());
        BottomNavigationView navigationView = findViewById(R.id.navigationPegawai);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flPegawai, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_invent :
                fragment = new PegawaiInvent();
                break;
            case R.id.navigation_status :
                fragment = new PegawaiStatus();
                break;
            case R.id.navigation_dashboard :
                fragment = new PegawaiDashboard();
                break;
        }
        return loadFragment(fragment);
    }
}

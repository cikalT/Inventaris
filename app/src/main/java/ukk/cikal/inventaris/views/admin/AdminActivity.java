package ukk.cikal.inventaris.views.admin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.views.admin.fragments.AdminDashboard;
import ukk.cikal.inventaris.views.admin.fragments.AdminInvent;
import ukk.cikal.inventaris.views.admin.fragments.AdminProses;

public class AdminActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        loadFragment(new AdminInvent());
        BottomNavigationView navigationView = findViewById(R.id.navigationAdmin);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flAdmin, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_invent :
                fragment = new AdminInvent();
                break;
            case R.id.navigation_proses :
                fragment = new AdminProses();
                break;
            case R.id.navigation_dashboard :
                fragment = new AdminDashboard();
                break;
        }
        return loadFragment(fragment);
    }
}

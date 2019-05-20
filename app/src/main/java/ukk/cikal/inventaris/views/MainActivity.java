package ukk.cikal.inventaris.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ukk.cikal.inventaris.R;
import ukk.cikal.inventaris.helpers.Utils;
import ukk.cikal.inventaris.views.admin.AdminActivity;
import ukk.cikal.inventaris.views.operator.OperatorActivity;
import ukk.cikal.inventaris.views.pegawai.PegawaiActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imgPengelola)
    ImageView imgPengelola;
    @BindView(R.id.imgPegawai)
    ImageView imgPegawai;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        sharedPreferences = this.getSharedPreferences(Utils.SHARED_NAME, Context.MODE_PRIVATE);
        cekLevel();
    }

    @OnClick({R.id.imgPengelola, R.id.imgPegawai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgPengelola:
                Intent intent1 = new Intent(MainActivity.this, LoginPengelolaActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.imgPegawai:
                Intent intent2 = new Intent(MainActivity.this, LoginPegawaiActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

    private void cekLevel() {
        String level = sharedPreferences.getString(Utils.sharedLevel, "");

        assert level != null;
        switch (level) {
            case "1" :
                Intent intent1 = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(intent1);
                finish();
                break;
            case "2" :
                Intent intent2 = new Intent(MainActivity.this, OperatorActivity.class);
                startActivity(intent2);
                finish();
                break;
            case "3" :
                Intent intent3 = new Intent(MainActivity.this, PegawaiActivity.class);
                startActivity(intent3);
                finish();
                break;
        }
    }
}

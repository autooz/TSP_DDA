package tet.oleg_zhabko.tsp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tet.oleg_zhabko.tsp.MainActivity;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.ui.autonom.ChoiceRouteActivityAutonom;
import tet.oleg_zhabko.tsp.ui.autonom.CreateNewRouteActivityAutonom;
import tet.oleg_zhabko.tsp.ui.autonom.Statistics;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class MainActivityAutonom extends Activity implements View.OnClickListener {

private String pseudo_tag = MainActivityAutonom.class.getSimpleName();
    private MainActivityAutonom mContext;
    private Button closApp_button;
    private Button setingsAutonom_button;
    private Button choiseRoot_button;
    private Button stats_button;
    private Button rout_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");
        setContentView(R.layout.activity_main_autonom);


        setingsAutonom_button = (Button) findViewById(R.id.setingsAutonom);
        setingsAutonom_button.setOnClickListener(this);
        choiseRoot_button = (Button) findViewById(R.id.choiceRout);
        choiseRoot_button.setOnClickListener(this);
        stats_button = (Button) findViewById(R.id.statsMAA);
        stats_button.setOnClickListener(this);
        rout_button = (Button) findViewById(R.id.newRouteOwn);
        rout_button.setOnClickListener(this);
        closApp_button = (Button) findViewById(R.id.closeFromAppMAA);
        closApp_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.setingsAutonom) {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
        } else if (id == R.id.choiceRout) {
            intent = new Intent(getApplicationContext(), ChoiceRouteActivityAutonom.class);
        } else if (id == R.id.statsMAA) {
            intent = new Intent(getApplicationContext(), Statistics.class);
        } else if (id == R.id.newRouteOwn) {
            intent = new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class);
        } else if (id == R.id.closeFromAppMAA) {//               intent = new Intent(getApplicationContext(), MainActivityDataPickerView.class);
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        startActivity(intent);
        this.finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }
}
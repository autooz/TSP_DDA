package tet.oleg_zhabko.tsp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.datas.databaseCreaterSQL;
import tet.oleg_zhabko.tsp.ui.MainActivityAutonom;
import tet.oleg_zhabko.tsp.ui.MainActivityWiaServer;
import tet.oleg_zhabko.tsp.ui.SettingsActivity;
import tet.oleg_zhabko.tsp.ui.utils.appAndMaps.workWithApkNaviOnDevice;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.ActivityOsmOnLineAddPoint;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class MainActivity extends Activity implements View.OnClickListener {

    String pseudo_tag = MainActivity.class.getSimpleName();
    private Button autonom_button;
    private Button wiaserrver_button;
    private Button closApp_button;
    private Button debug_button;
    private AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));

        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");


        setContentView(R.layout.activity_main);


        autonom_button = (Button) findViewById(R.id.autonomiusly);
        autonom_button.setOnClickListener(this);
        wiaserrver_button = (Button) findViewById(R.id.wiaserver);
        wiaserrver_button.setOnClickListener(this);
        closApp_button = (Button) findViewById(R.id.closeApp);
        closApp_button.setOnClickListener(this);
        debug_button = (Button) findViewById(R.id.debugApp);
        debug_button.setOnClickListener(this);


        /*Chech Db create/ If No Db creat it and advise */

        if (!allDbController.isDbCreated(this, GlobalDatas.db_name)) {
            TetDebugUtil.e(pseudo_tag, "This is First Start");

            if (!createDB(GlobalDatas.db_name)) {
                TetDebugUtil.e(pseudo_tag, "DB not created");
            } else {
                TetDebugUtil.e(pseudo_tag, "DB created OK!");
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }
        } else {
            ThisApp.getInstance().adjastFontScale();
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();

        if (id == R.id.autonomiusly) {
            intent = new Intent(getApplicationContext(), MainActivityAutonom.class);
        } else if (id == R.id.wiaserver) {
            intent = new Intent(getApplicationContext(), MainActivityWiaServer.class);
        } else if (id == R.id.closeApp) {
            //  intent = new Intent(getApplicationContext(), MainActivityDataPickerView.class);
            finish();
        } else if (id == R.id.debugApp){
           intent = new Intent(getApplicationContext(), SettingsActivity.class);
        }
        startActivity(intent);
        //this.finish();
    }


    private boolean createDB(String db_name) {
        boolean res = false;
        TetDebugUtil.e(pseudo_tag, "Make createDB");
        // allDbController.createNewDb(this,db_name);
        new databaseCreaterSQL().insertDatasToDb(db_name);
        String stringQuery = new databaseCreaterSQL().insertDatasToDb(db_name);

        if (allDbController.isDbCreated(this, db_name)) {
 res = true;
        }
        if (!allDbController.isDbCreated(this, db_name)) {
            TetDebugUtil.e(pseudo_tag, "ERROR1 FAIL in private boolean createDB");
            res = false;
        }
        return res;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
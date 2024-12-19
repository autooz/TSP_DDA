package tet.oleg_zhabko.tsp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.datas.addDemoDatas;
import tet.oleg_zhabko.tsp.datas.databaseCreaterSQL;
import tet.oleg_zhabko.tsp.ui.MainActivityAutonom;
import tet.oleg_zhabko.tsp.ui.MainActivityWiaServer;
import tet.oleg_zhabko.tsp.ui.SettingsActivity;
import tet.oleg_zhabko.tsp.ui.utils.appAndNaviMaps.workWithApkNaviOnDevice;
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
    private int colorRed;
    private int colorGreen;
    private int flow;


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
       // debug_button.setVisibility(View.GONE);
        debug_button.setOnClickListener(this);

        Resources resource = getResources();
        colorRed = resource.getColor(R.color.tetAccent);
        colorGreen = resource.getColor(R.color.tetGreen);
        flow = resource.getColor(R.color.tetElow);
        if  (GlobalDatas.db_name.equals("routing.db")) {
            autonom_button.setBackgroundColor(colorGreen);
            debug_button.setBackgroundColor(colorRed);
        } else if (GlobalDatas.db_name.equals("demo.db")){
            autonom_button.setBackgroundColor(flow);
            debug_button.setBackgroundColor(colorGreen);
        }
        /*Chech Db create/ If No Db creat it and advise */
        checkOrCreateDb();

        setGlobalDatas_navigationAPP();

    }

    private void checkOrCreateDb() {
        if (!allDbController.isDbCreated(this, GlobalDatas.db_name)) {
            TetDebugUtil.e(pseudo_tag, "This is First Start");

            if (!createDB(GlobalDatas.db_name)) {
                TetDebugUtil.e(pseudo_tag, "DB not created");
            } else {
                TetDebugUtil.e(pseudo_tag, "DB created OK!");
                if (GlobalDatas.db_name.equals("demo.db")){
                    new addDemoDatas().insertDatasToDb(GlobalDatas.db_name);

                }
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
//            autonom_button.setBackgroundColor();
//            debug_button.setBackgroundColor();
            GlobalDatas.db_name = "routing.db";
        } else if (id == R.id.wiaserver) {
            intent = new Intent(getApplicationContext(), MainActivityWiaServer.class);
        } else if (id == R.id.closeApp) {
            //  intent = new Intent(getApplicationContext(), MainActivityDataPickerView.class);
            finish();
        } else if (id == R.id.debugApp){
            if (GlobalDatas.db_name.equals("demo.db")){
                startActivity(new Intent(getApplicationContext(), MainActivityAutonom.class));
            } else {
                GlobalDatas.db_name = "demo.db";
                checkOrCreateDb();
//                autonom_button.setBackgroundColor();
//                debug_button.setBackgroundColor();
            }
        }
        if (intent != null) {
            startActivity(intent);
        }
        //this.finish();
    }

    private void setGlobalDatas_navigationAPP() {
        if (GlobalDatas.appsSupportedList.isEmpty()){
            new workWithApkNaviOnDevice(this);
        }
        ArrayList<ArrayList<String>> arar = allDbController.executeQuery(getApplicationContext(), GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='nav_map'");
        Intent intent = null;
        if (!arar.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, "ERROR arar.isEmpty()");

        ArrayList<String> ar = arar.get(0);
        if (ar.isEmpty()) {
            TetDebugUtil.e(pseudo_tag, "ERROR ar.isEmpty()");
        }
        String appPackageName = ar.get(0);
        if (appPackageName == null || appPackageName.isEmpty() || appPackageName.equals(null)){
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        }
        GlobalDatas.navigationAPP = appPackageName;
        }
    }


    private boolean createDB(String db_name) {
        boolean res = false;
        TetDebugUtil.e(pseudo_tag, "Make createDB");
         allDbController.createNewDb(this,db_name);
        new databaseCreaterSQL().insertDatasToDb(db_name);


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
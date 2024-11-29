package tet.oleg_zhabko.tsp.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.mapsforge.core.graphics.Color;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.MainActivity;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.ActivityGoogleMapWebView;
import tet.oleg_zhabko.tsp.ui.utils.points_and_maps.ActivityOsmOnline;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.oleg_zhabko.tsp.ui.utils.SettingsUtils;
import tet.oleg_zhabko.tsp.ui.utils.appAndMaps.workWithApkNaviOnDevice;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class SettingsActivity extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private String pseudo_tag = SettingsActivity.class.getSimpleName();
    Context context = this;
    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    private SettingsUtils settingsUtils = new SettingsUtils();

    private LinearLayout lay;
    private Button butonSavePWD;
    private Button buttonSaveAll;
    private EditText skaleEdit;
    private RadioButton rButtonOsmOfline;
    private RadioButton rButtonOsmOnLine;
    private RadioButton rButtonWaze;
    private RadioButton rButtonGoogleOffLine;
    private RadioButton rButtonGoogleNavi;
    private RadioButton rButtonGoogleWebView;
    private RadioGroup rGroupe;
    private RadioGroup rGroupeNavi;
    private workWithApkNaviOnDevice naviAppManipulation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_settings);

        naviAppManipulation =new workWithApkNaviOnDevice(this);

        // Check is nav_map variable added to setting in DB
        if (allDbController.executeQuery(context,GlobalDatas.db_name,"SELECT * FROM settings WHERE `variable`='nav_map';").isEmpty()){
            allDbController.executeQuery(context,GlobalDatas.db_name,"INSERT INTO settings (variable) VALUES ('nav_map');");
        }


        CheckBox chBox = (CheckBox) findViewById(R.id.checkBoxHidelay);
        String res = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='passNeed'").get(0).get(0);
        lay = (LinearLayout) findViewById(R.id.hidenPaswdForm);
        rGroupe = (RadioGroup) findViewById(R.id.rGroupeMaps);
        rGroupeNavi = (RadioGroup) findViewById(R.id.rGroupeNawigation);

        rButtonOsmOfline = (RadioButton) findViewById(R.id.rbOsmOffLine);
        rButtonOsmOnLine = (RadioButton) findViewById(R.id.rbOsmOnLine);
        rButtonWaze = (RadioButton) findViewById(R.id.rbWaze);
        rButtonGoogleOffLine = (RadioButton) findViewById(R.id.rbOsmOffLine);
        rButtonGoogleNavi = (RadioButton) findViewById(R.id.rbGoogleNavi);
        rButtonGoogleWebView = (RadioButton) findViewById(R.id.rbGoogleWebView);

        radiobuttonProcessing();

        rGroupe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TetDebugUtil.e(pseudo_tag, "  int checkedId=" + checkedId + "");
                String mapAktiyity = new String();
                TetDebugUtil.e(pseudo_tag, "Doing radioButtonClickListener ");
                if (checkedId == R.id.rbOsmOffLine) {

                } else if (checkedId == R.id.rbOsmOnLine) {
                    TetDebugUtil.e(pseudo_tag, "Doing R.id.rbOsmOnLine ");
                    mapAktiyity = ActivityOsmOnline.class.getSimpleName();
                } else if (checkedId == R.id.rbGoogleOffLine) {

                } else if (checkedId == R.id.rbGoogleMaps){

                }else if (checkedId == R.id.rbGoogleWebView) {
                    TetDebugUtil.e(pseudo_tag, "Doing R.id.rbGoogleWebView ");
                    mapAktiyity = ActivityGoogleMapWebView.class.getSimpleName();
                }

                if (!mapAktiyity.isEmpty()) {
                    TetDebugUtil.e(pseudo_tag, "ADD to db mapAktiyity=" + mapAktiyity + " ");
                    allDbController.executeQuery(context, GlobalDatas.db_name, "UPDATE settings SET value='" + mapAktiyity + "' WHERE variable ='" + GlobalDatas.setingMapsActivity + "';");
                }

            }


        });
        rGroupeNavi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String appPackageName = new String();
                 appPackageName = naviAppManipulation.getPaketNameByRadioButtonID(checkedId);
                 TetDebugUtil.e(pseudo_tag, "onCheckedChanged appPackageName=["+appPackageName+"]" );
                 if (!naviAppManipulation.isAppInstalled(appPackageName)){
                     makeDialog(appPackageName);
                    // return;
                 }

                if (!appPackageName.isEmpty()){
                        TetDebugUtil.e(pseudo_tag, "ADD to db nav_map=" + appPackageName + " ");
                        allDbController.executeQuery(context, GlobalDatas.db_name, "UPDATE settings SET value='" + appPackageName + "' WHERE variable ='nav_map';");
                        GlobalDatas.navigationAPP = appPackageName;
                }
            }
        });

        TetDebugUtil.e(pseudo_tag, "Res =" + res + " ");

        if (res.equals("true")) {
            TetDebugUtil.d(pseudo_tag, "res.equals(true) ");
            chBox.setChecked(true);
        } else {
            TetDebugUtil.d(pseudo_tag, "res.equals(false) ");
            lay.setVisibility(View.GONE);
            chBox.setChecked(false);
        }
        TetDebugUtil.e(pseudo_tag, " chBox.isActivated() = " + chBox.isActivated() + "");
        if (!chBox.isChecked()) {
            lay.setVisibility(View.GONE);
            if (res.equals("true")) {
                allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value='true' WHERE variable='passNeed'");
                lay.setVisibility(View.VISIBLE);
                chBox.setChecked(true);

            }
        }
        chBox.setOnCheckedChangeListener(this);
        butonSavePWD = (Button) findViewById(R.id.butSavePWD);
        butonSavePWD.setOnClickListener(this);
        skaleEdit = (EditText) findViewById(R.id.scaleEsit);

        ArrayList<ArrayList<String>> skaleArr = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable ='scaleText'");
        if (!skaleArr.isEmpty()) {
            ArrayList<String> a = skaleArr.get(0);
            if (!a.isEmpty()) {
                String scale = a.get(0);
                skaleEdit.setText(scale);
            }
        }
        buttonSaveAll = (Button) findViewById(R.id.butSaveSettings);
        buttonSaveAll.setOnClickListener(this);

    }


    private void radiobuttonProcessing() {

        String activityMaps = settingsUtils.getSettingValue(GlobalDatas.db_name, GlobalDatas.setingMapsActivity);

        TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing() activityMaps =" + activityMaps + "");

        if (activityMaps != null) {
            //        if (activityMaps.equals()) {
//            rButtonOsmOfline.setChecked(true);
//        } else
            if (activityMaps.equals(ActivityOsmOnline.class.getSimpleName())) {
                TetDebugUtil.e(pseudo_tag, "activityMaps =" + activityMaps + " ActivityOSMonline.class.getSimpleName()=" + ActivityOsmOnline.class.getSimpleName() + "");
                rButtonOsmOnLine.setChecked(true);
            }
            if (activityMaps.equals(ActivityGoogleMapWebView.class.getSimpleName())) {
                rButtonGoogleWebView.setChecked(true);
            }


        }

        String appPackageName = new String();

        if(GlobalDatas.navigationAPP.isEmpty()){
            // Looking for it in the database
            TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing GlobalDatas.navigationAPP.isEmpty()");

            ArrayList<ArrayList<String>> arar = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='nav_map';");
            if (!arar.isEmpty()){
                TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing {!arar.isEmpty()}");
                ArrayList<String> ar = arar.get(0);
                if(!ar.isEmpty()) {
                    TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing {!ar.isEmpty()}");
                    appPackageName = ar.get(0);
                    if (appPackageName == null || appPackageName.equals("null")) {
                        TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing {rbutton == null}");
                        RadioButton rb = (RadioButton) findViewById(R.id.rbGoogleNavi);
                        rb.setChecked(true);
                        appPackageName = "com.google.android.apps.maps";
                        TetDebugUtil.e(pseudo_tag,"Set asDefault appPackageName = ["+appPackageName+"]");
                        if (naviAppManipulation.isAppInstalled(GlobalDatas.navigationAPP)){
                            if (naviAppManipulation.isAppInstalled(appPackageName)) {
                                TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing ADD to db vav_map=" + appPackageName + " ");
                                allDbController.executeQuery(context, GlobalDatas.db_name, "UPDATE settings SET value='" + appPackageName + "' WHERE variable ='nav_map';");
                                GlobalDatas.navigationAPP = appPackageName;
                            } else {
                                TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing GlobalDatas.navigationAPP.isEmpty()");
                                //rGroupeNaviSetting (appPackageName);
                            }

                        }
                    } else {
                        TetDebugUtil.e(pseudo_tag, "radiobuttonProcessing {appPackageName not null} and appPackageName = ["+appPackageName+"]");
                        if (!naviAppManipulation.isAppInstalled(appPackageName)){
                            int radiobuttonID = naviAppManipulation.getRadioButtonIDByPakageName(appPackageName);
                            RadioButton rb = (RadioButton) findViewById(radiobuttonID);
                            rb.setChecked(true);
                            //rb.setTextColor(getResources().getColor(R.color.colorAccent));
                            makeDialog(appPackageName);
                        } else {
                            int radiobuttonID = naviAppManipulation.getRadioButtonIDByPakageName(appPackageName);
                            RadioButton rb = (RadioButton) findViewById(radiobuttonID);
                            rb.setChecked(true);
                            //rb.setTextColor(getResources().getColor(R.color.tetGreen));
                        }
                    }
                }
            } else {
                TetDebugUtil.e(pseudo_tag, "ELSE {arar.isEmpty()}");
                appPackageName = GlobalDatas.navigationAPP;
                rGroupeNaviSetting (appPackageName);
            }
        } else {
            TetDebugUtil.e(pseudo_tag, "ELSE {GlobalDatas.navigationAPP.isEmpty()}");
            if (naviAppManipulation.isAppInstalled(GlobalDatas.navigationAPP)){
            int rbId = naviAppManipulation.getRadioButtonIDByPakageName(GlobalDatas.navigationAPP);
                RadioButton rb = (RadioButton) findViewById(rbId);
                rb.setChecked(true);
            }
        }

    }

    private void rGroupeNaviSetting(String appPackageName) {

        TetDebugUtil.e(pseudo_tag,"Pakage name = ["+appPackageName+"] ");
        if (!naviAppManipulation.isAppInstalled(appPackageName)){
            TetDebugUtil.e(pseudo_tag, "rGroupeNaviSetting make makeDialog(appPackageName)");
makeDialog(appPackageName);
        }
        TetDebugUtil.e(pseudo_tag,"rGroupeNaviSetting appPackageName = ["+appPackageName+"] is installed ");
    }

    private void makeDialog(String appPackageName) {
        TetDebugUtil.e(pseudo_tag, "Run makeDialog appPackageName=["+appPackageName+"]");
        String packageDescription = naviAppManipulation.getPaketDecripByPackageName(appPackageName);
        String title = getResources().getString(R.string.titleInstallApp);
        String content = getResources().getString(R.string.txtInstallAppQuest)+" "+packageDescription+"?";
        Intent positive = naviAppManipulation.openAppInPlayStore(appPackageName);
        Intent negativ = null;
        new AllertOneAndTwoAndThreeButton().createThreeButtonAlertDialog(SettingsActivity.this,title, content,positive, negativ,  false, false).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            lay.setVisibility(View.VISIBLE);
            allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value='true' WHERE variable='passNeed'");
        } else {
            lay.setVisibility(View.GONE);
            allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value='false' WHERE variable='passNeed'");
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.butSavePWD) {
            EditText login = (EditText) findViewById(R.id.editCallsign);
            EditText passwd = (EditText) findViewById(R.id.editPassword);
            String value = login.getText().toString();
            String description = passwd.getText().toString();
            allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value=" + value + " description=" + description + " WHERE variable='passwd'");
            allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value='true' WHERE variable='passNeed'");
            AllertOneAndTwoAndThreeButton dialy = new AllertOneAndTwoAndThreeButton();
            String title = getResources().getString(R.string.txtGoodResult);
            String restrLIst = getResources().getString(R.string.txtLoginPasswdSaved);
            ;
            dialy.createOneButtonsAlertDialog(this, title, restrLIst, null).show();

        } else if (id == R.id.butSaveSettings) {
            String sk = skaleEdit.getText().toString();
            allDbController.executeQuery(this, GlobalDatas.db_name, "UPDATE settings SET value=" + sk + " WHERE variable='scaleText'");
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            this.finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Логика для скрытия FAB при активации другого приложения
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Логика для показа FAB при активации другого приложения
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }

}
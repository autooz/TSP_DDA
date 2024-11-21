package tet.oleg_zhabko.tsp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.MainActivity;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.points_and_maps.ActivityGoogleMapWebView;
import tet.oleg_zhabko.tsp.ui.points_and_maps.ActivityOsmOnline;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.oleg_zhabko.tsp.ui.utils.SettingsUtils;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_settings);


        CheckBox chBox = (CheckBox) findViewById(R.id.checkBoxHidelay);
        String res = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT value FROM settings WHERE variable='passNeed'").get(0).get(0);
        lay = (LinearLayout) findViewById(R.id.hidenPaswdForm);
        rGroupe = (RadioGroup) findViewById(R.id.rGrooupeMaps);

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
                } else if (checkedId == R.id.rbWaze) {

                } else if (checkedId == R.id.rbOsmOffLine) {

                } else if (checkedId == R.id.rbGoogleNavi) {

                } else if (checkedId == R.id.rbGoogleWebView) {
                    TetDebugUtil.e(pseudo_tag, "Doing R.id.rbGoogleWebView ");
                    mapAktiyity = ActivityGoogleMapWebView.class.getSimpleName();
                }

                if (!mapAktiyity.isEmpty()) {
                    TetDebugUtil.e(pseudo_tag, "ADD to db mapAktiyity=" + mapAktiyity + " ");
                    allDbController.executeQuery(context, GlobalDatas.db_name, "UPDATE settings SET value='" + mapAktiyity + "' WHERE variable ='" + GlobalDatas.seingMapsActivity + "';");
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

        String activityMaps = settingsUtils.getSettingValue(GlobalDatas.db_name, GlobalDatas.seingMapsActivity);

        TetDebugUtil.e(pseudo_tag, "activityMaps =" + activityMaps + "");

        if (activityMaps != null) {
            //        if (activityMaps.equals()) {
//            rButtonOsmOfline.setChecked(true);
//        } else
            if (activityMaps.equals(ActivityOsmOnline.class.getSimpleName())) {
                TetDebugUtil.e(pseudo_tag, "activityMaps =" + activityMaps + " ActivityOSMonline.class.getSimpleName()=" + ActivityOsmOnline.class.getSimpleName() + "");
                rButtonOsmOnLine.setChecked(true);
            }
//    else if(activityMaps.equals()){
//    rButtonWaze.setChecked(true);
//}
//       else if(activityMaps.equals()){
//            rButtonGoogleOffLine.setChecked(true);
//        }
//       else if(activityMaps.equals()) {
//            rButtonGoogleNavi.setChecked(true);
//        }
            if (activityMaps.equals(ActivityGoogleMapWebView.class.getSimpleName())) {
                rButtonGoogleWebView.setChecked(true);
            }

        }

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }

}
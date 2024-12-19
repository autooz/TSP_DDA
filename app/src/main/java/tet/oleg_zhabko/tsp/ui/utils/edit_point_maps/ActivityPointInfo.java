package tet.oleg_zhabko.tsp.ui.utils.edit_point_maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointOwnPoint;
import tet.oleg_zhabko.tsp.ui.route.OnRouteMainActivity;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ActivityPointInfo extends Activity {
    private String pseudo_tag = ActivityPointInfo.class.getSimpleName();
    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    private ArrayList<String> datasOfPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();

        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_point_info);
        Intent intent = getIntent();
        String point_id = intent.getStringExtra("point_id");

        ArrayList<ArrayList<String>> arar = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT * FROM owner_points WHERE point_id='" + point_id + "'");

       // ArrayList<ArrayList<String>> arar = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT * FROM owner_points WHERE point_id='115'");
        if (arar.isEmpty()) {
            String title = getResources().getString(R.string.worning);
            String msg = getResources().getString(R.string.msgNoPoints);
            Intent intent_back = new Intent();
            new AllertOneAndTwoAndThreeButton().createOneButtonsAlertDialog(this, title, msg, intent_back).show();
        }
        datasOfPoint = arar.get(0);
        // Set OrgName
        TextView txtNewPointTitle = (TextView) findViewById(R.id.txtInfoPointTitle);
        String title = getResources().getString(R.string.txtTitleNewPoint) + ": " + GlobalDatas.getOrgName();
        txtNewPointTitle.setText(title);
        // set Zone
        TextView zoneName = (TextView) findViewById(R.id.infoActiveZone);
        zoneName.setText(datasOfPoint.get(2).toString());
        // Set salesman
        TextView salemanName = (TextView) findViewById(R.id.tvInfoActivSalePoint);
        salemanName.setText(datasOfPoint.get(4).toString());
        // Set owner of point
        TextView owner = (TextView) findViewById(R.id.infoOwnerName);
        owner.setText(datasOfPoint.get(6));
        //Set landMark / pointName
        TextView landMark = (TextView) findViewById(R.id.infoLendMark);
        landMark.setText(datasOfPoint.get(9));
        // Set ambar/ramp
        TextView ramp = (TextView) findViewById(R.id.infoAmbar);
        ramp.setText(datasOfPoint.get(10));
        // Set AmbarPhone phone
        TextView ambarPhone = (TextView) findViewById(R.id.infoAmbarPhone);
        ambarPhone.setText(datasOfPoint.get(11));
        // Set office
        TextView office = (TextView) findViewById(R.id.infoOffice);
        office.setText(datasOfPoint.get(12));
        // Set office phone
//        TextView officePhone = (TextView) findViewById(R.id.infoOfficePhone);
//        officePhone.setText(datasOfPoint.get(13));
        // Set working time
        TextView startWorHouer = (TextView) findViewById(R.id.infoStartWorHouer);
        startWorHouer.setText(datasOfPoint.get(13));
        TextView startWorMinutes = (TextView) findViewById(R.id.infoStartWorMinutes);
        startWorMinutes.setText(datasOfPoint.get(14));
        TextView finWorkHouer = (TextView) findViewById(R.id.infoFinWorkHouer);
        finWorkHouer.setText(datasOfPoint.get(15));
        TextView finWorkMinutes = (TextView) findViewById(R.id.infoFinWorkMinutes);
        finWorkMinutes.setText(datasOfPoint.get(16));
        // Set Strike time
        TextView startStrikeHouer = (TextView) findViewById(R.id.infoNewStartStrikeHouer);
        startStrikeHouer.setText(datasOfPoint.get(17));
        TextView startSyrikeMinutes = (TextView) findViewById(R.id.infoStartSyrikeMinutes);
        startSyrikeMinutes.setText(datasOfPoint.get(18));
        TextView finStrikeHouer = (TextView) findViewById(R.id.infoFinStrikeHouer);
        finStrikeHouer.setText(datasOfPoint.get(19));
        TextView finStrileMinutes = (TextView) findViewById(R.id.infoFinStrileMinutes);
        finStrileMinutes.setText(datasOfPoint.get(20));

        Button editPoint = (Button) findViewById(R.id.btnCorrection);
        editPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button closeButton = (Button) findViewById(R.id.btnClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
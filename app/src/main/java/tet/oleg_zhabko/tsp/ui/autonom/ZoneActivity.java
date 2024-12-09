package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.ActivityOsmOnLineAddPoint;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterCustomForPointsList;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.SpinerDialog;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.ModelSpinnerDialog;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ZoneActivity extends Activity implements View.OnClickListener{

    Context mContext = this;
    private final String pseudo_tag = ZoneActivity.class.getSimpleName();
    private final AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    TextView selectedItems;
    ListView listViewZones;
    ArrayList<String> itemsZone = new ArrayList<>();
    ModelSpinnerDialog modelSpinnerDialogZone;
    TextView vActivZone;
    AdapterCustomForPointsList adapter;
    private LinearLayout layBtnZone;
    private Button buttonEditZone;
    private Button buttonCreateZone;
    private Button buttonDeleteZone;
    private Button buttonShowZoneList;
    private Button buttonAddPoints;
    private ArrayList<ArrayList<String>> activeArArZone;
    private ArrayList<ArrayList<String>> passiveArArZone;
    private String zoneNameForGlobalData;
    private String zoneIdForGlodalData;
    private Button buttonSaveByZone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_zone);

        listViewZones = (ListView) findViewById(R.id.listViewZone);

        vActivZone = (TextView) findViewById(R.id.tvActivZone); /* Text organisation name */
        vActivZone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<ArrayList<String>> dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points");
                adapter = null;
                String name = s.toString();
                dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE zone='"+name+"' AND organisation_name='"+GlobalDatas.getOrgName()+"'");
                makePointAdapter(dataList, name);
                listViewZones.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        layBtnZone = (LinearLayout) findViewById(R.id.layOfButtonZone); /* Button's View collection */


        /* BUTTONS */
        buttonEditZone = (Button) findViewById(R.id.btnEditZone);
        buttonEditZone.setOnClickListener(this);
        buttonCreateZone = (Button) findViewById(R.id.btnCreateZone);
        buttonCreateZone.setOnClickListener(this);
        buttonDeleteZone = (Button) findViewById(R.id.btnDeleteZone);
        buttonDeleteZone.setOnClickListener(this);
        buttonShowZoneList = (Button) findViewById(R.id.btnShowZoneList);
        buttonShowZoneList.setOnClickListener(this);
        buttonAddPoints = (Button) findViewById(R.id.btnZoneAddPoints);
        buttonAddPoints.setOnClickListener(this);
        buttonSaveByZone = (Button) findViewById(R.id.button_saveByZone);
        buttonSaveByZone.setOnClickListener(this);
        prepareWindowOrganisation();
    }

    private void prepareWindowOrganisation() {
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        ArrayList<ArrayList<String>> activeArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name, org_id FROM organisations WHERE is_active='true'");
        ArrayList<ArrayList<String>> passiveArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name FROM organisations WHERE is_active='false'");
        TetDebugUtil.e(pseudo_tag,"activeArArOrg.isEmpty = "+ activeArArOrg.isEmpty()+" passiveArArOrg.isEmpty="+ passiveArArOrg.isEmpty()+"");

        if (activeArArOrg.isEmpty() && !passiveArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" Do Start AllertOneAndTwoAndThreeButton");
            String title = getResources().getString(R.string.worning).toString();
            String mesage =  getResources().getString(R.string.txtVhoiseOrganisatoin).toString();
            Intent intent = new Intent(getApplicationContext(),CreateNewRouteActivityAutonom.class);
            new AllertOneAndTwoAndThreeButton().createOneButtonsAlertDialog(this,title, mesage ,intent).show();
            return;

        }

        activeArArZone = new ArrayList<>(); // allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT zone_name, zone_id FROM zones WHERE org_id="+GlobalDatas.orgId+"");
        passiveArArZone = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT zone_name FROM zones WHERE org_id="+GlobalDatas.orgId+"");
        TetDebugUtil.e(pseudo_tag,"activeArArZone.isEmpty = "+ activeArArZone.isEmpty()+"  passiveArArZone.isEmpty="+ passiveArArZone.isEmpty()+"");

        if (passiveArArZone.isEmpty()){
            TetDebugUtil.e(pseudo_tag, " passiveArArZone.isEmpty() Hide ");
            buttonShowZoneList.setVisibility(View.GONE);
        }

//        if (!activeArArZone.isEmpty()){
//            TetDebugUtil.e(pseudo_tag, "!activeArArZone.isEmpty()");
//            vActivZone.setVisibility(View.VISIBLE);
//            ArrayList<String> orgAr = activeArArZone.get(0);
//            if(!orgAr.isEmpty()){
//                zoneNameForGlobalData = orgAr.get(0);
//                zoneIdForGlodalData = orgAr.get(1);
//                TetDebugUtil.e(pseudo_tag,"zoneNameForGlobalData="+zoneNameForGlobalData+" zoneIdForGlodalData="+zoneIdForGlodalData+"");
//                vActivZone.setText(zoneNameForGlobalData);
//                GlobalDatas.zoneName = zoneNameForGlobalData;
//                GlobalDatas.zoneId = zoneIdForGlodalData;
//            }
//            makeGlobalDataForZoneman();
//        }


        if (activeArArZone.isEmpty() && passiveArArZone.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"if 0 (activeArArZone.isEmpty() && passiveArArZone.isEmpty())");
            startActivity(new Intent(getApplicationContext(),AddNewZone.class));
            this.finish();
        }
//        if (!activeArArZone.isEmpty() && passiveArArZone.isEmpty()){
//            TetDebugUtil.e(pseudo_tag," if 1 !activeArArZone.isEmpty() && passiveArArZone.isEmpty() GlobalDatas.db_name="+GlobalDatas.db_name+"");
//            buttonShowZoneList.setVisibility(View.GONE);
//            vActivZone.setVisibility(View.VISIBLE);
//            ArrayList<String> orgAr = activeArArZone.get(0);
//            if(!orgAr.isEmpty()){
//                zoneNameForGlobalData = orgAr.get(0);
//                zoneIdForGlodalData = orgAr.get(1);
//                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" zoneNameForGlobalData = "+ zoneNameForGlobalData +"");
//                vActivZone.setText(zoneNameForGlobalData);
//                GlobalDatas.organisation = zoneNameForGlobalData;
//                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.organisation+"");
//            }
//        }
//        if (!activeArArZone.isEmpty() && !passiveArArZone.isEmpty()) {
//            buttonShowZoneList.setVisibility(View.VISIBLE);
////            int size = passiveArArZone.size();
////            for (int i = 0; i < size; i++){
////                ArrayList<String> passiveAr = passiveArArZone.get(i);
////                if (!passiveAr.isEmpty()) {
////                    TetDebugUtil.e(pseudo_tag, "ADDing to items list");
////                    makeDialogForZoneMam(passiveAr);
//                    spinnerDialogZone = MakeAndGetSpinerDialogReturn.makeDialogAndReturnIt(selectedItems, vActivZone, this, passiveArArZone);
////                }
////            }
//        }

        if (activeArArZone.isEmpty() && !passiveArArZone.isEmpty()) {
            TetDebugUtil.e(pseudo_tag,"if 2 (activeArArZone.isEmpty() && !passiveArArZone.isEmpty()");
            layBtnZone.setVisibility(View.VISIBLE);
            ArrayList<String> passiveAr = passiveArArZone.get(0);
            if (!passiveAr.isEmpty()){
                TetDebugUtil.e(pseudo_tag, "ADDing to items list");
                modelSpinnerDialogZone = SpinerDialog.makeDialogAndReturnIt(selectedItems, vActivZone, this, passiveArArZone);
            }
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+": spinnerDialog.showSpinerDialog();");
            modelSpinnerDialogZone.showSpinerDialog(pseudo_tag);
        }
    }

    private void makePointAdapter(ArrayList<ArrayList<String>> dataList, String name) {
        if (dataList.isEmpty()) {
            dataList = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE zone='" + name + "' AND organisation_name='" + GlobalDatas.getOrgName() + "'");
        }
        adapter = null;
        adapter = new AdapterCustomForPointsList(this, dataList);
        listViewZones.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnShowZoneList){
            modelSpinnerDialogZone.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnCreateZone){
            TetDebugUtil.d(pseudo_tag, "PUSHED Button btnCreateOrg");
            startActivity(new Intent(this.getApplicationContext(),AddNewZone.class));
            this.finish();
        } else if (id == R.id.btnEditZone){
            startActivity(new Intent(getApplicationContext(),EditZone.class));
            this.finish();
        } else if (id == R.id.button_saveByZone) {

            onBackPressed();
        } else if (id == R.id.btnZoneAddPoints) {
            TetDebugUtil.e(pseudo_tag," Click btnSaleAddPoints");
            String title = getResources().getString(R.string.txtAdditingSalePoint);
            String content = getResources().getString(R.string.txtEdditionPointAdd);
            Intent positive = new Intent(getApplicationContext(), ActivityOsmOnLineAddPoint.class);
            positive.putExtra("who", pseudo_tag);
            positive.putExtra("org",GlobalDatas.getOrgName());
            positive.putExtra("saleman", GlobalDatas.saleManName);
            positive.putExtra("zone", GlobalDatas.zoneId);
            Intent negative = new Intent();
            AlertDialog di = new AllertOneAndTwoAndThreeButton().createAllertDialogSpetialForTCP(this, title, content, positive, negative, false, false);
            di.show();//(this, String title, String content, final Intent positiveIntent, final Intent negativeveIntent, final boolean isPositiveActivityForResult, final boolean hideCloseButton)
        }
    }

//    private void makeGlobalDataForZoneman() {
//        activeArArZone = new ArrayList<>() ; // allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT zone_name, zone_id FROM zones WHERE org_id="+GlobalDatas.orgId+" ");
//        passiveArArZone = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT zone_name,zones_id FROM zones WHERE org_id="+GlobalDatas.orgId+"");
//        if (passiveArArZone.isEmpty()){
//            TetDebugUtil.e(pseudo_tag, " passiveArArZone.isEmpty() Hide ");
//            buttonShowZoneList.setVisibility(View.GONE);
////            if (!activeArArZone.isEmpty()){
////                TetDebugUtil.e(pseudo_tag, "!activeArArZone.isEmpty()");
////                vActivZone.setVisibility(View.VISIBLE);
////                ArrayList<String> zoneAr = activeArArZone.get(0);
////                if(!zoneAr.isEmpty()){
////                    zoneNameForGlobalData = zoneAr.get(0);
////                    zoneIdForGlodalData = zoneAr.get(1);
////                    vActivZone.setText(zoneNameForGlobalData);
////                    GlobalDatas.zoneName = zoneNameForGlobalData;
////                    GlobalDatas.zoneId = zoneIdForGlodalData;
////                }
////
////            }
//        }
//    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
        this.finish();
    }



}

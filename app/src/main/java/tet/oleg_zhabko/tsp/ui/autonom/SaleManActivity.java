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
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.ModelSpinnerDialog;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.SpinerDialog;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class SaleManActivity extends Activity implements View.OnClickListener {

    Context mContext = this;
    private final String pseudo_tag = SaleManActivity.class.getSimpleName();
    private final AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    TextView tvTextOrgName;
    ListView listViewSaleMan;
    ArrayList<String> itemsSale = new ArrayList<>();
    ModelSpinnerDialog modelSpinnerDialogSale;
    TextView vActivSale;
    AdapterCustomForPointsList adapter;
    private LinearLayout layBtnSale;
    private Button buttonEditSale;
    private Button buttonCreateSale;
    private Button buttonDeleteSale;
    private Button buttonShowSaleList;
    private Button  buttonAddPoints;
    private ArrayList<ArrayList<String>> activeArArSale;
    private ArrayList<ArrayList<String>> passiveArArSale;
    private String saleNameForGlobalData;
    private String saleIdForGlodalData;
    private Button buttonSaveBySale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_sale_man);

        listViewSaleMan = (ListView) findViewById(R.id.listViewSaleMan);

        vActivSale = (TextView) findViewById(R.id.tvActivSale); /* Text organisation name */
        vActivSale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<ArrayList<String>> dataList;// = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points");
                adapter = null;
                String name = s.toString();
                dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE sales_name='"+name+"' AND organisation_name='"+GlobalDatas.getOrgName()+"'");
                makePointAdapter(dataList, name);
                listViewSaleMan.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        layBtnSale = (LinearLayout) findViewById(R.id.layOfButtonSale); /* Button's View collection */
        tvTextOrgName = (TextView) findViewById(R.id.tvTextOrgName); /*Organisayion name from GlobalDatas.organisation*/

        tvTextOrgName.setText (GlobalDatas.getOrgName());
        /* BUTTONS */
        buttonEditSale = (Button) findViewById(R.id.btnEditSale);
        buttonEditSale.setOnClickListener(this);
        buttonCreateSale = (Button) findViewById(R.id.btnCreateSale);
        buttonCreateSale.setOnClickListener(this);
        buttonDeleteSale = (Button) findViewById(R.id.btnDeleteSale);
        buttonDeleteSale.setOnClickListener(this);
        buttonShowSaleList = (Button) findViewById(R.id.btnShowSaleList);
        buttonShowSaleList.setOnClickListener(this);
        buttonAddPoints = (Button) findViewById(R.id.btnSaleAddPoints);
        buttonAddPoints.setOnClickListener(this);
        buttonSaveBySale = (Button) findViewById(R.id.button_saveBySale);
        buttonSaveBySale.setOnClickListener(this);
        prepareWindowOrganisation();
    }

    private void prepareWindowOrganisation() {

        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        ArrayList<ArrayList<String>> activeArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name, org_id FROM organisations WHERE is_active='true'");
        ArrayList<ArrayList<String>> passiveArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name FROM organisations WHERE is_active='false'");
        TetDebugUtil.e(pseudo_tag,"activeArAr.isEmpty = "+ activeArArOrg.isEmpty()+" passiveArAr.isEmpty="+ passiveArArOrg.isEmpty()+"");

        if (activeArArOrg.isEmpty() && !passiveArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" Do Start AllertOneAndTwoAndThreeButton");
            String title = getResources().getString(R.string.worning).toString();
            String mesage =  getResources().getString(R.string.txtVhoiseOrganisatoin).toString();
            Intent intent = new Intent(getApplicationContext(),CreateNewRouteActivityAutonom.class);
            new AllertOneAndTwoAndThreeButton().createOneButtonsAlertDialog(this,title, mesage ,intent).show();
            return;

        }

        if (!activeArArOrg.isEmpty()) {

            activeArArSale = new ArrayList<>(); // allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT salesman_name, salesman_id FROM salesman WHERE is_active='true' AND org_id=" + GlobalDatas.orgId + "");
            passiveArArSale = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT salesman_name FROM salesman WHERE org_id=" + GlobalDatas.orgId + "");
            TetDebugUtil.e(pseudo_tag, "activeArAr.isEmpty = " + activeArArSale.isEmpty() + " passiveArAr.isEmpty=" + passiveArArSale.isEmpty() + "");
        } else {
            String title = getResources().getString(R.string.worning).toString();
            String message = getResources().getString(R.string.txtVhoiseOrganisatoin).toString();
            Intent intent = new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class);
            new AllertOneAndTwoAndThreeButton().createOneButtonsAlertDialog(this,title, message,intent).show();
            return;
        }
            if (passiveArArSale.isEmpty()) {
                TetDebugUtil.e(pseudo_tag, " passiveArAr.isEmpty() Hide ");
                buttonShowSaleList.setVisibility(View.GONE);
            }



        if (activeArArSale.isEmpty() && passiveArArSale.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"if 0 (activeArAr.isEmpty() && passiveArAr.isEmpty())");
            startActivity(new Intent(getApplicationContext(),AddNewSaleMan.class));
            this.finish();
        }

        if (activeArArSale.isEmpty() && !passiveArArSale.isEmpty()) {
            TetDebugUtil.e(pseudo_tag,"if 2 (activeArAr.isEmpty() && !passiveArAr.isEmpty()");
            layBtnSale.setVisibility(View.VISIBLE);
            if (!passiveArArSale.isEmpty()){
                TetDebugUtil.e(pseudo_tag, "ADDing to items list");
                //makeGlobalDataForSaleman();
                modelSpinnerDialogSale = SpinerDialog.makeDialogAndReturnIt(null,vActivSale,this, passiveArArSale);
            }
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+": spinnerDialog.showSpinerDialog();");
            modelSpinnerDialogSale.showSpinerDialog(pseudo_tag);
        }
    }

    private void makePointAdapter(ArrayList<ArrayList<String>> dataList, String name) {
        if (dataList.isEmpty()) {
            dataList = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE sales_name='" + name + "' AND organisation_name='" + GlobalDatas.getOrgName() + "'");
        }
        adapter = null;
        adapter = new AdapterCustomForPointsList(this, dataList);
        listViewSaleMan.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnShowSaleList){
            modelSpinnerDialogSale.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnCreateSale){
            TetDebugUtil.d(pseudo_tag, "PUSHED Button btnCreateOrg");
            startActivity(new Intent(this.getApplicationContext(),AddNewSaleMan.class));
            this.finish();
        } else if (id == R.id.btnEditSale){
            startActivity(new Intent(getApplicationContext(),EditSaleMan.class));
            this.finish();
        } else if (id == R.id.button_saveBySale) {
            onBackPressed();
        } else if (id == R.id.btnSaleAddPoints) {
            TetDebugUtil.e(pseudo_tag," Click btnSaleAddPoints");
            String title = getResources().getString(R.string.txtAdditingSalePoint);
            String content = getResources().getString(R.string.txtEdditionPointAdd) + " "+ GlobalDatas.getOrgName();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
        this.finish();
    }


}

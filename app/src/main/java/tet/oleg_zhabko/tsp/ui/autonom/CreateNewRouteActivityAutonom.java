package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;


import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.MainActivityAutonom;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterCustomForPointsList;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.OnClickSpinerItemCAllBackInterface;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.ModelSpinnerDialog;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class CreateNewRouteActivityAutonom extends Activity implements View.OnClickListener{



    Context mContext = this;
    Activity activity = this;
    private final String pseudo_tag = CreateNewRouteActivityAutonom.class.getSimpleName();
    private final AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    //TextView selectedItems;
    ArrayList<String> itemsOrg = new ArrayList<>();
    ModelSpinnerDialog modelSpinnerDialogOrg;
    TextView vActivOrg;
    ListView listView;
    AdapterCustomForPointsList adapter;
    private LinearLayout layBtnOrg;
    private LinearLayout layChoiseAddingType;
    private Button buttonEditOrg;
    private Button buttonCreateOrg;
    private Button buttonDeleteOrg;
    private Button buttonShowOrgList;
    private ArrayList<ArrayList<String>> activeArArOrg;
    private ArrayList<ArrayList<String>> passiveArArOrg;
    private ArrayList<ArrayList<String>> activeArArSale;
    private ArrayList<ArrayList<String>>passiveArArSale;
    private String orgNameForGlobalData;
    private String orgIdForGlodalData;
    private Button buyyonAddBySaleMan;
    private Button buttonByZone;
    private Button buttonNewSAleMan;
    private Button buttonNezone;
    private Button buttonSavePoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_create_new_route_autonom);

       // selectedItems = (TextView) findViewById(R.id.spinerOrg);

        vActivOrg = (TextView) findViewById(R.id.tvActivOrg); /* Text organisation name */
        vActivOrg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TetDebugUtil.e(pseudo_tag,"onTextChanged to =["+s.toString()+"]");
                String name = s.toString();
                GlobalDatas.setOrgNameAndOrgId(name);
                ArrayList<ArrayList<String>> dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE organisation_name='" + GlobalDatas.getOrgName() + "'");
                // prepareWindowOrganisation();
                makePointAdapter(dataList);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        layBtnOrg = (LinearLayout) findViewById(R.id.layOfButtonOrg); /* Button's View collection */
        layChoiseAddingType = (LinearLayout) findViewById(R.id.layChoiseType);
        listView = findViewById(R.id.listViewOrgPoints);


        /* BUTTONS */
        buttonEditOrg = (Button) findViewById(R.id.btnEditOrg);
        buttonEditOrg.setOnClickListener(this);
        buttonCreateOrg = (Button) findViewById(R.id.btnCreateOrg);
        buttonCreateOrg.setOnClickListener(this);
        buttonDeleteOrg = (Button) findViewById(R.id.btnDeleteOrg);
        buttonDeleteOrg.setOnClickListener(this);
        buttonShowOrgList = (Button) findViewById(R.id.btnShowOrgList);
        buttonShowOrgList.setOnClickListener(this);
        buyyonAddBySaleMan = (Button) findViewById(R.id.btnBySaleMan);
        buyyonAddBySaleMan.setOnClickListener(this);
        buttonByZone = (Button) findViewById(R.id.btnByZone);
        buttonByZone.setOnClickListener(this);
        buttonNewSAleMan = (Button) findViewById(R.id.btnNewSAleM);
        buttonNewSAleMan.setOnClickListener(this);
        buttonNezone = (Button) findViewById(R.id.btnNezone);
        buttonNezone.setOnClickListener(this);
        buttonSavePoints = (Button) findViewById(R.id.button_save);
        buttonSavePoints.setOnClickListener(this);



        prepareWindowOrganisation();
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        makePointAdapter(dataList);

    }

    private void makePointAdapter(ArrayList<ArrayList<String>> dataList) {
        if (dataList.isEmpty()) {
            dataList = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT point_id, zone, point_owner FROM owner_points WHERE organisation_name='" + GlobalDatas.getOrgName() + "'");
        }
        if(adapter != null) {
            adapter = null;
        }
        adapter = new AdapterCustomForPointsList(this, dataList);
        listView.setAdapter(adapter);
    }

    private void prepareWindowOrganisation() {
        activeArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name, org_id FROM organisations WHERE is_active='true'");
        passiveArArOrg = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT organisation_name FROM organisations WHERE is_active='false'");
        TetDebugUtil.e(pseudo_tag,"activeArAr.isEmpty = "+ activeArArOrg.isEmpty()+" passiveArAr.isEmpty="+ passiveArArOrg.isEmpty()+"");

        if (passiveArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag, " passiveArAr.isEmpty() Hide ");
            buttonShowOrgList.setVisibility(View.GONE);
        }

        if (!activeArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag, "!activeArAr.isEmpty()");
            vActivOrg.setVisibility(View.VISIBLE);
            ArrayList<String> orgActAr = activeArArOrg.get(0);
            if(!orgActAr.isEmpty()){
                orgNameForGlobalData = orgActAr.get(0);
                orgIdForGlodalData = orgActAr.get(1);
                vActivOrg.setText(orgNameForGlobalData);
                GlobalDatas.setOrgNameAndOrgId(orgNameForGlobalData);
                GlobalDatas.orgId = orgIdForGlodalData;
                TetDebugUtil.e(pseudo_tag, "Write GlobalDatas.orgId = "+GlobalDatas.orgId+"");
            }
        }


        if (activeArArOrg.isEmpty() && passiveArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag,"if 0 (activeArAr.isEmpty() && passiveArAr.isEmpty())");
            startActivity(new Intent(getApplicationContext(),AddNewOrganisation.class));
            this.finish();
        }
        if (!activeArArOrg.isEmpty() && passiveArArOrg.isEmpty()){
            TetDebugUtil.e(pseudo_tag," if 1 !activeArAr.isEmpty() && passiveArAr.isEmpty() GlobalDatas.db_name="+GlobalDatas.db_name+"");
            buttonShowOrgList.setVisibility(View.GONE);
            vActivOrg.setVisibility(View.VISIBLE);
            ArrayList<String> orgActAr = activeArArOrg.get(0);
            if(!orgActAr.isEmpty()){
                orgNameForGlobalData = orgActAr.get(0);
                orgIdForGlodalData = orgActAr.get(1);
                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" orgNameForGlobalData = "+orgNameForGlobalData+"");
                vActivOrg.setText(orgNameForGlobalData);
                GlobalDatas.setOrgNameAndOrgId(orgNameForGlobalData);
                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+"");
            }
        }
        if (!activeArArOrg.isEmpty() && !passiveArArOrg.isEmpty()){
            buttonShowOrgList.setVisibility(View.VISIBLE);
            int sise = passiveArArOrg.size();
            for(int i =0; i < sise; i++) {
                ArrayList<String> passiveAr = passiveArArOrg.get(i);
                if (!passiveAr.isEmpty()) {
                    TetDebugUtil.e(pseudo_tag, "ADDing to items list");
                    makeDialogForOrg(passiveAr);
                }
            }
        }

        if (activeArArOrg.isEmpty() && !passiveArArOrg.isEmpty()) {
            TetDebugUtil.e(pseudo_tag,"if 2 (activeArAr.isEmpty() && !passiveArAr.isEmpty()");
            layChoiseAddingType.setVisibility(View.GONE);
            int sise = passiveArArOrg.size();
            for(int i =0; i < sise; i++) {
                ArrayList<String> passiveAr = passiveArArOrg.get(i);
                if (!passiveAr.isEmpty()) {
                    TetDebugUtil.e(pseudo_tag, "ADDing to items list");
                    makeDialogForOrg(passiveAr);
                }
            }
            TetDebugUtil.e(pseudo_tag,""+pseudo_tag+": spinnerDialog.showSpinerDialog();");
            modelSpinnerDialogOrg.showSpinerDialog(pseudo_tag);
        }
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnShowOrgList){
            TetDebugUtil.e(pseudo_tag," Touched .btnShowOrgList");
            modelSpinnerDialogOrg.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnCreateOrg){
            TetDebugUtil.d(pseudo_tag, "PUSHED Button btnCreateOrg");
            startActivity(new Intent(this.getApplicationContext(),AddNewOrganisation.class));
            this.finish();
        } else if (id == R.id.btnEditOrg){
            TetDebugUtil.e(pseudo_tag," Touched btnEditOrg");
            startActivity(new Intent(getApplicationContext(),EditOrganisation.class));
            this.finish();
        } else if(id == R.id.btnBySaleMan){
            TetDebugUtil.e(pseudo_tag," Touched R.id.btnBySaleMan");
            startActivity(new Intent(getApplicationContext(),SaleManActivity.class));
            this.finish();
        } else if(id == R.id.btnByZone) {
            TetDebugUtil.e(pseudo_tag, " Touched R.id.btnByZone");
            startActivity(new Intent(getApplicationContext(), ZoneActivity.class));
            this.finish();
        } else if (id == R.id.btnNewSAleM){
            Intent intent = new Intent(getApplicationContext(), AddNewSaleMan.class);
            intent.putExtra("who", pseudo_tag);
            startActivity(intent);
            this.finish();
        } else if (id == R.id.btnNezone){
            Intent intent = new Intent(getApplicationContext(), AddNewZone.class);
            intent.putExtra("who", pseudo_tag);
            startActivity(intent);
            this.finish();

        } else if (id == R.id.button_save) {
            ArrayList<ArrayList<String>> checkedItems = adapter.getCheckedItems();
            if (checkedItems.size() == 0){
                String msg = getResources().getString(R.string.hintNoAnySelected);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                return;
            }
          //  Toast.makeText(this, "Отмечено элементов: " + checkedItems.size(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ShowAndEditRoute.class));
        }
    }


    private void makeDialogForOrg(@NonNull ArrayList<String> passiveAr) {
        int size = passiveAr.size();
        for (int i = 0; i < size; i++){
            String org = passiveAr.get(i);
            TetDebugUtil.e(pseudo_tag,"itemsOrg.add("+org+")");
            itemsOrg.add(org);
        }
//        items.add("Mumbai");
//        items.add("Delhi");
        String dialogTitleOrg = getString(R.string.select_or_search_org).toString();
        modelSpinnerDialogOrg = new ModelSpinnerDialog(CreateNewRouteActivityAutonom.this, itemsOrg,
                dialogTitleOrg);

        modelSpinnerDialogOrg.setTitleColor(getResources().getColor(R.color.colorAccent));
        modelSpinnerDialogOrg.setSearchIconColor(getResources().getColor(R.color.colorAccent));
        modelSpinnerDialogOrg.setSearchTextColor(getResources().getColor(R.color.colorAccent));
        modelSpinnerDialogOrg.setItemColor(getResources().getColor(R.color.colorAccent));
        modelSpinnerDialogOrg.setItemDividerColor(getResources().getColor(R.color.colorAccent));
        modelSpinnerDialogOrg.setCloseColor(getResources().getColor(R.color.colorAccent));

        modelSpinnerDialogOrg.setCancellable(true);
        modelSpinnerDialogOrg.setShowKeyboard(false);
        modelSpinnerDialogOrg.bindOnSpinerListener(new OnClickSpinerItemCAllBackInterface() {
            @Override
            public void onClickSpinerItem(String item, int position) {
               allDbController.executeQuery(getApplicationContext(),GlobalDatas.db_name,"UPDATE organisations SET is_active='false'");
                allDbController.executeQuery(getApplicationContext(),GlobalDatas.db_name,"UPDATE organisations SET is_active='true' WHERE organisation_name='"+item+"'");
                vActivOrg.setText(item);
                GlobalDatas.setOrgNameAndOrgId(item.toString());
                ArrayList<ArrayList<String>> res = allDbController.executeQuery(getApplicationContext(), GlobalDatas.db_name, "SELECT org_id FROM organisations WHERE organisation_name='" + item + "'");
                if(!res.isEmpty()){
                    String id = res.get(0).get(0);
                    GlobalDatas.orgId = id;
                    TetDebugUtil.e(pseudo_tag, "Write GlobalDatas.orgId = "+GlobalDatas.orgId+"");
                    layChoiseAddingType.setVisibility(View.VISIBLE);
                }
                TetDebugUtil.e(pseudo_tag,"GlobalDatas.organisation="+GlobalDatas.getOrgName()+" GlobalDatas.orgId ="+GlobalDatas.orgId+" position="+position+"");
                itemsOrg = new ArrayList<>();
                prepareWindowOrganisation();
            }

        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivityAutonom.class));
        this.finish();
    }



}



package tet.oleg_zhabko.tsp.ui.autonom;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

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

import androidx.annotation.NonNull;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.MainActivity;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.adapters.AdapterChoiseRoutes;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.ModelSpinnerDialog;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.OnClickSpinerItemCAllBackInterface;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class ChoiceRouteActivityAutonom extends Activity implements View.OnClickListener {
    private String pseudo_tag = ChoiceRouteActivityAutonom.class.getSimpleName();
    private final AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    ModelSpinnerDialog modelSpinnerDialogOrg;
    ArrayList<String> itemsOrg = new ArrayList<>();
    Context mContext = this;
    private TextView vActivOrg;
    private LinearLayout layBtnOrg;
    private ListView listView;
    private AdapterChoiseRoutes adapter;
    private Button buttonCreateOrg;
    private Button buttonDeleteOrg;
    private Button buttonShowOrgList;
    private ArrayList<ArrayList<String>> activeArArOrg;
    private ArrayList<ArrayList<String>> passiveArArOrg;
    private String orgNameForGlobalData;
    private String orgIdForGlodalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();

        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_choice_route_autonom);

        vActivOrg = (TextView) findViewById(R.id.tvActivOrgInRoutes); /* Text organisation name */
        layBtnOrg = (LinearLayout) findViewById(R.id.layOfButtonOrgInRoutes); /* Button's View collection */
        listView = (ListView) findViewById(R.id.listViewInRoutes);
        buttonCreateOrg = (Button) findViewById(R.id.btnCreateOrgInRoutes);
        buttonCreateOrg.setOnClickListener(this);
        buttonDeleteOrg = (Button) findViewById(R.id.btnDeleteOrgInRoutes);
        buttonDeleteOrg.setOnClickListener(this);
        buttonShowOrgList = (Button) findViewById(R.id.btnShowOrgListInRoutes);
        buttonShowOrgList.setOnClickListener(this);

        if (!GlobalDatas.getOrgName().isEmpty()){
            vActivOrg.setText(GlobalDatas.getOrgName());
        }

        vActivOrg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TetDebugUtil.e(pseudo_tag,"onTextChanged to =["+s.toString()+"]");
                String name = s.toString();
                GlobalDatas.setOrgNameAndOrgId(name);
                adapter = null;
                ArrayList<ArrayList<String>> dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT route_id, route_name, route_points FROM owner_routes WHERE org_name='" + GlobalDatas.getOrgName() + "'");
                makeChoiseRouteAdapter(dataList, name);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        prepareWindowOrganisation();

    }

    private void makeChoiseRouteAdapter(ArrayList<ArrayList<String>> dataList, String name) {

            if (dataList.isEmpty() && !GlobalDatas.getOrgName().isEmpty()) {
                dataList = allDbController.executeQuery(mContext, GlobalDatas.db_name, "SELECT route_id, route_name, route_points FROM owner_routes");
            }
            adapter = null;
            adapter = new AdapterChoiseRoutes(this, dataList);

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
        if (id == R.id.btnShowOrgListInRoutes){
            TetDebugUtil.e(pseudo_tag," Touched .btnShowOrgList");
            modelSpinnerDialogOrg.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnCreateOrgInRoutes) {
            TetDebugUtil.d(pseudo_tag, "PUSHED Button btnCreateOrg");
            startActivity(new Intent(this.getApplicationContext(), AddNewOrganisation.class));
            this.finish();
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
        modelSpinnerDialogOrg = new ModelSpinnerDialog(ChoiceRouteActivityAutonom.this, itemsOrg,
                dialogTitleOrg);

        modelSpinnerDialogOrg.setTitleColor(getResources().getColor(R.color.tetAccent));
        modelSpinnerDialogOrg.setSearchIconColor(getResources().getColor(R.color.tetAccent));
        modelSpinnerDialogOrg.setSearchTextColor(getResources().getColor(R.color.tetAccent));
        modelSpinnerDialogOrg.setItemColor(getResources().getColor(R.color.tetAccent));
        modelSpinnerDialogOrg.setItemDividerColor(getResources().getColor(R.color.tetAccent));
        modelSpinnerDialogOrg.setCloseColor(getResources().getColor(R.color.tetAccent));

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
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }
}

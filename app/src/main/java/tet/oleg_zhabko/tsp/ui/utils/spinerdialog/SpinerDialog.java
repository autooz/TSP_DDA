package tet.oleg_zhabko.tsp.ui.utils.spinerdialog;// Created: by PC BEST, OS Linux

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointOwnPoint;
import tet.oleg_zhabko.tsp.ui.autonom.SaleManActivity;
import tet.oleg_zhabko.tsp.ui.autonom.ZoneActivity;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone/WhataApp/Viber/Telegram: +38(067) 411-98-75
//              Berdichev, Ukraine
//
public class SpinerDialog {

 private static String pseudo_tag = SpinerDialog.class.getSimpleName();

    public static ModelSpinnerDialog makeDialogAndReturnIt(TextView selectedItemsDebug_or_null, TextView vActivTv, Activity activity, ArrayList<ArrayList<String>> passiveArAr) {

        Activity mActivity = activity;
        ArrayList<String> passiveAr = new ArrayList<>();
        ArrayList<String> itemsList = new ArrayList<>();
        int size = passiveArAr.size();
        TetDebugUtil.e(pseudo_tag, " "+pseudo_tag+" size="+size+"");
        for (int i = 0; i < size; i++) {
            passiveAr = passiveArAr.get(i);
            //      }


        int size1 = passiveAr.size();
        for (int i0 = 0; i0 < size1; i0++){
            String org = passiveAr.get(i0);
//            String id = passiveAr.get(1);
            itemsList.add(org);
        }
        }

        String dialogTitle = activity.getString(R.string.select_or_search_org).toString();;
        ModelSpinnerDialog modelSpinnerDialog = new ModelSpinnerDialog(activity, itemsList,
                dialogTitle);

        modelSpinnerDialog.setTitleColor(activity.getResources().getColor(R.color.tetAccent));
        modelSpinnerDialog.setSearchIconColor(activity.getResources().getColor(R.color.tetAccent));
        modelSpinnerDialog.setSearchTextColor(activity.getResources().getColor(R.color.tetAccent));
        modelSpinnerDialog.setItemColor(activity.getResources().getColor(R.color.tetAccent));
        modelSpinnerDialog.setItemDividerColor(activity.getResources().getColor(R.color.tetAccent));
        modelSpinnerDialog.setCloseColor(activity.getResources().getColor(R.color.tetAccent));

        modelSpinnerDialog.setCancellable(true);
        modelSpinnerDialog.setShowKeyboard(false);
        modelSpinnerDialog.bindOnSpinerListener(new OnClickSpinerItemCAllBackInterface() {

            @Override
            public void onClickSpinerItem(String item, int position) {
                AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
                Context App = ThisApp.getInstance().getApplicationContext();
                String who = activity.getClass().getSimpleName();
                //allDbController.executeQuery(App, GlobalDatas.db_name,"UPDATE zones SET is_active='false' WHERE org_id="+GlobalDatas.orgId+"");
                if (who.equals(ZoneActivity.class.getSimpleName())) {
                    ArrayList<ArrayList<String>> aRR = allDbController.executeQuery(App, GlobalDatas.db_name, "SELECT zone_id FROM zones WHERE zone_name='" + item + "' AND org_id=" + GlobalDatas.orgId + "");
                    if (!aRR.isEmpty()){
                        ArrayList<String> aR = aRR.get(0);
                        if(!aR.isEmpty()){
                            GlobalDatas.zoneId = aR.get(0);
                            TetDebugUtil.e(pseudo_tag, "Write GlobalDatas.orgId = "+GlobalDatas.orgId+"");
                        }
                    }
                    vActivTv.setText(item);
                    if (selectedItemsDebug_or_null != null) {
                        selectedItemsDebug_or_null.setText(item + " Position: " + position);
                    }
                    GlobalDatas.zoneName = item;
                }
                if (who.equals(SaleManActivity.class.getSimpleName())) {
                    ArrayList<ArrayList<String>> aRR = allDbController.executeQuery(App, GlobalDatas.db_name, "SELECT salesman_id FROM salesman WHERE salesman_name='" + item + "' AND org_id=" + GlobalDatas.orgId + "");
                    vActivTv.setText(item);
                    if (!aRR.isEmpty()){
                        ArrayList<String> aR = aRR.get(0);
                        if(!aR.isEmpty()){
                            GlobalDatas.saleManId = aR.get(0);
                        }
                    }
                    if (selectedItemsDebug_or_null != null) {
                        selectedItemsDebug_or_null.setText(item + " Position: " + position);
                    }
                    GlobalDatas.saleManName = item;
                }
                if (who.equals(AddNewPointOwnPoint.class.getSimpleName())) {
                    vActivTv.setText(item);
                    int color = mActivity.getApplicationContext().getResources().getColor(R.color.black);
                    vActivTv.setTextColor(color);
                    if (selectedItemsDebug_or_null != null) {
                        selectedItemsDebug_or_null.setText(item + " Position: " + position);
                    }
                }

            }
        });
        return modelSpinnerDialog;
    }
}

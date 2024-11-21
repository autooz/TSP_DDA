package tet.oleg_zhabko.tsp.ui.utils.spinerdialog;// Created: by PC BEST, OS Linux

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewPointOwnPoint;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewSaleMan;
import tet.oleg_zhabko.tsp.ui.autonom.AddNewZone;
import tet.oleg_zhabko.tsp.ui.autonom.CreateNewRouteActivityAutonom;
import tet.oleg_zhabko.tsp.ui.autonom.SaleManActivity;
import tet.oleg_zhabko.tsp.ui.autonom.ZoneActivity;
import tet.oleg_zhabko.tsp.ui.points_and_maps.ActivityOsmOnLineAddPoint;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone/WhataApp/Viber/Telegram: +38(067) 411-98-75
//              Berdichev, Ukraine
//
public class ModelSpinnerDialog {

    private  Activity activity;
    String pseudo_tag=ModelSpinnerDialog.class.getSimpleName();
    ArrayList<String> items;
    Activity context;
    String dTitle, closeTitle = "Close";
    OnClickSpinerItemCAllBackInterface CallBackOnClickSpinerItem;
    AlertDialog alertDialog;
    int pos;
    int style;
    boolean cancellable = false;
    boolean showKeyboard = false;
    boolean useContainsFilter = false;
    int titleColor,searchIconColor,searchTextColor,itemColor,itemDividerColor,closeColor;
    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();

    private void initColor(Context context){
        this.titleColor=context.getResources().getColor(R.color.black);
        this.searchIconColor=context.getResources().getColor(R.color.black);
        this.searchTextColor=context.getResources().getColor(R.color.black);
        this.itemColor=context.getResources().getColor(R.color.black);
        this.closeColor=context.getResources().getColor(R.color.black);
        this.itemDividerColor=context.getResources().getColor(R.color.light_gray);
    }

    public ModelSpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        initColor(context);
        this.activity = activity;
    }

    public ModelSpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public ModelSpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        initColor(context);
    }

    public ModelSpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        this.closeTitle = closeTitle;
        initColor(context);
    }

    public void bindOnSpinerListener(OnClickSpinerItemCAllBackInterface onClickSpinerItem1) {
        this.CallBackOnClickSpinerItem = onClickSpinerItem1;
    }

    public void showSpinerDialog(String classname) {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.dialog_organisation_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        ImageView searchIcon=(ImageView) v.findViewById(R.id.searchIcon);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        final ListView listView = (ListView) v.findViewById(R.id.list);

        ColorDrawable sage = new ColorDrawable(itemDividerColor);
        listView.setDivider(sage);
        listView.setDividerHeight(1);

        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        if (isShowKeyboard()) {
            showKeyboard(searchBox);
        }

        title.setTextColor(titleColor);
        searchBox.setTextColor(searchTextColor);
        rippleViewClose.setTextColor(closeColor);
        searchIcon.setColorFilter(searchIconColor);


//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.items_view, items);
        final AdapterSpinerDialogWithFilter<String> adapter = new AdapterSpinerDialogWithFilter<String>(context, R.layout.items_view, items) {

            @Override@NonNull
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1=view.findViewById(R.id.textItemName);
                text1.setTextColor(itemColor);
                return view;
            }
        };
        listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TetDebugUtil.e(pseudo_tag, "onItemClick in");

                TextView t = (TextView) view.findViewById(R.id.textItemName);
                for (int j = 0; j < items.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(items.get(j).toString())) {
                        pos = j;
                        TetDebugUtil.e(pseudo_tag, "onItemClick item "+items.get(j)+"  pos = "+pos+"");
                    }
                }
                CallBackOnClickSpinerItem.onClickSpinerItem(t.getText().toString(), pos);
                /* ------------------- inserted for project TSP ------------------------ */

                TetDebugUtil.e(pseudo_tag," ------------------- inserted for project TSP ------------------------ ");
                TetDebugUtil.e(pseudo_tag,"Initialiser is classname=["+classname+"]");

                ArrayList<ArrayList<String>> allSaleman = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT salesman_name FROM salesman WHERE org_id="+GlobalDatas.orgId+"");
                ArrayList<ArrayList<String>> allZones = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT zone_name FROM zones WHERE org_id="+GlobalDatas.orgId+" ");
                    TetDebugUtil.e(pseudo_tag,"allSaleman =["+allSaleman+"] allZones=["+allZones+"]");



                ArrayList<ArrayList<String>> allPointsArAr = new ArrayList<>();
                if (classname.equals(SaleManActivity.class.getSimpleName())) {
                    TetDebugUtil.e(pseudo_tag,"YES I am here 1");
                    allPointsArAr = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT `point_id`, `zone`, `point_owner`, `landmarks` FROM `owner_points` WHERE `sales_name`='"+GlobalDatas.saleManName+"' AND organisation_name='"+GlobalDatas.getOrgName()+"'");
                    if (allPointsArAr.isEmpty()){
                        showAllert1_2_3_dialog();
                        return;
                    }

                    new ShowAllInArrayList(pseudo_tag, allPointsArAr);

                } else if (classname.equals(ZoneActivity.class.getSimpleName())){
                    TetDebugUtil.e(pseudo_tag,"YES I am here 2");
                    allPointsArAr = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT `point_id`, `zone`, `point_owner`, `landmarks` FROM `owner_points` WHERE `zone`='"+GlobalDatas.zoneName+"' AND organisation_name='"+GlobalDatas.getOrgName()+"'");
                    new ShowAllInArrayList(pseudo_tag, allPointsArAr);
                } else if (classname.equals(CreateNewRouteActivityAutonom.class.getSimpleName())) {
                    allPointsArAr = allDbController.executeQuery(context, GlobalDatas.db_name, "SELECT `point_id`, `zone`, `point_owner`, `landmarks` FROM `owner_points` WHERE organisation_name='"+GlobalDatas.getOrgName()+"'");
                }

                if (allSaleman.isEmpty() || allZones.isEmpty()) {
                    if (allSaleman.isEmpty()) {
                        context.startActivity(new Intent(context, AddNewSaleMan.class));
                    }
                    if (allZones.isEmpty()) {
                        context.startActivity(new Intent(context, AddNewZone.class));
                    }
                    closeSpinerDialog();
                }


                if (allPointsArAr.isEmpty() && !activity.getClass().getSimpleName().equals(AddNewPointOwnPoint.class.getSimpleName()) ){

showAllert1_2_3_dialog();

                }
                /* -------------------------------------------------------------------------  */
                closeSpinerDialog();
            }

            private void showAllert1_2_3_dialog() {
                String title = context.getString(R.string.worning);
                String body = context.getString(R.string.msgNoPoints);
                Intent intent = new Intent(context, ActivityOsmOnLineAddPoint.class);
                // Intent intent = MapsOpenerReturnIntent.getIntentPreferedMap(ThisApp.getContextApp());
                if (intent == null){
                    TetDebugUtil.e(pseudo_tag,"ERROR with MapsOpener.getIntentPreferedMap()");
                    return;                 }

//                    new AllertOneAndTwoAndThreeButton().createTwoButtonsAlertDialog(context,title, body,intent);
                new AllertOneAndTwoAndThreeButton().createThreeButtonAlertDialog(context,title,body, intent,null, false, true).show();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isUseContainsFilter()) {
                    adapter.getContainsFilter(searchBox.getText().toString());
                } else {
                    adapter.getFilter().filter(searchBox.getText().toString());
                }
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSpinerDialog();
            }
        });
        alertDialog.setCancelable(isCancellable());
        alertDialog.setCanceledOnTouchOutside(isCancellable());
        alertDialog.show();
    }

    public void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    private void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    private boolean isCancellable() {
        return cancellable;
    }

    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    private boolean isShowKeyboard() {
        return showKeyboard;
    }

    private boolean isUseContainsFilter() {
        return useContainsFilter;
    }


    public void setShowKeyboard(boolean showKeyboard) {
        this.showKeyboard = showKeyboard;
    }

    public void setUseContainsFilter(boolean useContainsFilter) {
        this.useContainsFilter = useContainsFilter;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public void setSearchIconColor(int searchIconColor) {
        this.searchIconColor = searchIconColor;
    }

    public void setSearchTextColor(int searchTextColor) {
        this.searchTextColor = searchTextColor;
    }

    public void setItemColor(int itemColor) {
        this.itemColor = itemColor;
    }

    public void setCloseColor(int closeColor) {
        this.closeColor = closeColor;
    }

    public void setItemDividerColor(int itemDividerColor) {
        this.itemDividerColor = itemDividerColor;
    }
}

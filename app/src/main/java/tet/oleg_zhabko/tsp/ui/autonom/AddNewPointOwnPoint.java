package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.edit_point_maps.ActivityOsmOnLineAddPoint;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.ModelSpinnerDialog;
import tet.oleg_zhabko.tsp.ui.utils.spinerdialog.SpinerDialog;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddNewPointOwnPoint extends Activity implements View.OnClickListener {

    private String pseudo_tag = AddNewPointOwnPoint.class.getSimpleName();
    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();

    private int colorRed;
    private int colorGreen;
    private Spinner spinSaleMan;
    private TextView vActivSale;
    private TextView vActivZone;
    private Button buttonShowSaleList;
    private ModelSpinnerDialog modelSpinnerDialogSale;
    private ModelSpinnerDialog modelSpinnerDialogZone;
    private ArrayList<ArrayList<String>> passiveArArSale;
    private Button buttonShowZooneList;
    private ArrayList<ArrayList<String>> passiveArArZone;
    private Spinner spinnerStartWorkH;
    private Spinner spinnerStartWorkM;
    private Spinner spinnerFinWorkHouer;
    private Spinner spinFinWorkMinutes;
    private Spinner spinnerStartStrikeHouer;
    private Spinner spinnerStartStrikeMinutes;
    private Spinner spinnerStopStrikeHouer;
    private Spinner spinnerStopStrikeMin;
    private Button buttonSavePoint;
    private Button buttoDoNotSavePoint;
    /* To dada base */
    private float toDb_lat;
    private float toDb_lon;
    private String toDb_organisation;
    private String toDb_orgId;
    private EditText toDb_edNewOwnerName;
    private EditText toDb_lendmark;
    private TextView toDb_tvActivSalePoint;
    private TextView toDb_activeZone;
    private EditText toDb_edNewAmbar;
    private EditText toDb_edNewOffice;
    private EditText toDb_edNewAmbarPhone;
    private EditText toDb_edNewOfficePhone;
    private EditText toDb_edtNewStartWorHouer;
    private EditText toDb_edtNewStartWorMinutes;
    private EditText toDb_edtFinWorkHouer;
    private EditText toDb_edtFinWorkMinutes;
    private EditText toDb_edtNewStartStrikeHouer;
    private EditText toDb_edtNewStartSyrikeMinutes;
    private EditText toDb_edtFinStrikeHouer;
    private EditText toDb_edtFinStrileMinutes;
    private String who;
    private String org;
    private String saleman;
    private String zone;
    /* ----- */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!=START " + pseudo_tag + " orgName=" + GlobalDatas.getOrgName() + " orgId=" + GlobalDatas.orgId + " Sale Zone=" + GlobalDatas.saleManName + " sid=" + GlobalDatas.saleManId + "   " + GlobalDatas.zoneName + " id=" + GlobalDatas.zoneId + "============");
        who = pseudo_tag;
        Intent intent = getIntent();
        intentManipulation(intent);
        toDb_lat = intent.getFloatExtra("lat", 0);
        toDb_lon = intent.getFloatExtra("lon", 0);

        TetDebugUtil.e(pseudo_tag, "Lat " + toDb_lat + " " + "lon " + toDb_lon + "");
        colorRed = getApplicationContext().getResources().getColor(R.color.colorAccent);
        colorGreen = getApplicationContext().getResources().getColor(R.color.tetGreen);

        setContentView(R.layout.activity_add_new_point_own_point);

//        GlobalDatas.orgId = "1";
//        GlobalDatas.organisation = "MONAKO";

        TextView txtNewPointTitle = (TextView) findViewById(R.id.txtNewPointTitle);
        String title = getResources().getString(R.string.txtTitleNewPoint) + ": " + GlobalDatas.getOrgName();
        txtNewPointTitle.setText(title);
        TextView txtNewPointName = (TextView) findViewById(R.id.txtNewPointName);
        String txt = getResources().getString(R.string.txtPointName) + "\n Lat:" + Float.toString(toDb_lat) + " Lon:" + Float.toString(toDb_lon) + "";
        txtNewPointName.setText(txt);
        toDb_organisation = GlobalDatas.getOrgName();
        toDb_orgId = GlobalDatas.orgId;
        toDb_tvActivSalePoint = (TextView) findViewById(R.id.tvActivSalePoint);
        toDb_activeZone = (TextView) findViewById(R.id.activeZone);
        toDb_edNewOwnerName = (EditText) findViewById(R.id.edNewOwnerName);
        toDb_lendmark = (EditText) findViewById(R.id.lendMark);
        toDb_edNewAmbar = (EditText) findViewById(R.id.edNewAmbar);
        toDb_edNewOffice = (EditText) findViewById(R.id.edNewOffice);
        toDb_edNewAmbarPhone = (EditText) findViewById(R.id.edNewAmbarPhone);
        toDb_edNewOfficePhone = (EditText) findViewById(R.id.edNewOfficePhone);
        toDb_edtNewStartWorHouer = (EditText) findViewById(R.id.edtNewStartWorHouer);
        toDb_edtNewStartWorMinutes = (EditText) findViewById(R.id.edtNewStartWorMinutes);
        toDb_edtFinWorkHouer = (EditText) findViewById(R.id.edtFinWorkHouer);
        toDb_edtFinWorkMinutes = (EditText) findViewById(R.id.edtFinWorkMinutes);
        toDb_edtNewStartStrikeHouer = (EditText) findViewById(R.id.edtNewStartStrikeHouer);
        toDb_edtNewStartSyrikeMinutes = (EditText) findViewById(R.id.edtNewStartSyrikeMinutes);
        toDb_edtFinStrikeHouer = (EditText) findViewById(R.id.edtFinStrikeHouer);
        toDb_edtFinStrileMinutes = (EditText) findViewById(R.id.edtFinStrileMinutes);

        buttonSavePoint = (Button) findViewById(R.id.btnSavePoint);
        buttonSavePoint.setOnClickListener(this);
        buttoDoNotSavePoint = (Button) findViewById(R.id.btnDoNotSavePoint);
        buttoDoNotSavePoint.setOnClickListener(this);
        prepareSpinerSaleman();
        prepareSpinerZone();
        setTimeSpiners();

    }

    private void intentManipulation(Intent intent) {
        who = intent.getStringExtra("who");
        toDb_lat = intent.getFloatExtra("lat", 0);
        toDb_lon = intent.getFloatExtra("lon", 0);
        if (who !=null) {
            if (who.equals(SaleManActivity.class.getSimpleName()) || who.equals(ZoneActivity.class.getSimpleName())) {
                org = intent.getStringExtra("org");
                saleman = intent.getStringExtra("saleman");
                zone = intent.getStringExtra(zone);
            }
            TetDebugUtil.e(pseudo_tag, "intentManipulation who=[" + who + "] toDb_lat=[" + toDb_lat + "] toDb_lon=[" + toDb_lon + "]");
        }
    }

    private void setTimeSpiners() {
        ArrayAdapter<CharSequence> adapterhouers = ArrayAdapter.createFromResource(this, R.array.houers, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMinutes = ArrayAdapter.createFromResource(this, R.array.minutes, android.R.layout.simple_spinner_item);

        adapterhouers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterMinutes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /* --------------------WORK --------------------*/
        spinnerStartWorkH = (Spinner) findViewById(R.id.spinNewStartWorkHouers);
        spinnerStartWorkH.setAdapter(adapterhouers);
        spinnerStartWorkM = (Spinner) findViewById(R.id.spinStartWorkMinutes);
        spinnerStartWorkM.setAdapter(adapterMinutes);
        spinnerFinWorkHouer = (Spinner) findViewById(R.id.spinFinWorkHouer);
        spinnerFinWorkHouer.setAdapter(adapterhouers);
        spinFinWorkMinutes = (Spinner) findViewById(R.id.spinFinWorkMinutes);
        spinFinWorkMinutes.setAdapter(adapterMinutes);
        /* -----------------------------STRIKE ------------------------------*/
        spinnerStartStrikeHouer = (Spinner) findViewById(R.id.spinNewStartStrikeHouer);
        spinnerStartStrikeHouer.setAdapter(adapterhouers);
        spinnerStartStrikeMinutes = (Spinner) findViewById(R.id.spinStartStrikeMinutes);
        spinnerStartStrikeMinutes.setAdapter(adapterMinutes);
        spinnerStopStrikeHouer = (Spinner) findViewById(R.id.spinNewStopStrikeHouer);
        spinnerStopStrikeHouer.setAdapter(adapterhouers);
        spinnerStopStrikeMin = (Spinner) findViewById(R.id.spinNewStopStrikeMin);
        spinnerStopStrikeMin.setAdapter(adapterMinutes);
        /*==============*/
        setItemListner();

    }

    private void setItemListner() {
        spinnerStartWorkH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtNewStartWorHouer.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStartWorkM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtNewStartWorMinutes.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerFinWorkHouer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtFinWorkHouer.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinFinWorkMinutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtFinWorkMinutes.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        /* -----------------------------STRIKE ------------------------------*/
        spinnerStartStrikeHouer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtNewStartStrikeHouer.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStartStrikeMinutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtNewStartSyrikeMinutes.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStopStrikeHouer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtFinStrikeHouer.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStopStrikeMin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itebTXT = parent.getItemAtPosition(position).toString();
                toDb_edtFinStrileMinutes.setText(itebTXT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void prepareSpinerZone() {
        vActivZone = (TextView) findViewById(R.id.activeZone);
        buttonShowZooneList = (Button) findViewById(R.id.btnShowZonePoint);
        buttonShowZooneList.setOnClickListener(this);

        passiveArArZone = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT zone_name FROM zones WHERE org_id=" + GlobalDatas.orgId + "");
        if (passiveArArZone.isEmpty()) {
            return;
        }
        modelSpinnerDialogZone = SpinerDialog.makeDialogAndReturnIt(null, vActivZone, this, passiveArArZone);
        if (GlobalDatas.zoneName.isEmpty()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vActivZone.setTextColor(colorRed);
            } else {
                vActivZone.setTextColor(colorRed);
            }
        } else {
            vActivZone.setTextColor(colorGreen);
        }

    }

    private void prepareSpinerSaleman() {
        vActivSale = (TextView) findViewById(R.id.tvActivSalePoint);
        buttonShowSaleList = (Button) findViewById(R.id.btnShowSalePoint);
        buttonShowSaleList.setOnClickListener(this);

        passiveArArSale = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT salesman_name FROM salesman WHERE org_id=" + GlobalDatas.orgId + "");
        if (passiveArArSale.isEmpty()) {
            return;
        }
        modelSpinnerDialogSale = SpinerDialog.makeDialogAndReturnIt(null, vActivSale, this, passiveArArSale);

        if (GlobalDatas.saleManName.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                vActivSale.setTextColor(colorRed);
            } else {
                vActivSale.setTextColor(colorRed);
            }
        } else {
            vActivSale.setTextColor(colorGreen);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnShowSalePoint) {
            modelSpinnerDialogSale.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnShowZonePoint) {
            modelSpinnerDialogZone.showSpinerDialog(pseudo_tag);
        } else if (id == R.id.btnSavePoint) {
            TetDebugUtil.d(pseudo_tag, "Pushed save");
            prepareAndSaveDatas();
        } else if (id == R.id.btnDoNotSavePoint) {
            onBackPressed();
        }
    }

    private void prepareAndSaveDatas() {

        String organisation_name = GlobalDatas.getOrgName();
        String zone = String.valueOf(toDb_activeZone.getText());
        String sales_name = String.valueOf(toDb_tvActivSalePoint.getText());
        String phone_number = getSalePhoneNum(sales_name);
        String point_owner = String.valueOf(toDb_edNewOwnerName.getText());
        String latitude = Float.toString(toDb_lat);
        String longitude = Float.toString(toDb_lon);
        String landmarks = String.valueOf(toDb_lendmark.getText());
        String description = String.valueOf(toDb_edNewAmbar.getText());
        String phone_ambar = String.valueOf(toDb_edNewAmbarPhone.getText());
        String description_office = String.valueOf(toDb_edNewOffice.getText());
        String phone_office = String.valueOf(toDb_edNewOfficePhone.getText());
        String work_from_h = String.valueOf(toDb_edtNewStartWorHouer.getText());
        String work_from_m = String.valueOf(toDb_edtNewStartWorMinutes.getText());
        String work_to_h = String.valueOf(toDb_edtFinWorkHouer.getText());
        String work_to_m = String.valueOf(toDb_edtFinWorkMinutes.getText());
        String strike_from_h = String.valueOf(toDb_edtNewStartStrikeHouer.getText());
        String strike_from_m = String.valueOf(toDb_edtNewStartSyrikeMinutes.getText());
        String strike_to_h = String.valueOf(toDb_edtFinStrikeHouer.getText());
        String strike_to_m = String.valueOf(toDb_edtFinStrileMinutes.getText());
        String pin_icon_code = "1";

        String insert = "INSERT INTO owner_points ";
        String variables = "organisation_name, zone, sales_name, phone_number, point_owner," +
                " latitude, longitude, landmarks, description, phone_ambar, description_office, " +
                "work_from_h, work_from_m, work_to_h, work_to_m, strike_from_h, strike_from_m, " +
                "strike_to_h, strike_to_m, pin_icon_code";
        String values = "'"+ organisation_name +"', '" +zone+"', '"+sales_name+"', '"+phone_number+"', '"+point_owner+"', "+
                "'"+latitude+"', '"+longitude+"', '"+landmarks+"', '"+description+"', '"+phone_ambar+"', '"+description_office+"', "+
                "'"+work_from_h+"', '"+work_from_m+"', '"+work_to_h+"', '"+work_to_m+"', '"+strike_from_h+"', '"+strike_from_m+"', "+
                "'"+strike_to_h+"', '"+strike_to_m+"', '"+pin_icon_code+"'";

        String query = insert + "("+variables+") VALUES ("+values+");";
        TetDebugUtil.e(pseudo_tag, " query = [ "+query+" ]");
        allDbController.executeQuery(this, GlobalDatas.db_name, query);
        onBackPressed();


    }

    private String getSalePhoneNum(String sales_name) {
        ArrayList<ArrayList<String>> arr = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT phone_number FROM salesman WHERE salesman_name='" + sales_name + "' AND org_id =" + GlobalDatas.orgId + " ");
        if (!arr.isEmpty()) {
            return arr.get(0).get(0);
        }
        return new String();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (who.equals(SaleManActivity.class.getSimpleName())){
//            startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
//            this.finish();
//        }
        startActivity(new Intent(getApplicationContext(), ActivityOsmOnLineAddPoint.class));
        this.finish();
    }

}
package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddNewSaleMan extends Activity implements View.OnClickListener {
    private String pseudo_tag = AddNewSaleMan.class.getSimpleName();
    AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    private EditText newName;
    private EditText newPhone;
    private EditText newDescr;
    private Button buttonSave;
    private Button buttonDoNotSave;
    private Button buttonChangeOrg;
    private String saleName;
    private String phone;
    private String descr;
    private String isActive;
    private String who;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        who = getIntent().getStringExtra("who");

        setContentView(R.layout.activity_add_new_sale_man);

        String titlePage = getResources().getString(R.string.titleAdditingSaleMans);
        TextView tv = (TextView) findViewById(R.id.titleAddingSale);
        String title = titlePage+":  \n "+GlobalDatas.getOrgName();
        tv.setText(title);


        newName = (EditText) findViewById(R.id.editSaleName);
        newPhone = (EditText) findViewById(R.id.editPhoneSale);
        newDescr = (EditText) findViewById(R.id.editDescriptionSale);

        buttonSave = (Button) findViewById(R.id.btnSaveSale);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveSale);
        buttonDoNotSave.setOnClickListener(this);
        buttonChangeOrg = (Button) findViewById(R.id.btnChangeOrg);
        buttonChangeOrg.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");
//if (GlobalDatas.orgId.equals("null")){
//    TetDebugUtil.e(pseudo_tag," "+pseudo_tag+" prgId=nill");
//}
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnNotSaveSale) {
            startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
            this.finish();
        } else if (id == R.id.btnSaveSale) {
            saleName = newName.getText().toString();
            phone = newPhone.getText().toString();
            descr = newDescr.getText().toString();
            isActive = "false";
            if (TextUtils.isEmpty((CharSequence) saleName)) {
                String msg = getResources().getString(R.string.txtAddOrganisation).toString();
                Toast.makeText(AddNewSaleMan.this, msg, Toast.LENGTH_LONG).show();
                return;
            }

            String queryForChesk = "SELECT salesman_name FROM  salesman WHERE salesman_name='" + newName + "'";
            String query = "INSERT INTO salesman (org_id, salesman_name, phone_number, desc, is_active ) VALUES ("+GlobalDatas.orgId+",'" + saleName + "', '" + phone + "', '" + descr + "', '" + isActive + "')";

            if (allDbController.executeQuery(this, GlobalDatas.db_name, queryForChesk).isEmpty()) {
                try {
                    allDbController.executeQuery(this, GlobalDatas.db_name, query);
                } catch (Exception e) {
                    e.printStackTrace();
                    TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
                }
            } else {
                TetDebugUtil.e(pseudo_tag, "There is org " + newName + "");
            }
            startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
            this.finish();
        } else if (id == R.id.btnChangeOrg){
            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (who.equals(CreateNewRouteActivityAutonom.class.getSimpleName())){
            startActivity(new Intent(getApplicationContext(),CreateNewRouteActivityAutonom.class));
            this.finish();
        } else {

            startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
            this.finish();
        }
    }
}
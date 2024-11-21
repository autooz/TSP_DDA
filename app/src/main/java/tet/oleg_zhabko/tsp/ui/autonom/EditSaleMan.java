package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class EditSaleMan extends Activity implements View.OnClickListener{

    private String pseudo_tag = EditSaleMan.class.getSimpleName() ;
    private AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
    private EditText oldName;
    private EditText oldPhone;
    private EditText oldDescr;
    private Button buttonSave;
    private Button buttonDoNotSave;
    private String newName;
    private String newPhone;
    private String newDescr;
    private String isActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_edit_sale_man);


        oldName = (EditText) findViewById(R.id.editSaleManNameCor);
        oldPhone = (EditText) findViewById(R.id.editPhoneSaleManCor);
        oldDescr = (EditText) findViewById(R.id.editDescriptionSaleManCor);

        buttonSave = (Button) findViewById(R.id.btnSaveSaleManCor);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveSaleManCor);
        buttonDoNotSave.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        ArrayList<ArrayList<String>> saleMan_nameArAr = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT salesman_name,phone_number,desc FROM salesman WHERE salesman_name='" + GlobalDatas.saleManName + "' AND org_id='"+GlobalDatas.orgId+"'");
        if (saleMan_nameArAr.isEmpty()) {
            saleMan_nameArAr = allDbController.executeQuery(this, GlobalDatas.db_name, "SELECT salesman_name,phone_number,desc FROM salesman WHERE is_active='true'");
            if (saleMan_nameArAr.isEmpty()) {
                TetDebugUtil.e(pseudo_tag, "" + pseudo_tag + " ERROR saleMan_nameArAr.isEmpty()");
            }
        }
        if (!saleMan_nameArAr.isEmpty()) {
            ArrayList<String> saleManAr = saleMan_nameArAr.get(0);
            if (!saleManAr.isEmpty()) {
                String name = saleManAr.get(0);
                String phone = saleManAr.get(1);
                String descr = saleManAr.get(2);
                if (!GlobalDatas.saleManName.equals(name)) {
                    Toast.makeText(this, " " + pseudo_tag + " ERROR: !GlobalDatas.saleMananisation.equals(name) ", Toast.LENGTH_LONG).show();
                } else {
                    oldName.setText(name);
                    oldPhone.setText(phone);
                    oldDescr.setText(descr);
                }


            }
        }
    }
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnNotSaveSaleManCor) {
                startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
                this.finish();
            } else if (id == R.id.btnSaveSaleManCor) {
                newName = oldName.getText().toString();
                newPhone = oldPhone.getText().toString();
                newDescr = oldDescr.getText().toString();
                if (TextUtils.isEmpty((CharSequence) newName)) {
                  // String msg = getResources().getString(R.string.txtAddSaleMan).toString();
                  //  Toast.makeText(EditSaleMan.this, msg, Toast.LENGTH_LONG).show();
                    return;
                }
                TetDebugUtil.e(pseudo_tag, "newName ="+newName+", newPhone="+newPhone+", newDescr="+newDescr+" ");

                AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
                String query = "UPDATE salesman SET salesman_name='" + newName +"', phone_number='" + newPhone + "', desc='"+ newDescr +"', org_id='"+GlobalDatas.orgId+"' WHERE salesman_name='"+ GlobalDatas.saleManName +"' AND org_id='"+GlobalDatas.orgId+"'";

                    try {
                        allDbController.executeQuery(this, GlobalDatas.db_name, query);
                    } catch (Exception e) {
                        e.printStackTrace();
                        TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
                    }
                    GlobalDatas.saleManName = newName;

                startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
                this.finish();
            }
        }
        @Override
        public void onBackPressed() {
            super.onBackPressed();
            startActivity(new Intent(getApplicationContext(), SaleManActivity.class));
            this.finish();
        }

}
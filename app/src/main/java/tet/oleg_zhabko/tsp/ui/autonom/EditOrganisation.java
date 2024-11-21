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

public class EditOrganisation extends Activity implements View.OnClickListener{

private String pseudo_tag = EditOrganisation.class.getSimpleName() ;
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

        setContentView(R.layout.activity_edit_organisation);


        oldName = (EditText) findViewById(R.id.editOrgNameCor);
        oldPhone = (EditText) findViewById(R.id.editPhoneOrgCor);
        oldDescr = (EditText) findViewById(R.id.editDescriptionOrgCor);

        buttonSave = (Button) findViewById(R.id.btnSaveOrgCor);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveOrgCor);
        buttonDoNotSave.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        ArrayList<ArrayList<String>> org_nameArAr = allDbController.executeQuery(this,GlobalDatas.db_name,"SELECT organisation_name,phone_number,desc FROM organisations WHERE organisation_name='"+GlobalDatas.getOrgName()+"'");
        if (org_nameArAr.isEmpty()){
            org_nameArAr = allDbController.executeQuery(this,GlobalDatas.db_name,"SELECT organisation_name,phone_number,desc FROM organisations WHERE is_active='true'");
            if(org_nameArAr.isEmpty()){
                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" ERROR org_nameArAr.isEmpty()");
            }
        }
        if (!org_nameArAr.isEmpty()){
            ArrayList<String> orgAr = org_nameArAr.get(0);
            if (!orgAr.isEmpty()){
                String name = orgAr.get(0);
                String phone = orgAr.get(1);
                String descr = orgAr.get(2);
                if (!GlobalDatas.getOrgName().equals(name)){
                    Toast.makeText(this, " "+pseudo_tag+" ERROR: !GlobalDatas.organisation.equals(name) ", Toast.LENGTH_LONG).show();
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
        if (id == R.id.btnNotSaveOrgCor) {
            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        } else if (id == R.id.btnSaveOrgCor) {
            newName = oldName.getText().toString();
            newPhone = oldPhone.getText().toString();
            newDescr = oldDescr.getText().toString();
            if (TextUtils.isEmpty((CharSequence) newName)) {
                String msg = getResources().getString(R.string.txtAddOrganisation).toString();
                Toast.makeText(EditOrganisation.this, msg, Toast.LENGTH_LONG).show();
                return;
            }


            AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
            String query = "UPDATE organisations SET organisation_name='" + newName +"', phone_number='" + newPhone + "', desc='"+ newDescr +"' WHERE organisation_name='"+ GlobalDatas.getOrgName() +"'";
                try {
                    allDbController.executeQuery(this, GlobalDatas.db_name, query);
                } catch (Exception e) {
                    e.printStackTrace();
                    TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
                }
                GlobalDatas.setOrgNameAndOrgId(newName);

            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
        this.finish();
    }
}

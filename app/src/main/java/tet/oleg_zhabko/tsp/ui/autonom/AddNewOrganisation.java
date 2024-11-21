package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddNewOrganisation extends Activity implements View.OnClickListener {
    private String pseudo_tag = AddNewOrganisation.class.getSimpleName();
    private EditText newName;
    private EditText newPhone;
    private EditText newDescr;
    private Button buttonSave;
    private Button buttonDoNotSave;
    private String orgName;
    private String phone;
    private String descr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_add_new_organisation);

        newName = (EditText) findViewById(R.id.editOrgName);
        newPhone = (EditText) findViewById(R.id.editPhoneOrg);
        newDescr = (EditText) findViewById(R.id.editDescriptionOrg);

        buttonSave = (Button) findViewById(R.id.btnSaveOrg);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveOrg);
        buttonDoNotSave.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnNotSaveOrg) {
            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        } else if (id == R.id.btnSaveOrg) {
            orgName = newName.getText().toString();
            phone = newPhone.getText().toString();
            descr = newDescr.getText().toString();
            if (TextUtils.isEmpty((CharSequence) orgName)) {
                String msg = getResources().getString(R.string.txtAddOrganisation).toString();
                Toast.makeText(AddNewOrganisation.this, msg, Toast.LENGTH_LONG).show();
                return;
            }

        TetDebugUtil.e(pseudo_tag,"Pushed Button R.id.btnSaveOrg");
        AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
        String queryForChesk = "SELECT organisation_name FROM  organisations WHERE organisation_name='" + orgName + "'";
        String query = "INSERT INTO organisations (organisation_name, phone_number, desc, is_active ) VALUES ('" + orgName + "', '" + phone + "', '" + descr + "', 'false')";

        if (allDbController.executeQuery(this, GlobalDatas.db_name, queryForChesk).isEmpty()) {
            TetDebugUtil.e(pseudo_tag," " + orgName + " Is`t DB");
            try {
                allDbController.executeQuery(this, GlobalDatas.db_name, query);
            } catch (Exception e) {
                e.printStackTrace();
                TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
            }
        } else {
            TetDebugUtil.e(pseudo_tag, "There is org " + newName + "");
        }
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

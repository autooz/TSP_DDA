package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class EditZone extends Activity implements View.OnClickListener{

    private String pseudo_tag = EditZone.class.getSimpleName() ;
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


        oldName = (EditText) findViewById(R.id.editZoneNameCor);
        oldPhone = (EditText) findViewById(R.id.editPhoneZoneCor);
        oldDescr = (EditText) findViewById(R.id.editDescriptionZone);

        buttonSave = (Button) findViewById(R.id.btnSaveZone);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveZone);
        buttonDoNotSave.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        ArrayList<ArrayList<String>> zone_nameArAr = allDbController.executeQuery(this,GlobalDatas.db_name,"SELECT zone_name,phone_number,desc FROM zones WHERE zone_name='"+GlobalDatas.zoneName+"'");
        if (zone_nameArAr.isEmpty()){
            zone_nameArAr = allDbController.executeQuery(this,GlobalDatas.db_name,"SELECT zone_name,phone_number,desc FROM zones WHERE is_active='true'");
            if(zone_nameArAr.isEmpty()){
                TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" ERROR zone_nameArAr.isEmpty()");
            }
        }
        if (!zone_nameArAr.isEmpty()){
            ArrayList<String> zoneAr = zone_nameArAr.get(0);
            if (!zoneAr.isEmpty()){
                String name = zoneAr.get(0);
                String phone = zoneAr.get(1);
                String descr = zoneAr.get(2);
                if (!GlobalDatas.zoneName.equals(name)){
                    Toast.makeText(this, " "+pseudo_tag+" ERROR: !GlobalDatas.zoneName.equals(name) ", Toast.LENGTH_LONG).show();
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
        if (id == R.id.btnNotSaveZone) {
            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        } else if (id == R.id.btnSaveZone) {
            newName = oldName.getText().toString();
            newPhone = oldPhone.getText().toString();
            newDescr = oldDescr.getText().toString();
            if (TextUtils.isEmpty((CharSequence) newName)) {
//                String msg = getResources().getString(R.string.txtAddZone).toString();
//                Toast.makeText(EditZone.this, msg, Toast.LENGTH_LONG).show();
                return;
            }


            AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
            String query = "UPDATE zones SET zone_name='" + newName +"', phone_number='" + newPhone + "', desc='"+ newDescr +"' WHERE zone_name='"+ GlobalDatas.zoneName +"'";
            try {
                allDbController.executeQuery(this, GlobalDatas.db_name, query);
            } catch (Exception e) {
                e.printStackTrace();
                TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
            }
            GlobalDatas.zoneName = newName;

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

package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddNewZone extends Activity implements View.OnClickListener {
    private String pseudo_tag = AddNewZone.class.getSimpleName();
    private EditText newName;
    private EditText newDescr;
    private Button buttonSave;
    private Button buttonDoNotSave;
    private String zoneName;
    private String descr;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");

        setContentView(R.layout.activity_add_new_zone);

        TextView orgNameForNewZone = (TextView) findViewById(R.id.orgNameForNewZone);
        orgNameForNewZone.setText(GlobalDatas.getOrgName());
        newName = (EditText) findViewById(R.id.editZoneName);
        newDescr = (EditText) findViewById(R.id.editDescriptionZone);

        buttonSave = (Button) findViewById(R.id.btnSaveZone);
        buttonSave.setOnClickListener(this);
        buttonDoNotSave = (Button) findViewById(R.id.btnNotSaveZone);
        buttonDoNotSave.setOnClickListener(this);
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnNotSaveZone) {
            startActivity(new Intent(getApplicationContext(), CreateNewRouteActivityAutonom.class));
            this.finish();
        } else if (id == R.id.btnSaveZone) {
            zoneName = newName.getText().toString();
            descr = newDescr.getText().toString();
            if (TextUtils.isEmpty((CharSequence) zoneName)) {
                return;
            }

            TetDebugUtil.e(pseudo_tag,"Pushed Button R.id.btnSaveZone");
            AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();
            String queryForChesk = "SELECT zone_name FROM  zones WHERE zone_name='" + zoneName + "' AND org_id="+GlobalDatas.orgId+"";
            String query = "INSERT INTO zones (org_id,  zone_name, desc) VALUES ("+GlobalDatas.orgId+", '" + zoneName + "', '" + descr + "')";

            if (allDbController.executeQuery(this, GlobalDatas.db_name, queryForChesk).isEmpty()) {
                TetDebugUtil.e(pseudo_tag," " + zoneName + " Is`t DB");
                try {
                    allDbController.executeQuery(this, GlobalDatas.db_name, query);
                } catch (Exception e) {
                    e.printStackTrace();
                    TetDebugUtil.e(pseudo_tag, "ERROR DB QUERY = " + query + "");
                }
            } else {
                TetDebugUtil.e(pseudo_tag, "There is zone " + newName + "");
            }
            startActivity(new Intent(getApplicationContext(), ZoneActivity.class));
            this.finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), ZoneActivity.class));
        this.finish();
    }
}
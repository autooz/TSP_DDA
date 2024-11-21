package tet.oleg_zhabko.tsp.ui.autonom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.CrashAppExceptionHandler;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AddNewPointToCurrentRoute extends Activity {
    private String pseudo_tag = AddNewPointToCurrentRoute.class.getSimpleName();
    private Spinner spinnerOrg;
    private Spinner spinnerS;
    private Spinner spinnerZ;
    private  AllDatabaseController allDbController = AllDatabaseController.getSingleControllerInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new CrashAppExceptionHandler(this));
        ThisApp.getInstance().adjastFontScale();
        TetDebugUtil.e(pseudo_tag, "!================= START " + pseudo_tag + "============");
        TetDebugUtil.e(pseudo_tag,""+pseudo_tag+" GlobalDatas.organisation = "+GlobalDatas.getOrgName()+" GlobalDatas.orgId = "+GlobalDatas.orgId+" ");

        setContentView(R.layout.activity_add_new_point);

        spinnerS = findViewById(R.id.spinnerSale);
        spinnerZ = findViewById(R.id.spinnerZone);
         /* Get active organisation list for title */
        ArrayList<ArrayList<String>> orgArAr = allDbController.executeQuery(this, GlobalDatas.db_name,"SELECT organisation_name, org_id FROM organisations WHERE is_active='true'");
        if (orgArAr.isEmpty()){
            orgArAr = allDbController.executeQuery(this, GlobalDatas.db_name,"SELECT organisation_name, org_id FROM organisations");
        }
        if (orgArAr.isEmpty()){
            startActivity(new Intent(getApplicationContext(),AddNewOrganisation.class));
            this.finish();
        }
        /* ------END----------*/

        /* Get All SaleMan List*/
        ArrayList<ArrayList<String>> saleArAr = allDbController.executeQuery(this, GlobalDatas.db_name,"SELECT salesman_name, salesman_id FROM salesman WHERE org_id="+GlobalDatas.orgId+"");
        if (saleArAr.isEmpty()){
            startActivity( new Intent(getApplicationContext(),AddNewSaleMan.class));
            this.finish();
        }
        /* ------END----------*/

        /* Get all Zone List */
        ArrayList<ArrayList<String>> zoneArAr = allDbController.executeQuery(this, GlobalDatas.db_name,"SELECT zone_name, zone_id FROM salesman WHERE org_id="+GlobalDatas.orgId+"");
        if (zoneArAr.isEmpty()){
            startActivity(new Intent(getApplicationContext(), AddNewZone.class));
            this.finish();
        }
        /* ------END----------*/


        /* Make adapters for SaleMan and Zone */
        ArrayList<String> saleManList = spinList(saleArAr);
        ArrayAdapter<String> adapterS = new ArrayAdapter(this, android.R.layout.simple_spinner_item, saleManList);
        // Определяем разметку для использования при выборе элемента
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerS.setAdapter(adapterS);

        ArrayList<String> zoneManList = spinList(zoneArAr);
        ArrayAdapter<String> adapterZ = new ArrayAdapter(this, android.R.layout.simple_spinner_item, zoneManList);
        // Определяем разметку для использования при выборе элемента
        adapterZ.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerZ.setAdapter(adapterS);


    }

    private ArrayList<String> spinList(ArrayList<ArrayList<String>> ArAr) {
        ArrayList<String> items = new ArrayList<String>();
        if (ArAr.isEmpty()){
            return  items;
        }
        int size = ArAr.size();
        for (int i = 0; i < size; i++){
            ArrayList<String> Ar = ArAr.get(i);
            TetDebugUtil.e(pseudo_tag,"itemsOrg.add("+Ar+")");
            if (Ar.isEmpty()){
                return  items;
            }
            String name = Ar.get(0);
            String id = Ar.get(1);
            items.add(name);
        }
        return items;
        }

}
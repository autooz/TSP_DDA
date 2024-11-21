package tet.oleg_zhabko.tsp.ui.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;
import tet.oleg_zhabko.tsp.ui.route.CreateTableForCurrentRoute;
import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.route.OnRouteMainActivity;
import tet.oleg_zhabko.tsp.ui.utils.AllertDialogCallbackInterface;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AdapterChoiseRoutes extends BaseAdapter implements AllertDialogCallbackInterface<String> {

    private static final String pseudo_tag = AdapterChoiseRoutes.class.getSimpleName();
    AllDatabaseController allDatabaseController = AllDatabaseController.getSingleControllerInstance();
    private final ArrayList<ArrayList<String>> dataList;
    //    private final ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();  // Список для сохранения отмеченных элементов

    private final LayoutInflater inflater;
    private final Activity activity;
    private Context context;
    private ViewHolder holder;
    private int itemPosition;
    private final String variable = " point_id, organisation_name, zone, folder_color, sales_name," +
            " phone_number, point_owner, latitude, longitude, landmarks, description, phone_ambar," +
            " description_office, work_from_h, work_from_m, work_to_h, work_to_m, strike_from_h," +
            " strike_from_m, strike_to_h, strike_to_m, color, pin_icon_code, color1";

    private static class ViewHolder {
        TextView pointIdTextView;
        TextView point_fiel1_TextView;
        TextView point_fiel2_TextView;
        Button buttonChoise;
    }


    public AdapterChoiseRoutes(Activity activity, ArrayList<ArrayList<String>> dataList) {
        holder = null;
        // boolean[] checkedStates = null;
        this.dataList = dataList;
        this.activity = activity;
        context = activity.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        itemPosition = position;


        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_routes_list_layout, parent, false);
            holder = new AdapterChoiseRoutes.ViewHolder();

            holder.pointIdTextView = convertView.findViewById(R.id.route_id);
            holder.point_fiel1_TextView = convertView.findViewById(R.id.route_fiel1);
            holder.point_fiel2_TextView = convertView.findViewById(R.id.route_fiel2);
            holder.buttonChoise = convertView.findViewById(R.id.buttonChoiseRoute);

            convertView.setTag(holder);
        } else {
            holder = (AdapterChoiseRoutes.ViewHolder) convertView.getTag();
        }

        ArrayList<String> currentItem = dataList.get(position);


        holder.pointIdTextView.setText(currentItem.get(0));  // route_id
        holder.point_fiel1_TextView.setText(currentItem.get(1));  // route_name
        holder.point_fiel2_TextView.setText(currentItem.get(2));  // pointsByComa

        holder.buttonChoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> currentItem = dataList.get(position);
                String id = currentItem.get(0);
                String route = currentItem.get(1);
                String points = currentItem.get(2);
                TetDebugUtil.e(pseudo_tag, " id =[" + id + "] route =[" + route + "] points =[" + points + "]");
                preparePointList(points);
                Intent intent =new Intent(context, OnRouteMainActivity.class);
                intent.putExtra("org", GlobalDatas.getOrgName());
                intent.putExtra("id", id);
                intent.putExtra("route", route);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        return convertView;
    }

    private void preparePointList(String points) {

        TetDebugUtil.d(pseudo_tag, "points = ] " + points + " [");
        Context context = activity.getApplicationContext();

        CreateTableForCurrentRoute.createByStringWithCommas(context, points);

//
//        StringTokenizer tokenizer = new StringTokenizer(points, ",");
//        int size = tokenizer.countTokens();
//        for (int i = 0; i < size; i++) {
//            String id = tokenizer.nextToken();
//            ArrayList<ArrayList<String>> aRaR = null;
//            try {
//                aRaR = allDatabaseController.executeQuery(context, GlobalDatas.db_name, "SELECT * FROM owner_points WHERE point_id=" + id + ";");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (aRaR.isEmpty()) {
//                return;
//            }
//            ArrayList<String> valList = aRaR.get(0);
//            if (!makeAddToDbValueString(valList)) {
//                TetDebugUtil.e(pseudo_tag, "ERROR makeAddToDbValueString(valList);");
//                return;
//            }
//        }
    }

    private boolean makeAddToDbValueString(ArrayList<String> valList) {
        if (valList.isEmpty()) {
            return false;
        }
        int sizeColum = valList.size();
        String val = new String();
        for (int i = 0; i < sizeColum; i++) {
            if (i == sizeColum - 1) {
                val = val + "'"+ valList.get(i) + "'";
            } else {
                val = val + "'" + valList.get(i)+ "',";
            }
        }
        TetDebugUtil.e(pseudo_tag, "makeAddToDbValueString {" + val + "}");
        try {
            allDatabaseController.executeQuery(context,GlobalDatas.db_name,"INSERT INTO current_route ("+variable+") VALUES ("+val+")");
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void allertDialogCallback(boolean ret) {
        TetDebugUtil.e(pseudo_tag, "From CALBACK recieved {" + ret + "}");
        if (ret == false) {
            //  holder.checkBox.setChecked(false);
            // GlobalDatas.pointChecked.add(currentItem);
        } else {
            ArrayList<String> item = dataList.get(itemPosition);
            GlobalDatas.pointChecked.add(item);
        }

    }


    public ArrayList<ArrayList<String>> getCheckedItems() {
        return GlobalDatas.pointChecked;
    }
}




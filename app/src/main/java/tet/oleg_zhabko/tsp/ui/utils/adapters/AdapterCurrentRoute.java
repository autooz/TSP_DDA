package tet.oleg_zhabko.tsp.ui.utils.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ThisApp;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.points_and_maps.ActivityPointInfo;
import tet.oleg_zhabko.tsp.ui.utils.CheckIsItemDubbed;
import tet.tetlibrarymodules.alldbcontroller.AllDatabaseController;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;
import tet.oleg_zhabko.tsp.ui.utils.mapsTypeSeparator;

public class AdapterCurrentRoute extends BaseAdapter {
    private static final String pseudo_tag = AdapterCurrentRoute.class.getSimpleName();
    private AllDatabaseController allDatabaseController = AllDatabaseController.getSingleControllerInstance();
    private final ArrayList<ArrayList<String>> dataList;
    //    private final ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();  // Список для сохранения отмеченных элементов
    private final LayoutInflater inflater;
    private final Activity activity;
    private Context context;
    private ViewHolder holder;
    private int itemPosition;

    private static class ViewHolder {
        TextView pointIdTextView;
        TextView point_fiel1_TextView;
        TextView point_fiel2_TextView;
        Button buttonGo;
    }

    public AdapterCurrentRoute(Activity activity, ArrayList<ArrayList<String>> dataList) {
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
            convertView = inflater.inflate(R.layout.item_current_route_goahead, parent, false);
            holder = new ViewHolder();

            holder.pointIdTextView = convertView.findViewById(R.id.tv_idRC);
            holder.point_fiel1_TextView = convertView.findViewById(R.id.point_currentZ);
            holder.point_fiel2_TextView = convertView.findViewById(R.id.point_currentOw);
            holder.buttonGo = (Button) convertView.findViewById(R.id.btnGoAhead);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ArrayList<String> currentItem = dataList.get(position);


        holder.pointIdTextView.setText(currentItem.get(0));  // point_id
        holder.point_fiel1_TextView.setText(currentItem.get(1));  // sales_name
        holder.point_fiel2_TextView.setText(currentItem.get(2));  // zone

        holder.buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pointId = currentItem.get(0);
                    SharedPreferences sharedPrefs = ThisApp.getSharedPreferenceManager();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString("directPoint", pointId);
                editor.apply();
                Intent intent = mapsTypeSeparator.getPreferedMaps();
                context.startActivity(intent);

                }
        });

        return convertView;
    }
}
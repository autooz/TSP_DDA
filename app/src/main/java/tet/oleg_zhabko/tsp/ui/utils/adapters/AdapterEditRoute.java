package tet.oleg_zhabko.tsp.ui.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.datas.GlobalDatas;
import tet.oleg_zhabko.tsp.ui.utils.AllertDialogCallbackInterface;
import tet.oleg_zhabko.tsp.ui.utils.AllertOneAndTwoAndThreeButton;
import tet.oleg_zhabko.tsp.ui.utils.CheckIsItemDubbed;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.ShowAllInArrayList;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class AdapterEditRoute extends BaseAdapter implements AllertDialogCallbackInterface<String> {

    private static final String pseudo_tag = AdapterEditRoute.class.getSimpleName();
    private final ArrayList<ArrayList<String>> dataList;
    //    private final ArrayList<ArrayList<String>> pointChecked = new ArrayList<>();  // Список для сохранения отмеченных элементов
    private boolean[] checkedStates;  // Массив состояний чекбоксов
    private final LayoutInflater inflater;
    private final Activity activity;
    private Context context;
    private AdapterEditRoute.ViewHolder holder;
    private int itemPosition;

    private static class ViewHolder {
        TextView pointIdTextView;
        TextView point_fiel1_TextView;
        TextView point_fiel2_TextView;
        //CheckBox checkBox;
        ImageButton buttonUp;
        ImageButton buttonDown;
        Button buttonDel;
    }


    public AdapterEditRoute(Activity activity, ArrayList<ArrayList<String>> dataList) {
        holder = null;
        checkedStates = null;
        this.dataList = dataList;
        checkedStates = new boolean[dataList.size()];  // Инициализация массива для состояний чекбоксов
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
            convertView = inflater.inflate(R.layout.item_point_root_list, parent, false);
            holder = new AdapterEditRoute.ViewHolder();

            holder.pointIdTextView = convertView.findViewById(R.id.point_idR);
            holder.point_fiel1_TextView = convertView.findViewById(R.id.point_fiel1R);
            holder.point_fiel2_TextView = convertView.findViewById(R.id.point_fiel2R);
            holder.buttonUp = convertView.findViewById(R.id.btnUp);
            holder.buttonDown = convertView.findViewById(R.id.btnDown);
            holder.buttonDel = convertView.findViewById(R.id.btnDel);

            convertView.setTag(holder);
        } else {
            holder = (AdapterEditRoute.ViewHolder) convertView.getTag();
        }

        ArrayList<String> currentItem = dataList.get(position);


        holder.pointIdTextView.setText(currentItem.get(0));  // point_id
        holder.point_fiel1_TextView.setText(currentItem.get(1));  // sales_name
        holder.point_fiel2_TextView.setText(currentItem.get(2));  // zone
        holder.buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int newPosition = position -1;
                if (newPosition < 0){
                    return;
                }
                ArrayList<String> previosItem = dataList.get(newPosition);
                ArrayList<String> currentItem = dataList.get(position);
                dataList.remove(previosItem);
                dataList.remove(currentItem);
                dataList.add(newPosition,currentItem);
                dataList.add(position,previosItem);
                notifyDataSetChanged();
            }
        });
        holder.buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int nextPosition = position +1;

                if (nextPosition==getCount()){
                    return;
                }
                ArrayList<String> nextItem = dataList.get(nextPosition);
                ArrayList<String> currentItem = dataList.get(position);
                dataList.remove(nextItem);
                dataList.remove(currentItem);
                dataList.add(position,nextItem);
                dataList.add(nextPosition,currentItem);

                notifyDataSetChanged();
            }
        });

        holder.buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataList.remove(position); // remove the item from the data list
                GlobalDatas.pointChecked = dataList;
                notifyDataSetChanged();
            }
        });

//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                checkedStates[position] = isChecked;  // Сохраняем состояние чекбокса
//
//                // Если чекбокс отмечен - добавляем элемент в pointChecked, иначе удаляем
//                if (isChecked) {
//                    if (CheckIsItemDubbed.checkIsDubbed(GlobalDatas.pointChecked, currentItem)) {
//                        String title = context.getResources().getString(R.string.worning).toString();
//                        String message = context.getResources().getString(R.string.allert_double_point);
//                        String initialiserName = activity.getClass().getSimpleName();
//                        AlertDialog allert = new AllertOneAndTwoAndThreeButton().createTwoButtonsAlertDialog(activity, title, message, null, 1, AdapterEditRoute.this);
//                        allert.show();
//                    } else {
//                        TetDebugUtil.e(pseudo_tag, "pointChecked.add(currentItem)=[" + currentItem + "]");
//                        holder.checkBox.setChecked(checkedStates[position]);
//                        GlobalDatas.pointChecked.add(currentItem);
//                    }
//                } else {
//                    TetDebugUtil.e(pseudo_tag, "pointChecked.remove(currentItem)=[" + currentItem + "]");
//                    GlobalDatas.pointChecked.remove(currentItem);
//                }
//                new ShowAllInArrayList(pseudo_tag, GlobalDatas.pointChecked);
//
//            }
//        });

        return convertView;
    }


    @Override
    public void allertDialogCallback(boolean ret) {
        TetDebugUtil.e(pseudo_tag,"From CALBACK recieved {"+ret+"}");
        if (ret == false){
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


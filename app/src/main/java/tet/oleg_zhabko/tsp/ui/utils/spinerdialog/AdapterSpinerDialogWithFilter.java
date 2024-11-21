package tet.oleg_zhabko.tsp.ui.utils.spinerdialog;// Created: by PC BEST, OS Linux

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone/WhataApp/Viber/Telegram: +38(067) 411-98-75
//              Berdichev, Ukraine
//
public class AdapterSpinerDialogWithFilter<S> extends ArrayAdapter {

    private List<String> items = null;
    private ArrayList<String> arraylist;

    public AdapterSpinerDialogWithFilter(Activity context, int items_view, ArrayList<String> items) {
        super(context,items_view,items);
        this.items = items;
        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(items);
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    // Filter Class
    public void getContainsFilter(String charText) {
        //charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(arraylist);
        }
        else
        {
            for (String item : arraylist)
            {
//                if (item.toLowerCase(Locale.getDefault()).contains(charText))
                if (item.contains(charText))
                {
                    items.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
package tet.oleg_zhabko.tsp.ui.utils;
// Created: by PC BEST, OS Linux
// Copyright:  Copyright (c) 2008-2024 Best LLC & Oleg Zhabko. All rights reserved.
//License: ASK LICENSE TERMS AND CONDITIONS!
//             Oleg Zhabko, mailto:olegzhabko@gmail.com
//             phone +380 (67) 411-98-75
//              Berdichev, Ukraine

//

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import tet.oleg_zhabko.tsp.R;

public class SearchActivityExample extends Activity {

    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_route_autonom);

        //  searchEditText = findViewById(R.id.searchRoudEditText);

        // Добавляем слушателя текстового поля для отслеживания изменений в тексте
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Этот метод вызывается до изменения текста
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Этот метод вызывается при изменении текста
                String searchText = s.toString();
                performSearch(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Этот метод вызывается после изменения текста
            }
        });
    }

    private void performSearch(String searchText) {
        // Здесь вы можете реализовать свой механизм поиска
        // Например, вы можете использовать базу данных или список для поиска совпадений
        // После завершения поиска, вы можете отобразить результаты или выполнить другие действия
        Toast.makeText(SearchActivityExample.this, "Выполняется поиск по запросу: " + searchText, Toast.LENGTH_SHORT).show();
    }
}

package tet.oleg_zhabko.tsp.ui.utils;

import android.widget.EditText;

public class isFieldEmptyOrWrited {


    public static boolean isEditTextEmpty(EditText editText) {
        String string = editText.getText().toString();
        if (string.matches("")) {
            return true;
        }
        return false;
    }
}

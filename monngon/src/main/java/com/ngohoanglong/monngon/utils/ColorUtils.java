package com.ngohoanglong.monngon.utils;

import com.ngohoanglong.monngon.MainApplication;
import com.ngohoanglong.monngon.R;

/**
 * Created by Long on 11/24/2016.
 */

public class ColorUtils {
    static int[] catalogueColors = MainApplication.mContext.getResources().getIntArray(R.array.catalogue_colors);
    static String[] catalogues = MainApplication.mContext.getResources().getStringArray(R.array.catalogues);

    synchronized  public static int getColorByCatalogue(int position) {
        return catalogueColors[position];
    }

    public static int getColorByCatalogue(String catalogue) {
        int index = 0;
        for (int i = 0; i < catalogues.length; i++) {
            if (catalogues[i].equals(catalogue)){
                index=i;
                break;
            }
        }
        return catalogueColors[index];
    }
}

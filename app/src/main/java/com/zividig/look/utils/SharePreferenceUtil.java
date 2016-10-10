package com.zividig.look.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zividig.look.R;

/**
 * Created by adolph
 * on 2016-10-10.
 */

public class SharePreferenceUtil {

    public static int getNavigationItem(Context context){
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        return spf.getInt(context.getString(R.string.navigation_item),-1);
    }

    public static void putNavigationItem(Context context,int t){
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
        spf.edit().putInt(context.getString(R.string.navigation_item),t).apply();
    }
}

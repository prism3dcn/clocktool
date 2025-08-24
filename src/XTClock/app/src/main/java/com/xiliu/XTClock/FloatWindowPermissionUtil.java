package com.xiliu.XTClock;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

public class FloatWindowPermissionUtil {
    public static boolean hasPermission(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return Settings.canDrawOverlays(context);
        }else{
            return  true;
        }

    }

    public  static  void requestPermission(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+context.getPackageName()));
            context.startActivity(intent);
        }

    }
}

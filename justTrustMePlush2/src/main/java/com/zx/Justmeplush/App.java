package com.zx.Justmeplush;

import android.app.Application;
import android.content.Context;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import okhttp3.OkHttpClient;

/**
 * Created by lyh on 2019/4/3.
 */

public class App extends Application {
    private static Context mContext;




    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        doLog();
    }

    public static void doLog(){
        try {
            XposedHelpers.findAndHookMethod(OkHttpClient.Builder.class, "build", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    OKhttpAOp.doIgnore((OkHttpClient.Builder) param.thisObject);
                }
            });
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }


    public static Context getContext(){
        return mContext ;
    }
}

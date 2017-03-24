package com.plugin.cl.plugin_demo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2017/3/24.
 */

public class PluginManager {

    public static final int PLUGIN_LOAD_SUCCESS = 1;
    //缓存全路径
    private String mCachePath;
    private Context mContext;

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    public PluginManager(Context context) {
        mContext = context.getApplicationContext();
        mCachePath = mContext.getCacheDir() + File.separator + SkinConst.SKIN_FILE_NAME;
    }

    public String getCachePath(){
        return mCachePath;
    }

    public int getPluginResourceId(String name, String defType){
        return getPluginResources().getIdentifier(name,defType,getPackageName(mCachePath));
    }

    public Resources getPluginResources(){
        return PluginResources.getPluginResource(mCachePath,mContext.getResources());
    }

    /**
     * 异步加载插件
     */
    public void syncPlugin(final String pluginFileName , final Handler handler) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                copyAssetsToCache(pluginFileName);
                handler.sendEmptyMessage(PLUGIN_LOAD_SUCCESS);
            }
        });
    }

    /**
     * 拷贝asset下文件到cache下
     * @param pluginFileName
     */
    private void copyAssetsToCache(String pluginFileName) {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = mContext.getResources().getAssets().open(pluginFileName);
            if (is == null) {
                return;
            }
            os = new FileOutputStream(mCachePath);
            byte[] buffer = new byte[1024 * 4];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
                os.flush();
            }
        } catch (IOException e) {

        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getPackageName(String apkPath){
        if(!TextUtils.isEmpty(mCachePath)){
            PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
            return packageInfo.packageName;
        }
        return null;
    }



}

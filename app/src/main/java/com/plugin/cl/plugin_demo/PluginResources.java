package com.plugin.cl.plugin_demo;

import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created on 2017/3/24.
 */

public class PluginResources extends Resources {
    /**
     * @param assets
     * @param metrics
     * @param config
     * @deprecated
     */
    public PluginResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
        super(assets, metrics, config);
    }

    public static Resources getPluginResource(String apkPath,Resources resources){
        try {
            if(TextUtils.isEmpty(apkPath)){
                return null;
            }
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = assetManager.getClass().getMethod("addAssetPath",String.class);
            method.invoke(assetManager,apkPath);
            Resources pluginResources = new PluginResources(assetManager,resources.getDisplayMetrics(),resources.getConfiguration());
            return pluginResources;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}

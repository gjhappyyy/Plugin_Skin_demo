package com.plugin.cl.plugin_demo;

import android.os.Build;
import android.os.Handler;
import android.os.Message;

/**
 * Created on 2017/3/24.
 */

public class MainPresenter implements PluginData.IPresenter{

    private static PluginData.IPluginView mView;
    private static PluginManager mPluginManager;

    private MyHandler myHandler = new MyHandler();

    public MainPresenter(PluginData.IPluginView view){
        mView = view;
        mPluginManager = new PluginManager(view.getContext());
        view.setPresenter(this);
    }

    public void loadPlugin(){
        mView.showLoading();
        mPluginManager.syncPlugin(SkinConst.SKIN_FILE_NAME,myHandler);
    }

    @Override
    public void start() {

    }


    static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            mView.dissmissLoading();

            int drawableId = mPluginManager.getPluginResourceId(SkinConst.SKIN_DRAWABLE,"drawable");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mView.updatePluginDrawable(mPluginManager.getPluginResources().getDrawable(drawableId,null));
            }

            int color = mPluginManager.getPluginResourceId(SkinConst.SKIN_COLOR,"color");
            mView.updatePluginColor(mPluginManager.getPluginResources().getColor(color));

            int animDrawableId = mPluginManager.getPluginResourceId(SkinConst.SKIN_ANIM,"drawable");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mView.updatePluginAnimDrawable(mPluginManager.getPluginResources().getDrawable(animDrawableId,null));
            }

        }
    }
}

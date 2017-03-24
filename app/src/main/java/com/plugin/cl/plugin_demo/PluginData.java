package com.plugin.cl.plugin_demo;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created on 2017/3/24.
 */

public class PluginData {
    interface IPluginView extends IBaseView<IPresenter> {
        void showLoading();
        void dissmissLoading();
        Context getContext();
        void updatePluginDrawable(Drawable drawable);
        void updatePluginColor(int color);
        void updatePluginAnimDrawable(Drawable drawable);
    }

    interface IPresenter extends IBasePresenter{
        void loadPlugin();
    }
}

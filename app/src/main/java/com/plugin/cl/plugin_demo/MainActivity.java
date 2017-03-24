package com.plugin.cl.plugin_demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PluginData.IPluginView {

    private ImageView mIconIV;
    private ImageView mAnimationIV;
    private TextView mNickTV;
    private Button mChangeSkinBt;
    private ProgressDialog mProgressDialog;

    private PluginData.IPresenter mainPresenter;


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mainPresenter.loadPlugin();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        new MainPresenter(this);
        mIconIV = (ImageView) findViewById(R.id.icon_iv);
        mAnimationIV = (ImageView) findViewById(R.id.animation_iv);
        mNickTV = (TextView) findViewById(R.id.nick_tv);
        mChangeSkinBt = (Button) findViewById(R.id.change_skin_bt);
        mChangeSkinBt.setOnClickListener(mOnClickListener);

    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "更新插件", "正在加载插件");
        }
    }

    @Override
    public void dissmissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updatePluginDrawable(Drawable drawable) {
        mIconIV.setImageDrawable(drawable);
    }

    @Override
    public void updatePluginColor(int color) {
        mNickTV.setTextColor(color);
    }

    @Override
    public void updatePluginAnimDrawable(Drawable drawable) {
        mAnimationIV.setImageDrawable(drawable);
        Drawable anmiDrawable = (Drawable)mAnimationIV.getDrawable();
        if(anmiDrawable != null && anmiDrawable instanceof AnimationDrawable){
            AnimationDrawable animation = ((AnimationDrawable)anmiDrawable);
            if(animation.isRunning()){
                animation.stop();
            }else{
                animation.stop();
                animation.start();
            }
        }

    }

    @Override
    public void setPresenter(PluginData.IPresenter presenter) {
        mainPresenter = presenter;
    }

}

package com.plugin.cl.plugin_demo;

/**
 * Created by gaocaili on 2017/3/24.
 */

import android.view.animation.Interpolator;

/**
 * 内圈动画的插值器，该插值器根据音量产生变化
 *
 * @author yuqiang.xia
 * @since 2014-7-20
 */
public  class InnerAniInterpolator implements Interpolator {

    /**
     * 缩减周期比值
     */
    private static final float TIME_SHRINK = 0.8f;
    /**
     * 普通振动的波谷
     */
    private static final float Amplitude_LOW = -0.4f;
    /**
     * 动画结束时的波谷
     */
    private static final float Amplitude_END = -2.0f;

    /**
     * 上次输入
     */
    private float mLastInput;
    /**
     * 上次输出
     */
    private float mLastOutput;

    /**
     * 时间周期状态，一个周期是1.0+TIME_SHRINK，振幅从-0.1到高峰再回到-0.1
     */
    private float mTimeCicle;
    /**
     * 当前周期需要达到的波峰
     */
    private float mHighAmplitude;
    /**
     * 当前周期需要达到的波谷
     */
    private float mLowAmplitude;

    public InnerAniInterpolator() {
        reset();
    }

    public void reset() {
        mLastInput = 0.0f;
        mLastOutput = -1.0f;
        mTimeCicle = 0.0f;
        mHighAmplitude = Amplitude_LOW;
        mLowAmplitude = Amplitude_LOW;
    }

    public void setVolume(int volume) {
        if (volume == 0) {
            return;
        }

        float amplitude = (float) Math.min(volume / 30f, 0.7);
        if (amplitude < mLastOutput) {
            return;
        }

        // 改变过小、频率过快，会导致震动频率大，
        if (Math.abs(amplitude - mHighAmplitude) < 0.05) {
            return;
        }

        mHighAmplitude = amplitude;

        // 依据当前振幅位置，计算当前周期进行范围
        mTimeCicle = (mLastOutput - Amplitude_LOW)
                / (mHighAmplitude - Amplitude_LOW);
    }

    @Override
    public float getInterpolation(float input) {
        // 计算我们自己的时序
        float delta;
        if (input >= mLastInput) {
            delta = input - mLastInput;
        } else {
            delta = input + 1.0f - mLastInput;
        }

        mTimeCicle += delta;
        if (mTimeCicle > (1.0f + TIME_SHRINK)) {
            mTimeCicle = mTimeCicle - 1.0f - TIME_SHRINK;
            mHighAmplitude = Amplitude_LOW;

            if (mLowAmplitude == Amplitude_END) {
                return mLowAmplitude;
            }
        }

        if (mTimeCicle <= 1.0f) {
            mLastOutput = (mHighAmplitude - mLowAmplitude) * mTimeCicle
                    + mLowAmplitude;
        } else {
            mLastOutput = (mLowAmplitude - mHighAmplitude) / TIME_SHRINK
                    * (mTimeCicle - 1.0f) + mHighAmplitude;
        }

        mLastInput = input;
        return mLastOutput;
    }

}

package com.meizu.testdevVideo.library;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 小数据存储辅助函数
 * Created by maxueming on 2016/8/30.
 */
public class SharedPreferencesHelper {

    private Context mContext;
    private SharedPreferences mSharedPreference1;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context, String tableName){
        this.mContext = context;
        mSharedPreference1 = mContext.getSharedPreferences(tableName,
                Context.MODE_MULTI_PROCESS);
        editor = mSharedPreference1.edit();
    }

    /**
     * 保存字符串数据
     * @param key：键值
     * @param value：数值
     * @return 结果
     */
    public boolean writeStringData(String key, String value){
        editor.remove(key);
        editor.putString(key, value);
        return editor.commit();
    }
    /**
     * 保存整型数据
     * @param key：键值
     * @param value：数值
     * @return 结果
     */
    public boolean writeIntData(String key, int value){
        editor.remove(key);
        editor.putInt(key, value);
        return editor.commit();
    }

    public boolean writeLongData(String key, long value){
        editor.remove(key);
        editor.putLong(key, value);
        return editor.commit();
    }

    public boolean writeFloatData(String key, float value){
        editor.remove(key);
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * 保存布尔型数据
     * @param key：键值
     * @param value：数值
     * @return 结果
     */
    public boolean writeBooleanData(String key, boolean value){
        editor.remove(key);
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * 读取字符串数据
     * @param key：键值
     * @return 读取值
     */
    public String readStringData(String key){
        return mSharedPreference1.getString(key, null);
    }

    /**
     * 读取整型数据
     * @param key：键值
     * @return 读取值
     */
    public int readIntData(String key){
        return mSharedPreference1.getInt(key, 0);
    }

    public long readLongData(String key){
        return mSharedPreference1.getLong(key, 0);
    }

    public float readFloatData(String key){
        return mSharedPreference1.getFloat(key, 0);
    }

    /**
     * 读取布尔型数据
     * @param key：键值
     * @return 读取值
     */
    public boolean readBooleanData(String key){
        return mSharedPreference1.getBoolean(key, false);
    }

    /**
     * 读取布尔型数据，默认初始值为true
     * @param key：键值
     * @return 读取值
     */
    public boolean readBooleanDataDefTrue(String key){
        return mSharedPreference1.getBoolean(key, true);
    }


}

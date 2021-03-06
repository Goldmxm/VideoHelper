package com.meizu.testdevVideo.library;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.meizu.testdevVideo.util.PublicMethod;

/**
 * Created by maxueming on 2017/4/28.
 */
public class AlarmSetting {
    public static AlarmSetting mInstance;
    private AlarmManager alarmManager = null;

    public AlarmSetting(){

    }

    public static synchronized AlarmSetting getInstance() {
        if(mInstance == null){
            mInstance = new AlarmSetting();
        }
        return mInstance;
    }

    /**
     * 相同action的设置，前次设置还未闹钟的会被取消，以最新为准
     * @param context 上下文
     * @param action 广播动作
     * @param startTime 开始闹钟时间
     */
    public void setOnceAlarm(Context context, String action, long startTime){
        alarmManager = (null == alarmManager)? (AlarmManager) context.getSystemService(Context.ALARM_SERVICE) : alarmManager;
        Intent alarmIntent = new Intent(action);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // 适配版本，Android 4.3以后的用setExact
        if(PublicMethod.isKitKatOrLater()){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, operation);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, operation);
        }
    }

    /**
     * 设置重复闹钟
     * @param context 上下文
     * @param action 广播动作
     * @param startTime 开始闹钟时间
     * @param lengthMills 下次闹钟时间间隔
     */
    public void setRepeatAlarm(Context context, String action, long startTime, long lengthMills){
        alarmManager = (null == alarmManager)? (AlarmManager) context.getSystemService(Context.ALARM_SERVICE) : alarmManager;
        Intent alarmIntent = new Intent(action);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        // 适配版本，Android 4.3以后的用setWindow，参数2是开始时间、参数3是允许系统延迟的时间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC, startTime, lengthMills, operation);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC, startTime, lengthMills, operation);
        }
    }

    /**
     * 设置重复闹钟
     * @param context 上下文
     * @param action 广播动作
     * @param startTime 开始闹钟时间
     */
    public void setRepeatAlarm(Context context, String action, long startTime){
        alarmManager = (null == alarmManager)? (AlarmManager) context.getSystemService(Context.ALARM_SERVICE) : alarmManager;
        Intent alarmIntent = new Intent(action);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        if(PublicMethod.isKitKatOrLater()){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, startTime, operation);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP, startTime, operation);
        }
    }

    /**
     * 适配版本，Android 4.3以后需要在接收到广播后再次设置setwindow
     */
    public void setRepeatWindowAlarm(Context context, String action, long startTime, long lengthMills){
        alarmManager = (null == alarmManager)? (AlarmManager) context.getSystemService(Context.ALARM_SERVICE) : alarmManager;
        Intent alarmIntent = new Intent(action);
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setWindow(AlarmManager.RTC, startTime, lengthMills, operation);
        }
    }

    /**
     * 取消闹钟
     * @param context 上下文
     * @param action 广播动作
     */
    public void canalAlarm(Context context, String action) {
        alarmManager = (null == alarmManager)? (AlarmManager) context.getSystemService(Context.ALARM_SERVICE) : alarmManager;
        Intent alarmIntent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pi);
    }
}

package com.meizu.testdevVideo.util.download;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.meizu.testdevVideo.interports.PerformsJarDownloadCallBack;

import java.lang.reflect.Field;

/**
 * Created by maxueming on 2016/10/22.
 */
public class DownloadReceiver extends BroadcastReceiver {

    private DownloadManager downloadManager;
    private String TAG = DownloadReceiver.class.getSimpleName();
    private DownloadIdCallback mDownloadIdCallback;
    public static DownloadReceiver mInstance;

    public static DownloadReceiver getInstance(){
        if(mInstance == null){
            mInstance = new DownloadReceiver();
        }
        return mInstance;
    }


    public void setOnDownloadListener(DownloadIdCallback downloadIdCallback){
        this.mDownloadIdCallback = downloadIdCallback;
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            Query query = new Query();
            query.setFilterById(id);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            // 利用反射修改UNDERLYING_COLUMNS中的值，解决部分手机系统存在下述BUG，去除allow_write字段
            // android.database.sqlite.SQLiteException: no such column: allow_write (code 1)
            try {
                Field fields= DownloadManager.class.getDeclaredField("UNDERLYING_COLUMNS");
                fields.setAccessible(true);
                try {
                    fields.set("UNDERLYING_COLUMNS", new String[] {"_id",
                            "_data AS local_filename", "mediaprovider_uri", "destination",
                            "title", "description", "uri", "status", "hint", "mimetype AS media_type",
                            "total_bytes AS total_size", "lastmod AS last_modified_timestamp",
                            "current_bytes AS bytes_so_far", "'placeholder' AS local_uri", "'placeholder' AS reason"});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }


            Cursor cursor = downloadManager.query(query);
            int columnCount = cursor.getColumnCount();
            String path = null;
            String _id = null;
            String columnName;
            String string;
            boolean isGetId = false;
            while(!isGetId && cursor.moveToNext()) {
                for (int j = 0; j < columnCount; j++) {
                    columnName = cursor.getColumnName(j);
                    string = cursor.getString(j);
//                    if(columnName.equals("local_uri")) {
//                        Log.d(TAG, "日志1" + columnName+": "+ string);
//                    }

                    if(string != null) {
                        Log.d(TAG, "日志2" + columnName + ": "+ string);
                        if(columnName.equals("_id")){
                            _id = string;
                            path = cursor.getString(j + 1);
                        }

                        if(columnName.equals("hint")){
                            if(null == path){
                                path = string.replace("file://", "");
                            }
                        }

                        if(null != _id && null != path){
                            if(this.mDownloadIdCallback != null){
                                this.mDownloadIdCallback.onDownloadListener(_id, path);
                            }

                            isGetId = true;
                            break;
                        }
                    }else {
                        Log.d(TAG, "日志3" + columnName + ": null");
                    }
                }
            }


            cursor.close();
//            // 如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理
//            if(path.startsWith("content:")) {
//                cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
//                columnCount = cursor.getColumnCount();
//                while (cursor.moveToNext()) {
//                    for (int j = 0; j < columnCount; j++) {
//                        String columnName = cursor.getColumnName(j);
//                        String string = cursor.getString(j);
//                        if (string != null) {
//                            Log.d(TAG, columnName + ": " + string);
//                        } else {
//                            Log.d(TAG, columnName + ": null");
//                        }
//                    }
//                }
//                cursor.close();
//            }
        }else if(action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
        }
    }
}

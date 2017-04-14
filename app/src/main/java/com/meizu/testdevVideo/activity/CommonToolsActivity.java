package com.meizu.testdevVideo.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.meizu.testdevVideo.R;
import com.meizu.testdevVideo.fragment.AppListFragment;
import com.meizu.testdevVideo.fragment.AppUpdateFragment;
import com.meizu.testdevVideo.fragment.ScreenRecordFragment;
import com.meizu.testdevVideo.fragment.UiautomatorFragment;
import com.meizu.testdevVideo.fragment.UpdateSoftwareFtpFragment;


public class CommonToolsActivity extends AppCompatActivity {

    private ScreenRecordFragment screenRecordFragment;
    private UiautomatorFragment uiautomatorFragment;
    private AppListFragment appListFragment;
//    private AppUpdateFragment appUpdateFragment;
    private UpdateSoftwareFtpFragment updateSoftwareFtpFragment;
    private static Activity mActivity = null;
    String object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Intent intend = getIntent();    // 获取跳转数据
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);   // 设置竖屏
        Bundle bundle=intend.getExtras();  // 从Intent中获得Bundle对象
        object=bundle.getString("object");   // 从Bundle中获得数据
        setTitle(object);
        setContentView(R.layout.activity_common_tools);
        setFragment(object);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    // 设置Fragment
    private void setFragment(String fragmentFlag)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if(fragmentFlag.equals("录制视频")){
            screenRecordFragment = new ScreenRecordFragment();
            transaction.replace(R.id.id_common_tools, screenRecordFragment);
        }else if(fragmentFlag.equals("脚本执行")){
            uiautomatorFragment = new UiautomatorFragment();
            transaction.replace(R.id.id_common_tools, uiautomatorFragment);
        }else if(fragmentFlag.equals("应用信息")){
            appListFragment = new AppListFragment();
            transaction.replace(R.id.id_common_tools, appListFragment);
//        }else if(fragmentFlag.equals("业务更新")){
//            appUpdateFragment = new AppUpdateFragment();
//            transaction.replace(R.id.id_common_tools, appUpdateFragment);
        }else if(fragmentFlag.equals("业务更新")){
            updateSoftwareFtpFragment = new UpdateSoftwareFtpFragment();
            transaction.replace(R.id.id_common_tools, updateSoftwareFtpFragment);
        }else if(fragmentFlag.equals("Scheme Test")){

        }
        transaction.commit();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(null != mActivity){
            mActivity = null;
        }
    }
}

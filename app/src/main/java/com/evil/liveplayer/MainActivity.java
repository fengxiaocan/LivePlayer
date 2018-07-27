package com.evil.liveplayer;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.evil.helper.recycler.recyclerhelper.RecyclerHelper;
import com.evil.rxlib.AsyncCallback;
import com.evil.rxlib.RxThread;

import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AppBarLayout mAblMain;
    private RecyclerView mRecyclerView;
    private FrameLayout mMenuFrame;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LiveAdapter mLiveAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0x321);
        }
    }

    private void initView() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mAblMain = (AppBarLayout)findViewById(R.id.abl_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mMenuFrame = (FrameLayout)findViewById(R.id.menu_frame);
        mDrawer = (DrawerLayout)findViewById(R.id.drawer);

        try {
            mToolbar.setTitle("📺电视TV");
        } catch (Exception e) {
            mToolbar.setTitle("电视TV");
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                                                           mDrawer,
                                                           mToolbar,
                                                           R.string.drawer_layout_open,
                                                           R.string.drawer_layout_close
        );
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.setHomeAsUpIndicator(R.mipmap.ic_launcher);//channge the icon,改变图标
        mActionBarDrawerToggle.syncState();////show the default icon and sync the DrawerToggle state,如果你想改变图标的话，这句话要去掉。这个会使用默认的三杠图标
        mDrawer.setDrawerListener(mActionBarDrawerToggle);
        mDrawer.setStatusBarBackgroundColor(Color.WHITE);//设置状态栏颜色
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.menu_frame,new SettingFragment(),"setting");
        transaction.addToBackStack("setting");
        transaction.commit();

        RecyclerHelper.with(mRecyclerView)
                      .linearManager()
                      .addDividerDecoration()
                      .animation()
                      .init();
        mLiveAdapter = new LiveAdapter();
        mRecyclerView.setAdapter(mLiveAdapter);
        List<LiveInfo> list = null;
        try {
            list = LitePal.findAll(LiveInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list == null || list.size() == 0) {
            RxThread.io().open().observeOnMain().async(new Callable<List<LiveInfo>>() {
                @Override
                public List<LiveInfo> call() throws Exception {
                    InputStream is = getAssets().open("live.txt");
                    BufferedReader bis = new BufferedReader(new InputStreamReader(is));
                    String temp;
                    List<LiveInfo> liveInfos = new ArrayList<>();
                    while ((temp = bis.readLine()) != null) {
                        String name = temp;
                        String url = bis.readLine();
                        Log.e("noah","name = " + name + " url = " + url);
                        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(url)) {
                            LiveInfo info = new LiveInfo();
                            info.setName(name);
                            info.setUrl(url);
                            liveInfos.add(info);
                        }
                    }
                    LitePal.saveAll(liveInfos);
                    return liveInfos;
                }
            }).subscribe(new AsyncCallback<List<LiveInfo>>() {
                @Override
                public void onSuccess(List<LiveInfo> liveInfos) {
                    mLiveAdapter.setDatasAndNotify(liveInfos);
                }

                @Override
                public void onFailed(Throwable throwable) {
                    if (throwable != null) {
                        Log.e("noah",throwable.getMessage());
                    }
                    Toast.makeText(MainActivity.this,"数据读取错误",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mLiveAdapter.setDatasAndNotify(list);
        }
    }

}

package com.evil.liveplayer;

import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.evil.liveplayer.util.ActivityUtils;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class LiveActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> {
    private StandardGSYVideoPlayer mVideoPlayer;
    private String mName;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        ActivityUtils.allScreen(this);
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            mName = " ";
            mUrl = uri.getPath();
            Log.e("noah",mUrl);
        } else {
            mName = getIntent().getStringExtra("name");
            mUrl = getIntent().getStringExtra("url");
        }

        if (Setting.PLAYER_TYPE == 1) {
            //EXO 2 播放内核
            GSYVideoManager.instance().setVideoType(this,GSYVideoType.IJKEXOPLAYER2);
        } else if (Setting.PLAYER_TYPE == 2) {
            //系统播放器
            GSYVideoManager.instance().setVideoType(this,GSYVideoType.SYSTEMPLAYER);
        } else {
            //默认ijk播放内核
            GSYVideoManager.instance().setVideoType(this,GSYVideoType.IJKPLAYER);
        }
        setContentView(R.layout.activity_live);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0x321);
        }
        initVideoBuilderMode();
    }


    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return mVideoPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        return new GSYVideoOptionBuilder().setUrl(mUrl)
                                          .setCacheWithPlay(true)
                                          .setVideoTitle(mName)
                                          .setIsTouchWiget(true)
                                          .setRotateViewAuto(false)
                                          .setLockLand(false)
                                          .setShowFullAnimation(false)//打开动画
                                          .setNeedLockFull(true)
                                          .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }

    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void initView() {
        mVideoPlayer = (StandardGSYVideoPlayer)findViewById(R.id.video_player);
    }
}

package com.evil.liveplayer;

import com.evil.liveplayer.util.SDCardUtils;

import java.io.File;

public class Setting {
    public static final String APP_DIR = "LivePlayer";
    public static final String VIDEO_CACHE_DIR = "video";

    public static File getVideoCacheDir() {
        return new File(getAppCacheDir(),VIDEO_CACHE_DIR);
    }

    public static File getAppCacheDir() {
        return SDCardUtils.getSdSaveDir(APP_DIR);
    }

    public static int PLAYER_TYPE = 0;
}

package com.waracle.androidtest;

import android.app.Application;
import android.net.http.HttpResponseCache;
import android.util.Log;

import com.waracle.androidtest.features.imagelist.ui.PlaceholderFragment;
import com.waracle.androidtest.shared.utils.ImageLoader;

import java.io.File;
import java.io.IOException;

/**
 * Created by lukas on 16/02/2018.
 */

public class CakeApplication extends Application {
    private static final String TAG = PlaceholderFragment.class.getSimpleName();
    private static CakeApplication appInstance = null;

    private final ImageLoader imageLoader = new ImageLoader();

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 15 * 1024 * 1024; // 15 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.i(TAG, "HttpResponseCache installation failed:" + e); // Timber should be used for logging
        }
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static CakeApplication getAppInstance() {
        return appInstance;
    }
}

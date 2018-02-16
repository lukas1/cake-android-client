package com.waracle.androidtest.shared.utils;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lukas on 15/02/2018.
 */

public final class UrlConnectionUtils {
    public static @NonNull HttpURLConnection createConnection(@NonNull String url) throws IOException {
        return  (HttpURLConnection) new URL(url).openConnection();
    }
}

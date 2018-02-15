package com.waracle.androidtest.shared.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lukas on 15/02/2018.
 */

public final class UrlConnectionUtils {
    public static HttpURLConnection createConnection(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }
}

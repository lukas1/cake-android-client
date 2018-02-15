package com.waracle.androidtest.networking;

import android.support.annotation.NonNull;

import com.waracle.androidtest.StreamUtils;
import com.waracle.androidtest.core.Failable;
import com.waracle.androidtest.core.IO;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lukas on 15/02/2018.
 */

public final class JsonHttpDataLoader {
    public static final @NonNull IO<Failable<JSONArray>> loadJsonData(@NonNull final URL url) {
        return new IO<>(new IO.IOOperation<Failable<JSONArray>>() {
            @Override
            public @NonNull Failable<JSONArray> doIOOperation() {
                try {
                    return doHttpCall((HttpURLConnection) url.openConnection());
                } catch (IOException exception) {
                    return new Failable(exception.getMessage());
                }
            }
        });
    }

    private static Failable<JSONArray> doHttpCall(HttpURLConnection urlConnection) {
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???

            // Also, Do you trust any utils thrown your way????

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            return new Failable(new JSONArray(jsonText));
        } catch (IOException | JSONException exception) {
            return new Failable(exception.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    public static String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }
}

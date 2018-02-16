package com.waracle.androidtest.shared.networking;

import android.support.annotation.NonNull;

import com.waracle.androidtest.shared.utils.StreamUtils;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.shared.utils.UrlConnectionUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public final class JsonHttpDataLoader {
    public final @NonNull <T> IO<Failable<ArrayList<T>>> loadJsonData(
            @NonNull final String url,
            @NonNull final JsonArrayConvertor<T> jsonArrayConvertor,
            final boolean useCaches
    ) {
        return new IO<>(new IO.IOOperation<Failable<ArrayList<T>>>() {
            @Override
            public @NonNull Failable<ArrayList<T>> doIOOperation() {
                try {
                    return doHttpCall(
                            UrlConnectionUtils.createConnection(url), jsonArrayConvertor, useCaches
                    );
                } catch (IOException exception) {
                    return new Failable(exception.getMessage());
                }
            }
        });
    }

    private static @NonNull <T> Failable<ArrayList<T>> doHttpCall(
            @NonNull HttpURLConnection urlConnection,
            @NonNull final JsonArrayConvertor<T> jsonArrayConvertor,
            final boolean useCaches
    ) {
        // Most of this code should really be solved via Retrofit + OkHttp + Gson/Moshi
        try {
            urlConnection.setUseCaches(useCaches);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Also, Do you trust any utils thrown your way????

            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            JSONArray jsonArray = new JSONArray(jsonText);

            return new Failable(jsonArrayConvertor.convertJsonArray(jsonArray));
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

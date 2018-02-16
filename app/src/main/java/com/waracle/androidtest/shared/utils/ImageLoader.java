package com.waracle.androidtest.shared.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    private final HashMap<String, Bitmap> cache = new HashMap<>();

    public ImageLoader() { /**/ }

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     * @param imageView view to set image too.
     */
    public void load(@NonNull final String url, @NonNull final WeakReference<ImageView> imageView) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        if (cache.get(url) != null) {
            setImageView(imageView, cache.get(url));
        } else {
            loadImageData(url).runAsync(new IO.IOCallback<Failable<Bitmap>>() {
                @Override
                public void callback(@NonNull Failable<Bitmap> value) {
                    value.fold(new Failable.FailableFoldCallback<Bitmap>() {
                        @Override
                        public void foldValue(@NonNull Bitmap value) {
                            cache.put(url, value);
                            setImageView(imageView, value);
                        }

                        @Override
                        public void foldError(@NonNull String error) {
                            Log.e(TAG, error);
                        }
                    });
                }
            });
        }
    }

    private static @NonNull IO<Failable<Bitmap>> loadImageData(@NonNull final String url) {
        return new IO<>(new IO.IOOperation<Failable<Bitmap>>() {
            @NonNull
            @Override
            public Failable<Bitmap> doIOOperation() {
                return doLoadImageData(url);
            }
        });
    }

    private static @NonNull Failable<Bitmap> doLoadImageData(@NonNull final String url) {
        try {
            return doHttpCall(UrlConnectionUtils.createConnection(url));
        } catch (IOException exception) {
            return new Failable<>(exception.getMessage());
        }
    }

    private static @NonNull Failable<Bitmap> doHttpCall(@NonNull HttpURLConnection connection) {
        InputStream inputStream = null;
        try {
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);
            inputStream = connection.getInputStream();

            int httpResponseCode = connection.getResponseCode();
            if (httpResponseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    return new Failable<>(bitmap);
                } else {
                    return new Failable<>(
                            "Failed to load bitmap: " + connection.getURL().toString()
                    );
                }
            } else if (httpResponseCode == HttpURLConnection.HTTP_MOVED_PERM) {
                return doLoadImageData(connection.getHeaderField("Location"));
            } else {
                return new Failable<>(
                        "Loading image at url" + connection.getURL().toString()
                        + " failed with HTTP CODDE " + httpResponseCode
                );
            }
        } catch (IOException exception) {
            return new Failable<>(exception.getMessage());
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    private static void setImageView(@NonNull WeakReference<ImageView> imageView, @Nullable Bitmap bitmap) {
        if (imageView.get() != null) {
            imageView.get().setImageBitmap(bitmap);
        }
    }
}

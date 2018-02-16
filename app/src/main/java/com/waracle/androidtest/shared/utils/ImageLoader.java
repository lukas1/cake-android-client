package com.waracle.androidtest.shared.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.security.InvalidParameterException;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();

    public ImageLoader() { /**/ }

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     * @param imageView view to set image too.
     */
    public void load(@NonNull String url, @NonNull final ImageView imageView) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        loadImageData(url).runAsync(new IO.IOCallback<Failable<Bitmap>>() {
            @Override
            public void callback(@NonNull Failable<Bitmap> value) {
                value.fold(new Failable.FailableFoldCallback<Bitmap>() {
                    @Override
                    public void foldValue(@NonNull Bitmap value) {
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

    private static IO<Failable<Bitmap>> loadImageData(@NonNull final String url) {
        return new IO<>(new IO.IOOperation<Failable<Bitmap>>() {
            @NonNull
            @Override
            public Failable<Bitmap> doIOOperation() {
                try {
                    return doHttpCall(UrlConnectionUtils.createConnection(url));
                } catch (IOException exception) {
                    return new Failable<>(exception.getMessage());
                }
            }
        });
    }

    private static @NonNull Failable<Bitmap> doHttpCall(@NonNull HttpURLConnection connection) {
        InputStream inputStream = null;
        try {
            try {
                connection.setUseCaches(true);
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                inputStream = connection.getErrorStream();
            }
            return new Failable<>(BitmapFactory.decodeStream(inputStream));
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    private static void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}

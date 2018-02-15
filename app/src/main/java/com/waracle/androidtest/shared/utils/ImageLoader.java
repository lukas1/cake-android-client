package com.waracle.androidtest.shared.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.shared.core.Transform;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;

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

        getBitmapIO(url).runAsync(new IO.IOCallback<Failable<Bitmap>>() {
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

    private IO<Failable<Bitmap>> getBitmapIO(@NonNull String url) {
        return loadImageData(url).map(new Transform<Failable<byte[]>, Failable<Bitmap>>() {
            @Override
            public Failable<Bitmap> transform(Failable<byte[]> input) {
                return input.map(new Transform<byte[], Bitmap>() {
                    @Override
                    public Bitmap transform(byte[] input) {
                        return convertToBitmap(input);
                    }
                });
            }
        });
    }

    private static IO<Failable<byte[]>> loadImageData(@NonNull final String url) {
        return new IO<>(new IO.IOOperation<Failable<byte[]>>() {
            @NonNull
            @Override
            public Failable<byte[]> doIOOperation() {
                try {
                    return doHttpCall(UrlConnectionUtils.createConnection(url));
                } catch (IOException exception) {
                    return new Failable<>(exception.getMessage());
                }
            }
        });
    }

    private static @NonNull Failable<byte[]> doHttpCall(@NonNull HttpURLConnection connection) {
        InputStream inputStream = null;
        try {
            try {
                // Read data from workstation
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                inputStream = connection.getErrorStream();
            }

            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??

            return new Failable<>(StreamUtils.readUnknownFully(inputStream));
        } catch (IOException exception) {
            return new Failable<>(exception.getMessage());
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    private static Bitmap convertToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    private static void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}

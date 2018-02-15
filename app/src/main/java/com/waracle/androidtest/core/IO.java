package com.waracle.androidtest.core;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * IO class encapsulates IO operation that can be executed asynchronously, so that this type can be
 * returned, to enable for pure functions even in cases where asynchronous operations are necessary
 */
public final class IO<T> {
    public interface IOCallback<T> {
        void callback(@NonNull T value);
    }

    public interface IOOperation<T> {
        @NonNull T doIOOperation();
    }

    private final IOOperation<T> operation;

    public IO(@NonNull IOOperation<T> operation) {
        this.operation = operation;
    }

    public final void runAsync(@NonNull final IOCallback<T> callback) {
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                final T value = operation.doIOOperation();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.callback(value);
                    }
                });
            }
        }).start();
    }
}

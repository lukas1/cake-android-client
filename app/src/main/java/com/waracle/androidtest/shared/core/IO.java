package com.waracle.androidtest.shared.core;

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

    public IO(@NonNull final T pureValue) {
        operation = new IOOperation<T>() {
            @NonNull
            @Override
            public T doIOOperation() {
                return pureValue;
            }
        };
    }

    public final @NonNull <U> IO<U> map(@NonNull final Transform<T, U> transform) {
        return new IO<>(new IOOperation<U>() {
            @NonNull
            @Override
            public U doIOOperation() {
                return transform.transform(operation.doIOOperation());
            }
        });
    }

    public final void runAsync(@NonNull final IOCallback<T> callback) {
        // Using handler here directly together with starting new thread makes this class
        // not testable using Unit tests.
        // This can be fixed by injecting async runner, that can be mocked for test purposes as
        // a synchronous runner instead.
        // This demo is not tested anyway, due to JUnit being forbidden on the project
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        // Ideally there should be a thread pool of limited number of threads to avoid spawning too
        // many threads. I'm not solving this problem here, but threads should be avoided altogether
        // now in favour of Kotlin's coroutines
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

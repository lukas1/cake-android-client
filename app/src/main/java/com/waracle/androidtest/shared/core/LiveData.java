package com.waracle.androidtest.shared.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public final class LiveData<T> {
    public interface Observer<T> {
        void onNext(@Nullable T value);
        void onError(@NonNull String error);
    }

    private final ArrayList<Observer<T>> observers = new ArrayList<>();
    private T value;

    public final void updateValue(@Nullable T value) {
        this.value = value;
        for (Observer<T> observer: observers) {
            observer.onNext(value);
        }
    }

    public final void postError(@NonNull String error) {
        value = null;
        for (Observer<T> observer: observers) {
            observer.onError(error);
        }
    }

    public final void subscribe(@NonNull Observer<T> observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
        if (value != null) {
            observer.onNext(value);
        }
    }

    public final void unsubscribe(@NonNull Observer<T> observer) {
        observers.remove(observer);
    }
}

package com.waracle.androidtest.shared.core;

import android.support.annotation.NonNull;

/**
 * This class is used to encapsulate errors into a type, so that functions that have to deal with
 * exceptions can be pure.
 *
 * FailableFoldCallback interface is expected to be used as input to fold method of Failable
 *
 * Failable guaranees that only one of foldValue or foldError is called, depending on the state
 * Object of Failable type can only ever contain value or errorMessage, never both at once
 */
public final class Failable<T> {

    public interface FailableFoldCallback<T> {
        void foldValue(@NonNull T value);
        void foldError(@NonNull String error);
    }

    private final T value;

    // Idealy this would be also generic type not a String and user of this class could select alternative error type - Maybe monad
    private final String errorMessage;

    public Failable(@NonNull T value) {
        this.value = value;
        errorMessage = null;
    }

    public Failable(@NonNull String errorMessage) {
        this.errorMessage = errorMessage;
        value = null;
    }

    public final void fold(@NonNull FailableFoldCallback<T> foldCallback) {
        if (value != null) {
            foldCallback.foldValue(value);
        } else if (errorMessage != null) {
            foldCallback.foldError(errorMessage);
        }
    }
}

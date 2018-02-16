package com.waracle.androidtest.shared.core;

import android.support.annotation.NonNull;

/**
 * Created by lukas on 15/02/2018.
 */

public interface Transform<T, U> {
    @NonNull U transform(@NonNull T input);
}

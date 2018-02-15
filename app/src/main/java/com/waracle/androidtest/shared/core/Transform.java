package com.waracle.androidtest.shared.core;

/**
 * Created by lukas on 15/02/2018.
 */

public interface Transform<T, U> {
    U transform(T input);
}

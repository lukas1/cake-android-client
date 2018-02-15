package com.waracle.androidtest.shared.networking;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonObjectConvertor<T> {
    T convertToDataClass(@NonNull JSONObject input) throws JSONException;
}

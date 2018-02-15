package com.waracle.androidtest.networking;

import android.support.annotation.NonNull;

import com.waracle.androidtest.dataclasses.ImageItem;

import org.json.JSONException;
import org.json.JSONObject;

public final class ImageItemConvertor implements JsonObjectConvertor<ImageItem> {

    @Override
    public @NonNull ImageItem convertToDataClass(@NonNull JSONObject input) throws JSONException {
        return new ImageItem(
                input.getString("title"),
                input.getString("desc"),
                input.getString("image")
        );
    }
}

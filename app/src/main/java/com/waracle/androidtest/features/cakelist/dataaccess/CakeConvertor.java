package com.waracle.androidtest.features.cakelist.dataaccess;

import android.support.annotation.NonNull;

import com.waracle.androidtest.features.cakelist.dataclasses.Cake;
import com.waracle.androidtest.shared.networking.JsonObjectConvertor;

import org.json.JSONException;
import org.json.JSONObject;

public final class CakeConvertor implements JsonObjectConvertor<Cake> {

    @Override
    public @NonNull Cake convertToDataClass(@NonNull JSONObject input) throws JSONException {
        return new Cake(
                input.getString("title"),
                input.getString("desc"),
                input.getString("image")
        );
    }
}

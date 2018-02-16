package com.waracle.androidtest.shared.networking;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonArrayConvertor<T> {
    private final JsonObjectConvertor<T> objectConvertor;

    public JsonArrayConvertor(@NonNull JsonObjectConvertor<T> objectConvertor) {
        this.objectConvertor = objectConvertor;
    }

    public @NonNull ArrayList<T> convertJsonArray(@NonNull JSONArray jsonArray) throws JSONException {
        ArrayList<T> result = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(objectConvertor.convertToDataClass(jsonObject));
        }
        return result;
    }
}

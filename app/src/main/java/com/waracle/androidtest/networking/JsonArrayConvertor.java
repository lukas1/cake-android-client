package com.waracle.androidtest.networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonArrayConvertor<T> {
    private final JsonObjectConvertor<T> objectConvertor;

    public JsonArrayConvertor(JsonObjectConvertor<T> objectConvertor) {
        this.objectConvertor = objectConvertor;
    }

    public ArrayList<T> convertJsonArray(JSONArray jsonArray) throws JSONException {
        ArrayList<T> result = new ArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            result.add(objectConvertor.convertToDataClass(jsonObject));
        }
        return result;
    }
}

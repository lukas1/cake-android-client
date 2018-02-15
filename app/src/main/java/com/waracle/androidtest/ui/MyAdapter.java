package com.waracle.androidtest.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class MyAdapter extends BaseAdapter {
    private final ImageLoader imageLoader = new ImageLoader();
    private final Context context;

    // Can you think of a better way to represent these items???
    private JSONArray items;

    public MyAdapter(Context context) {
        this(context, new JSONArray());
    }

    public MyAdapter(Context context, JSONArray items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return items.getJSONObject(position);
        } catch (JSONException e) {
            Log.e("", e.getMessage());
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.list_item_layout, parent, false);
        if (root != null) {
            TextView title = root.findViewById(R.id.title);
            TextView desc = root.findViewById(R.id.desc);
            ImageView image = root.findViewById(R.id.image);
            try {
                JSONObject object = (JSONObject) getItem(position);
                title.setText(object.getString("title"));
                desc.setText(object.getString("desc"));
                imageLoader.load(object.getString("image"), image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public void setItems(JSONArray items) {
        this.items = items;
    }
}

package com.waracle.androidtest.features.imagelist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.shared.utils.ImageLoader;
import com.waracle.androidtest.R;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;

import java.util.ArrayList;

final class MyAdapter extends BaseAdapter {
    private final ImageLoader imageLoader = new ImageLoader();
    private final Context context;

    private ArrayList<ImageItem> items;

    public MyAdapter(Context context) {
        this(context, new ArrayList<ImageItem>());
    }

    public MyAdapter(Context context, ArrayList<ImageItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ImageItem getItem(int position) {
        return items.get(position);
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

            ImageItem imageItem = getItem(position);
            title.setText(imageItem.getTitle());
            desc.setText(imageItem.getDescription());
            imageLoader.load(imageItem.getImageUrl(), image);
        }

        return root;
    }

    public void setItems(ArrayList<ImageItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}

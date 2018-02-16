package com.waracle.androidtest.features.cakelist.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.CakeApplication;
import com.waracle.androidtest.shared.utils.ImageLoader;
import com.waracle.androidtest.R;
import com.waracle.androidtest.features.cakelist.dataclasses.Cake;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

final class CakeAdapter extends BaseAdapter {
    private final ImageLoader imageLoader = CakeApplication.getAppInstance().getImageLoader();
    private final Context context;

    private ArrayList<Cake> items;

    public CakeAdapter(Context context) {
        this(context, new ArrayList<Cake>());
    }

    public CakeAdapter(Context context, ArrayList<Cake> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Cake getItem(int position) {
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

            Cake cake = getItem(position);
            title.setText(cake.getTitle());
            desc.setText(cake.getDescription());
            imageLoader.load(cake.getImageUrl(), new WeakReference(image));
        }

        return root;
    }

    public void setItems(ArrayList<Cake> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}

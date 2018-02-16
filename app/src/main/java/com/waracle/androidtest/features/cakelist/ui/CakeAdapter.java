package com.waracle.androidtest.features.cakelist.ui;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Cake cake = getItem(position);
        viewHolder.title.setText(cake.getTitle());
        viewHolder.desc.setText(cake.getDescription());
        viewHolder.image.setImageBitmap(null);
        imageLoader.load(cake.getImageUrl(), new WeakReference(viewHolder.image));

        return convertView;
    }

    public void setItems(ArrayList<Cake> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView title;
        TextView desc;
        ImageView image;

        ViewHolder(View view) {
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            image = view.findViewById(R.id.image);
        }
    }
}

package com.waracle.androidtest.features.imagelist.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;
import com.waracle.androidtest.shared.core.LiveData;

import java.util.ArrayList;

/**
 * Fragment is responsible for subscribing to images LiveData
 * then displaying a list of cakes with images.
 * Improve any performance issues
 */
public final class PlaceholderFragment extends ListFragment {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private ListView listView;
    private MyAdapter adapter;

    private LiveData<ArrayList<ImageItem>> images;
    private final LiveData.Observer<ArrayList<ImageItem>> imagesObserver = new LiveData.Observer<ArrayList<ImageItem>>() {
        @Override
        public void onNext(@Nullable ArrayList<ImageItem> value) {
            if (value == null) {
                return;
            }

            adapter.setItems(value);
        }

        @Override
        public void onError(@NonNull String error) {
            Log.e(TAG, error);
        }
    };

    public PlaceholderFragment() { /**/ }

    public final void setImagesLiveData(LiveData<ArrayList<ImageItem>> images) {
        if (this.images != null) {
            unsubscribe();
        }
        this.images = images;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.
        adapter = new MyAdapter(getActivity());
        listView.setAdapter(adapter);
        subscribe();
    }

    @Override
    public void onStart() {
        super.onStart();

        subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();

        unsubscribe();
    }

    private void subscribe() {
        images.subscribe(imagesObserver);
    }

    private void unsubscribe() {
        images.unsubscribe(imagesObserver);
    }
}

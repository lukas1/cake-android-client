package com.waracle.androidtest.features.imagelist.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;
import com.waracle.androidtest.features.imagelist.dataaccess.ImageListLoader;

import java.util.ArrayList;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public final class PlaceholderFragment extends ListFragment {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private ListView mListView;
    private MyAdapter mAdapter;

    public PlaceholderFragment() { /**/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.
        mAdapter = new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);

        // Load data from net.
        ImageListLoader.loadImages().runAsync(new IO.IOCallback<Failable<ArrayList<ImageItem>>>() {
            @Override
            public void callback(@NonNull Failable<ArrayList<ImageItem>> value) {
                value.fold(new Failable.FailableFoldCallback<ArrayList<ImageItem>>() {
                    @Override
                    public void foldValue(@NonNull ArrayList<ImageItem> value) {
                        mAdapter.setItems(value);
                    }

                    @Override
                    public void foldError(@NonNull String error) {
                        Log.e(TAG, error);
                    }
                });
            }
        });
    }


}

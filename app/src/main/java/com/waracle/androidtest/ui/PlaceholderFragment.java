package com.waracle.androidtest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.core.Failable;
import com.waracle.androidtest.core.IO;
import com.waracle.androidtest.networking.JsonHttpDataLoader;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public final class PlaceholderFragment extends ListFragment {
    private static final String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

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
        try {
            JsonHttpDataLoader.loadJsonData(new URL(JSON_URL)).runAsync(new IO.IOCallback<Failable<JSONArray>>() {
                @Override
                public void callback(@NonNull Failable<JSONArray> value) {
                    value.fold(new Failable.FailableFoldCallback<JSONArray>() {
                        @Override
                        public void foldValue(@NonNull JSONArray value) {
                            mAdapter.setItems(value);
                        }

                        @Override
                        public void foldError(@NonNull String error) {
                            Log.e(TAG, error);
                        }
                    });
                }
            });
        } catch (MalformedURLException exception) {
            Log.e(TAG, exception.getMessage());
        }
    }


}

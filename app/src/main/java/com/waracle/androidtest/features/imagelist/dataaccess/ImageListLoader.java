package com.waracle.androidtest.features.imagelist.dataaccess;

import android.support.annotation.NonNull;

import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;
import com.waracle.androidtest.shared.networking.JsonArrayConvertor;
import com.waracle.androidtest.shared.networking.JsonHttpDataLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by lukas on 15/02/2018.
 */

public class ImageListLoader {
    private static final String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    public static @NonNull IO<Failable<ArrayList<ImageItem>>> loadImages() {
        try {
            return JsonHttpDataLoader.loadJsonData(
                    new URL(JSON_URL),
                    new JsonArrayConvertor<>(new ImageItemConvertor())
            );
        } catch (MalformedURLException exception) {
            return new IO<>(new Failable<ArrayList<ImageItem>>(exception.getMessage()));
        }
    }
}

package com.waracle.androidtest.features.imagelist.viewmodel;

import com.waracle.androidtest.features.imagelist.dataaccess.ImageItemConvertor;
import com.waracle.androidtest.features.imagelist.dataaccess.ImageListLoader;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;
import com.waracle.androidtest.shared.networking.JsonArrayConvertor;
import com.waracle.androidtest.shared.networking.JsonHttpDataLoader;

public class ImageListLoadingContext {
    public final String imageListUrl = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";
    public final ImageListLoader imageListLoader = new ImageListLoader();
    public final JsonHttpDataLoader jsonHttpDataLoader = new JsonHttpDataLoader();
    public final JsonArrayConvertor<ImageItem> imageItemListConverter = new JsonArrayConvertor<>(
            new ImageItemConvertor()
    );
}

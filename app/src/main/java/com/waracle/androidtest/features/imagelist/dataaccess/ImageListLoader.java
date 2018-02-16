package com.waracle.androidtest.features.imagelist.dataaccess;

import android.support.annotation.NonNull;

import com.waracle.androidtest.features.imagelist.viewmodel.ImageListLoadingContext;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;

import java.util.ArrayList;

public class ImageListLoader {
    public @NonNull IO<Failable<ArrayList<ImageItem>>> loadImages(boolean useCaches, ImageListLoadingContext context) {
        return context.jsonHttpDataLoader.loadJsonData(
                context.imageListUrl,
                context.imageItemListConverter,
                useCaches
        );
    }
}

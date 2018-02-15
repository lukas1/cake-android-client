package com.waracle.androidtest.features.imagelist.viewmodel;

import android.support.annotation.NonNull;

import com.waracle.androidtest.features.imagelist.dataclasses.ImageItem;
import com.waracle.androidtest.shared.core.Failable;
import com.waracle.androidtest.shared.core.IO;
import com.waracle.androidtest.shared.core.LiveData;

import java.util.ArrayList;

public class ImageListViewModel {
    private final ImageListLoadingContext context;
    private final LiveData<ArrayList<ImageItem>> images = new LiveData<>();

    public ImageListViewModel(ImageListLoadingContext context) {
        this.context = context;
        loadImages();
    }

    public LiveData<ArrayList<ImageItem>> getImages() {
        return images;
    }

    private void loadImages() {
        context.imageListLoader.loadImages(context).runAsync(new IO.IOCallback<Failable<ArrayList<ImageItem>>>() {
            @Override
            public void callback(@NonNull Failable<ArrayList<ImageItem>> value) {
                value.fold(new Failable.FailableFoldCallback<ArrayList<ImageItem>>() {
                    @Override
                    public void foldValue(@NonNull ArrayList<ImageItem> value) {
                        images.updateValue(value);
                    }

                    @Override
                    public void foldError(@NonNull String error) {
                        images.postError(error);
                    }
                });
            }
        });
    }
}

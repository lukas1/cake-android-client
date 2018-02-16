package com.waracle.androidtest.features.cakelist.dataclasses;

import android.support.annotation.NonNull;

public final class Cake {
    private final String title;
    private final String description;
    private final String imageUrl;

    public Cake(@NonNull String title, @NonNull String description,@NonNull String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public @NonNull String getTitle() {
        return title;
    }

    public @NonNull String getDescription() {
        return description;
    }

    public @NonNull String getImageUrl() {
        return imageUrl;
    }
}

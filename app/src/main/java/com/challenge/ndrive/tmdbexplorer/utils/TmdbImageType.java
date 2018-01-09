package com.challenge.ndrive.tmdbexplorer.utils;

/**
 * Created by marcelo on 1/8/18.
 */

public enum TmdbImageType {

    THUMB("w92"), LARGE("w500");

    private String mValue;

    TmdbImageType(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}

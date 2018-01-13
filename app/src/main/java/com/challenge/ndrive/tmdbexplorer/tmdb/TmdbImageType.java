package com.challenge.ndrive.tmdbexplorer.tmdb;

/**
 * Represents the size of the images used in the application
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

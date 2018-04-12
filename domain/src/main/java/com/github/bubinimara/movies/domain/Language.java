package com.github.bubinimara.movies.domain;

/**
 * Created by davide.
 */
public class Language {
    private String displayName;
    private String isoCode;

    public Language() {
    }

    public Language(String displayName, String isoCode) {
        this.displayName = displayName;
        this.isoCode = isoCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }
}

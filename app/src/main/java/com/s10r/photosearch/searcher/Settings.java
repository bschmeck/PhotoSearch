package com.s10r.photosearch.searcher;

import java.util.Arrays;

/**
 * Created by bschmeckpeper on 11/2/15.
 */
public class Settings {
    /*
    User can configure advanced search filters such as:
    Size (small, medium, large, extra-large)
    Color filter (black, blue, brown, gray, green, etc...)
    Type (faces, photo, clip art, line art)
    Site (espn.com)
    */

    public static final String[] ALLOWED_IMAGE_COLORS = {
            "black",
            "blue",
            "brown",
            "gray",
            "green",
            "orange",
            "pink",
            "purple",
            "red",
            "teal",
            "white",
            "yellow"
    };
    public static final String[] ALLOWED_IMAGE_TYPES = {
            "face",
            "photo",
            "clipart",
            "lineart"
    };
    public static final String[] ALLOWED_IMAGE_SIZES = {
            "small",
            "medium",
            "large",
            "xlarge"
    };
    private String imageSize;
    private String imageColor;
    private String imageType;
    private String site;

    public void setImageColor(String color) {
        color = color.toLowerCase();
        if (!Arrays.asList(ALLOWED_IMAGE_COLORS).contains(color)) {
            throw new IllegalArgumentException("Unsupported color " + color);
        }
        this.imageColor = color;
    }
    public void unsetImageColor() {
        this.imageColor = null;
    }
    public boolean isImageColorSet() {
        return this.imageColor == null;
    }
    public String getImageColor() { return this.imageColor; }
    
    public void setImageType(String type) {
        type = type.toLowerCase();
        if (!Arrays.asList(ALLOWED_IMAGE_TYPES).contains(type)) {
            throw new IllegalArgumentException("Unsupported type " + type);
        }
        this.imageType = type;
    }
    public void unsetImageType() {
        this.imageType = null;
    }
    public boolean isImageTypeSet() {
        return this.imageType == null;
    }
    public String getImageType() { return this.imageType; }

    public void setSite(String site) {
        this.site = site;
    }
    public void unsetSite() {
        this.site = null;
    }
    public boolean isSiteSet() {
        return this.site == null;
    }
    public String getSite() { return this.site; }

    public void setImageSize(String size) {
        size = size.toLowerCase();

        if (size == "small") {
            this.imageSize = "icon";
        } else if (size == "medium") {
            this.imageSize = "medium";
        } else if (size == "large") {
            this.imageSize = "xxlarge";
        } else if (size == "xlarge") {
            this.imageSize = "huge";
        } else {
            throw new IllegalArgumentException("Unsupported size " + size);
        }
    }
    public void unsetImageSize() {
        this.imageSize = null;
    }
    public boolean imageSizeIsSet() {
        return this.imageSize != null;
    }
    public String getImageSize() { return this.imageSize; }
}

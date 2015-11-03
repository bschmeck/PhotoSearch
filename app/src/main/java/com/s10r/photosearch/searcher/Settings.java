package com.s10r.photosearch.searcher;

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

    public enum Color {
        ALL,
        BLACK,
        BLUE,
        BROWN,
        GRAY,
        GREEN,
        ORANGE,
        PINK,
        PURPLE,
        RED,
        TEAL,
        WHITE,
        YELLOW
    }
    public enum Type {
        ALL,
        CLIPART,
        FACE,
        LINEART,
        PHOTO
    };
    public enum Size {
        ALL,
        LARGE,
        MEDIUM,
        SMALL,
        XLARGE
    }
    private Size imageSize = Size.ALL;
    private Color imageColor = Color.ALL;
    private Type imageType = Type.ALL;
    private String site;

    public Color getImageColor() { return this.imageColor; }
    public void setImageColor(Color color) { this.imageColor = color; }

    public Type getImageType() { return this.imageType; }
    public void setImageType(Type type) { this.imageType = type; }

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

    public Size getImageSize() { return this.imageSize; }
    public void setImageSize(Size size) { this.imageSize = size; }
}

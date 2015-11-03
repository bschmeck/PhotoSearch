package com.s10r.photosearch.searcher;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bschmeckpeper on 11/2/15.
 */
public class Settings implements Parcelable {
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
        SMALL,
        MEDIUM,
        LARGE,
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageSize == null ? -1 : this.imageSize.ordinal());
        dest.writeInt(this.imageColor == null ? -1 : this.imageColor.ordinal());
        dest.writeInt(this.imageType == null ? -1 : this.imageType.ordinal());
        dest.writeString(this.site);
    }

    public Settings() {
    }

    protected Settings(Parcel in) {
        int tmpImageSize = in.readInt();
        this.imageSize = tmpImageSize == -1 ? null : Size.values()[tmpImageSize];
        int tmpImageColor = in.readInt();
        this.imageColor = tmpImageColor == -1 ? null : Color.values()[tmpImageColor];
        int tmpImageType = in.readInt();
        this.imageType = tmpImageType == -1 ? null : Type.values()[tmpImageType];
        this.site = in.readString();
    }

    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}

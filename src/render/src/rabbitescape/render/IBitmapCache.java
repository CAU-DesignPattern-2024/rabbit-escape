package rabbitescape.render;

import rabbitescape.render.androidlike.Bitmap;

public interface IBitmapCache<T extends Bitmap> {
    T get(String fileName, int tileSize);
    void recycle();
    void setLoader(BitmapLoader<T> loader);
}

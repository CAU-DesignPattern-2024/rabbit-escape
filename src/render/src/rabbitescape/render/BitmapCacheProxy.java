package rabbitescape.render;

import rabbitescape.render.androidlike.Bitmap;

public class BitmapCacheProxy<T extends Bitmap> implements IBitmapCache<T> {
    private final IBitmapCache<T> realCache;
    private long totalHits = 0;
    private long totalMisses = 0;
    
    public BitmapCacheProxy(BitmapLoader<T> loader, BitmapScaler<T> scaler, long cacheSize) {
        this.realCache = new BitmapCache<>(loader, scaler, cacheSize);
    }
    
    @Override
    public T get(String fileName, int tileSize) {
        T bitmap = realCache.get(fileName, tileSize);
        if (bitmap != null) {
            totalHits++;
        } else {
            totalMisses++;
        }
        return bitmap;
    }
    
    @Override
    public void recycle() {
        realCache.recycle();
    }
    
    @Override
    public void setLoader(BitmapLoader<T> loader) {
        realCache.setLoader(loader);
    }
    
    public double getHitRate() {
        long total = totalHits + totalMisses;
        return total == 0 ? 0.0 : (double) totalHits / total;
    }
}

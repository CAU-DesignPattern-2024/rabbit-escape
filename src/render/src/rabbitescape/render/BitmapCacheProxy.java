package rabbitescape.render;

import rabbitescape.render.androidlike.Bitmap;

// 실제 비트맵 캐시와 상호작용하며 캐시 히트 및 미스를 추적
public class BitmapCacheProxy<T extends Bitmap> implements IBitmapCache<T> {
    private final IBitmapCache<T> realCache; // 실제 비트맵 캐시
    private long totalHits = 0; // 캐시 히트 횟수
    private long totalMisses = 0; // 캐시 미스 횟수
    
    public BitmapCacheProxy(BitmapLoader<T> loader, BitmapScaler<T> scaler, long cacheSize) {
        this.realCache = new BitmapCache<>(loader, scaler, cacheSize);
    }
    
    @Override
    public T get(String fileName, int tileSize) {
        T bitmap = realCache.get(fileName, tileSize); // 비트맵 가져오기
        if (bitmap != null) {
            totalHits++; // 캐시 히트 증가
        } else {
            totalMisses++; // 캐시 미스 증가
        }
        return bitmap;
    }
    
    @Override
    public void recycle() {
        realCache.recycle(); // 캐시 재활용
    }
    
    @Override
    public void setLoader(BitmapLoader<T> loader) {
        realCache.setLoader(loader); // 비트맵 로더 설정
    }
    
    public double getHitRate() {
        long total = totalHits + totalMisses;
        return total == 0 ? 0.0 : (double) totalHits / total;
    }
}

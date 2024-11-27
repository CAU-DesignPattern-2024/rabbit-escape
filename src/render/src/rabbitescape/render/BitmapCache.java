package rabbitescape.render;

import rabbitescape.render.androidlike.Bitmap;

// 비트맵을 캐시하고 관리하는 기능을 제공
public class BitmapCache<T extends Bitmap> implements IBitmapCache<T>
{
    private BitmapLoader<T> loader; // 비트맵 로더
    private final BitmapScaler<T> scaler; // 비트맵 스케일러
    private final ReLruCache<T> cache; // 비트맵 캐시

    public BitmapCache(
        BitmapLoader<T> loader,
        BitmapScaler<T> scaler,
        long cacheSize
    )
    {
        this.loader = loader;
        this.scaler = scaler;
        this.cache = new ReLruCache<T>( cacheSize ); // 캐시 생성
    }

    @Override
    public T get( String fileName, int tileSize )
    {
        T ret = cache.get( fileName + tileSize ); // 캐시에서 비트맵 가져오기

        if ( ret == null )
        {
            ret = loadBitmap( fileName, tileSize ); // 비트맵 로드
            cache.put( fileName + tileSize, ret ); // 캐시에 비트맵 저장
        }

        return ret;
    }

    @Override
    public void recycle()
    {
        cache.recycle(); // 캐시 재활용
    }

    @Override
    public void setLoader( BitmapLoader<T> loader )
    {
        this.loader = loader; // 비트맵 로더 설정
    }

    private T loadBitmap( String fileName, int tileSize )
    {
        // TODO: move this logic into loader - some loaders
        //       (including android?) can scale as they load,
        //       meanng we have less garbage to collect.

        int unscaledSize = loader.sizeFor( tileSize );
        T unscaled = loader.load( fileName, unscaledSize );

        if ( unscaledSize == tileSize )
        {
            return unscaled;
        }
        else
        {
            double scale = tileSize / ( double )unscaledSize;
            T ret = scaler.scale( unscaled, scale );
            unscaled.recycle();
            return ret;
        }
    }
}

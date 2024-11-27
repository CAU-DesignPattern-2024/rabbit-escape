package rabbitescape.render;

import rabbitescape.render.androidlike.Bitmap;

// 비트맵을 캐시에서 가져오거나 캐시를 재활용하는 메서드를 정의
public interface IBitmapCache<T extends Bitmap> {
    T get(String fileName, int tileSize); // 파일 이름과 타일 크기를 기반으로 비트맵을 가져옴
    void recycle(); // 캐시를 재활용
    void setLoader(BitmapLoader<T> loader); // 비트맵 로더를 설정
}

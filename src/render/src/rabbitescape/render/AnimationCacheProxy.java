package rabbitescape.render;

import java.util.HashMap;
import java.util.Map;

// 실제 애니메이션 캐시와 상호작용하며 캐시 히트 및 미스를 추적
public class AnimationCacheProxy implements IAnimationCache {
    private final IAnimationCache realCache; // 실제 애니메이션 캐시
    private final Map<String, Animation> lazyLoadCache; // 지연 로드 캐시
    private long totalHits = 0; // 캐시 히트 횟수
    private long totalMisses = 0; // 캐시 미스 횟수
    
    public AnimationCacheProxy(AnimationLoader loader) {
        this.realCache = new AnimationCache(loader);
        this.lazyLoadCache = new HashMap<>();
    }
    
    @Override
    public Animation get(String animationName) {
        if (lazyLoadCache.containsKey(animationName)) {
            totalHits++; // 캐시 히트 증가
            return lazyLoadCache.get(animationName); // 지연 로드 캐시에서 애니메이션 가져오기
        }
        
        Animation animation = realCache.get(animationName); // 실제 애니메이션 캐시에서 애니메이션 가져오기
        if (animation != null) {
            lazyLoadCache.put(animationName, animation); // 지연 로드 캐시에 애니메이션 저장
            totalHits++; // 캐시 히트 증가
        } else {
            totalMisses++; // 캐시 미스 증가
        }
        return animation;
    }
    
    @Override
    public String[] listAll() {
        return realCache.listAll(); // 실제 애니메이션 캐시에서 모든 애니메이션 가져오기
    }
    
    public double getHitRate() {
        long total = totalHits + totalMisses;
        return total == 0 ? 0.0 : (double) totalHits / total;
    }
    
    public void clearCache() {
        lazyLoadCache.clear(); // 지연 로드 캐시 초기화
    }
}

package rabbitescape.render;

import java.util.HashMap;
import java.util.Map;

public class AnimationCacheProxy implements IAnimationCache {
    private final IAnimationCache realCache;
    private final Map<String, Animation> lazyLoadCache;
    private long totalHits = 0;
    private long totalMisses = 0;
    
    public AnimationCacheProxy(AnimationLoader loader) {
        this.realCache = new AnimationCache(loader);
        this.lazyLoadCache = new HashMap<>();
    }
    
    @Override
    public Animation get(String animationName) {
        if (lazyLoadCache.containsKey(animationName)) {
            totalHits++;
            return lazyLoadCache.get(animationName);
        }
        
        Animation animation = realCache.get(animationName);
        if (animation != null) {
            lazyLoadCache.put(animationName, animation);
            totalHits++;
        } else {
            totalMisses++;
        }
        return animation;
    }
    
    @Override
    public String[] listAll() {
        return realCache.listAll();
    }
    
    public double getHitRate() {
        long total = totalHits + totalMisses;
        return total == 0 ? 0.0 : (double) totalHits / total;
    }
    
    public void clearCache() {
        lazyLoadCache.clear();
    }
}

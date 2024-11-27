package rabbitescape.render;

import java.util.HashMap;
import java.util.Map;

public class AnimationCacheProxy implements IAnimationCache {
    private final IAnimationCache realCache;
    private final Map<String, Animation> lazyLoadCache;
    
    public AnimationCacheProxy(AnimationLoader loader) {
        this.realCache = new AnimationCache(loader);
        this.lazyLoadCache = new HashMap<>();
    }
    
    @Override
    public Animation get(String animationName) {
        if (!lazyLoadCache.containsKey(animationName)) {
            Animation animation = realCache.get(animationName);
            if (animation != null) {
                lazyLoadCache.put(animationName, animation);
            }
            return animation;
        }
        return lazyLoadCache.get(animationName);
    }
    
    @Override
    public String[] listAll() {
        return realCache.listAll();
    }
}

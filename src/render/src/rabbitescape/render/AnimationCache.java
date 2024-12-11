package rabbitescape.render;

import java.util.HashMap;
import java.util.Map;

public class AnimationCache implements IAnimationCache 
{
    private final String[] names;
    private final Map<String, Animation> animations;

    public AnimationCache( AnimationLoader animationLoader )
    {
        this.names = AnimationLoader.listAll();
        this.animations = new HashMap<>();
    }

    @Override
    public String[] listAll()
    {
        return names;
    }

    @Override
    public Animation get(String animationName) {
        // 애니메이션이 없으면 로딩 후 캐시에 추가
        if (!animations.containsKey(animationName)) {
            Animation animation = AnimationLoader.load(animationName);
            animations.put(animationName, animation);
        }
        return animations.get(animationName);
    }
}

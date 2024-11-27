package rabbitescape.render;

public interface IAnimationCache {
    Animation get(String animationName);
    String[] listAll();
}

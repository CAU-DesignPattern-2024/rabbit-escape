package rabbitescape.render;

// 애니메이션을 캐시에서 가져오거나 모든 애니메이션을 나열하는 메서드를 정의
public interface IAnimationCache {
    Animation get(String animationName); // 애니메이션 이름을 기반으로 애니메이션을 가져옴
    String[] listAll(); // 모든 애니메이션 이름을 나열
}

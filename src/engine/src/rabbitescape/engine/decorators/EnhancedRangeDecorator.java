package rabbitescape.engine.decorators;

import rabbitescape.engine.*;

public class EnhancedRangeDecorator extends TokenDecorator {
    private final int rangeBonus;

    public EnhancedRangeDecorator(TokenComponent token, int rangeBonus) {
        super(token);
        this.rangeBonus = rangeBonus;
    }

    @Override
    public void behave(World world, int x, int y) {
        // 기존 동작 수행
        super.behave(world, x, y);
        
        // 향상된 범위 효과 적용
        applyEnhancedRange(world, x, y);
    }

    private void applyEnhancedRange(World world, int x, int y) {
        // 토큰의 효과 범위를 증가시키는 로직 구현
        // 예: bridge 토큰의 경우 더 긴 다리를 만들 수 있음
    }
}

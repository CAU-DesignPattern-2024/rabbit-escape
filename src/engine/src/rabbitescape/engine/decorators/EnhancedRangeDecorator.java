package rabbitescape.engine.decorators;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;

public class EnhancedRangeDecorator extends TokenDecorator {
    private final int rangeBonus;

    public EnhancedRangeDecorator(TokenComponent token, int x, int y, State state, int rangeBonus) {
        super(token, x, y, state);
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
        Type tokenType = getType();
        if (tokenType == Type.bridge) {
            // bridge 토큰의 경우 rangeBonus만큼 더 긴 다리를 만듦
            for (int i = 1; i <= rangeBonus; i++) {
                // 추가 다리 생성 로직
            }
        }
    }

    @Override
    public String overlayText() {
        return decoratedToken.overlayText();
    }

    @Override
    public void step(World world) {
        if (decoratedToken instanceof Thing) {
            ((Thing) decoratedToken).step(world);
        }
    }

    @Override
    public void restoreFromState(Map<String, String> state) {
        if (decoratedToken instanceof Thing) {
            ((Thing) decoratedToken).restoreFromState(state);
        }
    }

    @Override
    public Map<String, String> saveState(boolean runtimeMeta) {
        if (decoratedToken instanceof Thing) {
            return ((Thing) decoratedToken).saveState(runtimeMeta);
        }
        return null;
    }

    @Override
    public void accept(ThingVisitor visitor) {
        if (decoratedToken instanceof Thing) {
            ((Thing) decoratedToken).accept(visitor);
        }
    }
}

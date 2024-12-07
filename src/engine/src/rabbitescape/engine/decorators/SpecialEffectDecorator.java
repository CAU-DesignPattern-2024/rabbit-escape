package rabbitescape.engine.decorators;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;

public class SpecialEffectDecorator extends TokenDecorator {
    private final String effectType;

    public SpecialEffectDecorator(TokenComponent token, int x, int y, State state, String effectType) {
        super(token, x, y, state);
        this.effectType = effectType;
    }

    @Override
    public void behave(World world, int x, int y) {
        super.behave(world, x, y);
        applySpecialEffect(world, x, y);
    }

    private void applySpecialEffect(World world, int x, int y) {
        // 특수 효과 적용 로직 구현
        // 예: 폭발 효과, 얼음 효과 등
        Type tokenType = getType();
        switch (effectType) {
            case "explosion":
                // 폭발 효과 구현
                if (tokenType == Type.explode) {
                    // 폭발 범위 확대 등의 로직
                }
                break;
            case "freeze":
                // 얼음 효과 구현
                break;
            // 추가 효과들...
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
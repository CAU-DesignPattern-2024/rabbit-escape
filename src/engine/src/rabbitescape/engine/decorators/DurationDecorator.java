package rabbitescape.engine.decorators;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;
import java.util.Map;

public class DurationDecorator extends TokenDecorator {
    private int duration;

    public DurationDecorator(TokenComponent token, int x, int y, State state, int duration) {
        super(token, x, y, state);
        this.duration = duration;
    }

    @Override
    public void behave(World world, int x, int y) {
        if (duration > 0) {
            super.behave(world, x, y);
            duration--;
        }
    }

    public int getRemainingDuration() {
        return duration;
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

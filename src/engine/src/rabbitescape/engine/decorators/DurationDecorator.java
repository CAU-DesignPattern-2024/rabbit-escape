package rabbitescape.engine.decorators;

import rabbitescape.engine.*;

public class DurationDecorator extends TokenDecorator {
    private int duration;

    public DurationDecorator(TokenComponent token, int duration) {
        super(token);
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
}

package rabbitescape.engine.behaviours.walk;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Blocking;

import static rabbitescape.engine.ChangeDescription.State.*;

public class UpSlopeHandler extends StateHandler {

    public UpSlopeHandler(BehaviourTools t) {
        super(t);
    }

    @Override
    protected State process() {
        if (t.isOnUpSlope()) {
            Block aboveNext = t.blockAboveNext();
            Block above = t.blockAbove();
            int nextX = t.nextX();
            int nextY = t.rabbit.y - 1;

            if (t.isWall(aboveNext) || Blocking.blockerAt(t.world, nextX, nextY) || t.isRoof(above)
                    || (t.isCresting() && Blocking.blockerAt(t.world, nextX, t.rabbit.y))) {
                return rl(RABBIT_TURNING_RIGHT_TO_LEFT_RISING, RABBIT_TURNING_LEFT_TO_RIGHT_RISING);
            } else if (t.isUpSlope(aboveNext)) {
                return rl(RABBIT_RISING_RIGHT_CONTINUE, RABBIT_RISING_LEFT_CONTINUE);
            } else if (t.isDownSlope(t.blockNext())) {
                return rl(RABBIT_RISING_AND_LOWERING_RIGHT, RABBIT_RISING_AND_LOWERING_LEFT);
            } else {
                return rl(RABBIT_RISING_RIGHT_END, RABBIT_RISING_LEFT_END);
            }
        }
        return null; // Not on up slope, move to next handler
    }

    private State rl(State rightState, State leftState) {
        return t.rl(rightState, leftState);
    }
}

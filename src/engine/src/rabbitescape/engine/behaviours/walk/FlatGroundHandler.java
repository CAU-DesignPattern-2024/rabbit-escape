package rabbitescape.engine.behaviours.walk;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Blocking;

import static rabbitescape.engine.ChangeDescription.State.*;

public class FlatGroundHandler extends StateHandler {

    public FlatGroundHandler(BehaviourTools t) {
        super(t);
    }

    @Override
    protected State process() {
        if (!t.isOnUpSlope() && !t.isOnDownSlope()) {
            int nextX = t.nextX();
            int nextY = t.rabbit.y;
            Block next = t.blockNext();

            if (t.isWall(next) || Blocking.blockerAt(t.world, nextX, nextY)) {
                return rl(RABBIT_TURNING_RIGHT_TO_LEFT, RABBIT_TURNING_LEFT_TO_RIGHT);
            } else if (t.isUpSlope(next)) {
                return rl(RABBIT_RISING_RIGHT_START, RABBIT_RISING_LEFT_START);
            } else if (t.isDownSlope(t.blockBelowNext())) {
                if (Blocking.blockerAt(t.world, nextX, t.rabbit.y + 1)) {
                    return rl(RABBIT_TURNING_RIGHT_TO_LEFT, RABBIT_TURNING_LEFT_TO_RIGHT);
                } else {
                    return rl(RABBIT_LOWERING_RIGHT_START, RABBIT_LOWERING_LEFT_START);
                }
            } else {
                return rl(RABBIT_WALKING_RIGHT, RABBIT_WALKING_LEFT);
            }
        }
        return null; // Not flat ground, move to next handler
    }

    private State rl(State rightState, State leftState) {
        return t.rl(rightState, leftState);
    }
}

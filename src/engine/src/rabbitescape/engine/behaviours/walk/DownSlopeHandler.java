package rabbitescape.engine.behaviours.walk;

import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.Blocking;
import rabbitescape.engine.behaviours.StateHandler;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.BehaviourTools;

public class DownSlopeHandler extends StateHandler {

    public DownSlopeHandler(BehaviourTools t, StateHandler next) {
        super(t, next);
    }

    @Override
    protected boolean canHandle() {
        return t.isOnDownSlope();
    }

    @Override
    protected State computeState() {
        int nextX = t.nextX();
        int nextY = t.rabbit.y + 1;
        Block next = t.blockNext();
        Block belowNext = t.blockBelowNext();

        if (t.isWall(next) || Blocking.blockerAt(t.world, nextX, nextY)
            || (t.isValleying() && Blocking.blockerAt(t.world, nextX, t.rabbit.y))) {
            return rl(RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING, RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING);
        } else if (t.isUpSlope(next)) {
            return rl(RABBIT_LOWERING_AND_RISING_RIGHT, RABBIT_LOWERING_AND_RISING_LEFT);
        } else if (t.isDownSlope(belowNext)) {
            return rl(RABBIT_LOWERING_RIGHT_CONTINUE, RABBIT_LOWERING_LEFT_CONTINUE);
        } else {
            return rl(RABBIT_LOWERING_RIGHT_END, RABBIT_LOWERING_LEFT_END);
        }
    }

    private State rl(State rightState, State leftState) {
        return t.rl(rightState, leftState);
    }
}

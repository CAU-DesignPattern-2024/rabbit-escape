package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;

public class BaseToken implements TokenComponent {
    protected int x;
    protected int y;
    protected Type type;
    protected State state;

    public BaseToken(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.state = switchType(type, false, false, true);
    }

    @Override
    public void behave(World world, int x, int y) {
        // 기본 동작 구현
        Block onBlock = world.getBlockAt(x, y);
        Block belowBlock = world.getBlockAt(x, y + 1);
        boolean still = (
            BehaviourTools.s_isFlat(belowBlock)
            || (onBlock != null)
            || BridgeTools.someoneIsBridgingAt(world, x, y)
        );

        state = switchType(type, !still,
            BehaviourTools.isSlope(belowBlock),
            BehaviourTools.isSlope(onBlock));
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    protected State switchType(Type type, boolean moving, boolean slopeBelow, boolean onSlope) {
        switch (type) {
            case bash:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_BASH_FALLING, TOKEN_BASH_STILL,
                    TOKEN_BASH_FALL_TO_SLOPE, TOKEN_BASH_ON_SLOPE);

            case dig:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_DIG_FALLING, TOKEN_DIG_STILL,
                    TOKEN_DIG_FALL_TO_SLOPE, TOKEN_DIG_ON_SLOPE);

            case bridge:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_BRIDGE_FALLING, TOKEN_BRIDGE_STILL,
                    TOKEN_BRIDGE_FALL_TO_SLOPE, TOKEN_BRIDGE_ON_SLOPE);

            case block:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_BLOCK_FALLING, TOKEN_BLOCK_STILL,
                    TOKEN_BLOCK_FALL_TO_SLOPE, TOKEN_BLOCK_ON_SLOPE);

            case climb:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_CLIMB_FALLING, TOKEN_CLIMB_STILL,
                    TOKEN_CLIMB_FALL_TO_SLOPE, TOKEN_CLIMB_ON_SLOPE);

            case explode:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_EXPLODE_FALLING, TOKEN_EXPLODE_STILL,
                    TOKEN_EXPLODE_FALL_TO_SLOPE, TOKEN_EXPLODE_ON_SLOPE);

            case brolly:
                return chooseState(moving, slopeBelow, onSlope,
                    TOKEN_BROLLY_FALLING, TOKEN_BROLLY_STILL,
                    TOKEN_BROLLY_FALL_TO_SLOPE, TOKEN_BROLLY_ON_SLOPE);

            default:
                throw new Token.UnknownType(type);
        }
    }

    private State chooseState(
        boolean moving,
        boolean slopeBelow,
        boolean onSlope,
        State falling,
        State onFlat,
        State fallingToSlope,
        State onSlopeState
    ) {
        if (onSlope) {
            return onSlopeState;
        }
        if (!moving) {
            return onFlat;
        }
        if (slopeBelow) {
            return fallingToSlope;
        }
        return falling;
    }
}

package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;

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

    protected State switchType(Type type, boolean moving, boolean falling, boolean onSlope) {
        switch (type) {
            case bash:
                return moving ? TOKEN_BASH_FALLING : (falling ? TOKEN_BASH_FALLING : (onSlope ? TOKEN_BASH_ON_SLOPE : TOKEN_BASH));
            case dig:
                return moving ? TOKEN_DIG_FALLING : (falling ? TOKEN_DIG_FALLING : (onSlope ? TOKEN_DIG_ON_SLOPE : TOKEN_DIG));
            case bridge:
                return moving ? TOKEN_BRIDGE_FALLING : (falling ? TOKEN_BRIDGE_FALLING : (onSlope ? TOKEN_BRIDGE_ON_SLOPE : TOKEN_BRIDGE));
            case block:
                return moving ? TOKEN_BLOCK_FALLING : (falling ? TOKEN_BLOCK_FALLING : (onSlope ? TOKEN_BLOCK_ON_SLOPE : TOKEN_BLOCK));
            case climb:
                return moving ? TOKEN_CLIMB_FALLING : (falling ? TOKEN_CLIMB_FALLING : (onSlope ? TOKEN_CLIMB_ON_SLOPE : TOKEN_CLIMB));
            case explode:
                return moving ? TOKEN_EXPLODE_FALLING : (falling ? TOKEN_EXPLODE_FALLING : (onSlope ? TOKEN_EXPLODE_ON_SLOPE : TOKEN_EXPLODE));
            case brolly:
                return moving ? TOKEN_BROLLY_FALLING : (falling ? TOKEN_BROLLY_FALLING : (onSlope ? TOKEN_BROLLY_ON_SLOPE : TOKEN_BROLLY));
            default:
                throw new Token.UnknownType(type);
        }
    }
}

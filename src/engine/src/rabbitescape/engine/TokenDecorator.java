package rabbitescape.engine;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;
import rabbitescape.engine.TokenComponent;
import rabbitescape.engine.World;
import rabbitescape.engine.Thing;

/**
 * TokenDecorator는 모든 데코레이터의 기본 클래스입니다.
 */
public abstract class TokenDecorator extends Thing implements TokenComponent {
    protected TokenComponent decoratedToken;

    public TokenDecorator(TokenComponent token, int x, int y, State state) {
        super(x, y, state);
        this.decoratedToken = token;
    }

    @Override
    public void behave(World world, int x, int y) {
        decoratedToken.behave(world, x, y);
    }

    @Override
    public void calcNewState(World world) {
        // Thing의 추상 메서드 구현
        // 데코레이터 패턴에 맞게 위임
        if (decoratedToken instanceof Thing) {
            ((Thing)decoratedToken).calcNewState(world);
        }
    }

    @Override
    public State getState() {
        return decoratedToken.getState();
    }

    @Override
    public Type getType() {
        return decoratedToken.getType();
    }

    @Override
    public int getX() {
        return decoratedToken.getX();
    }

    @Override
    public int getY() {
        return decoratedToken.getY();
    }

    @Override
    public void setX(int x) {
        decoratedToken.setX(x);
    }

    @Override
    public void setY(int y) {
        decoratedToken.setY(y);
    }
}

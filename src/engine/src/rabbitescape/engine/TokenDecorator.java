package rabbitescape.engine;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;
import rabbitescape.engine.TokenComponent;
import rabbitescape.engine.World;

/**
 * TokenDecorator는 모든 데코레이터의 기본 클래스입니다.
 */
public abstract class TokenDecorator implements TokenComponent {
    protected TokenComponent decoratedToken;

    public TokenDecorator(TokenComponent token) {
        this.decoratedToken = token;
    }

    @Override
    public void behave(World world, int x, int y) {
        decoratedToken.behave(world, x, y);
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

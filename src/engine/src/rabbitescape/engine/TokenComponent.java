package rabbitescape.engine;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Token.Type;

/**
 * TokenComponent 인터페이스는 모든 토큰의 기본 동작을 정의합니다.
 */
public interface TokenComponent extends ShownOnOverlay {
    void behave(World world, int x, int y);
    State getState();
    Token.Type getType();
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    // Thing 클래스의 필수 메서드 추가
    void calcNewState(World world);
}

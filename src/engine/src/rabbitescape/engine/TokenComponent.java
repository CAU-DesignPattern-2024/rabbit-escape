package rabbitescape.engine;

/**
 * TokenComponent 인터페이스는 모든 토큰의 기본 동작을 정의합니다.
 */
public interface TokenComponent {
    void behave(World world, int x, int y);
    State getState();
    Type getType();
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
}

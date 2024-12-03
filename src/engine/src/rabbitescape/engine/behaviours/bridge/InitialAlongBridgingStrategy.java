package rabbitescape.engine.behaviours.bridge;

import rabbitescape.engine.World;
import rabbitescape.engine.Rabbit;

public class InitialAlongBridgingStrategy extends BaseBridgingStrategy {
    @Override
    public boolean execute(World world, Rabbit rabbit) {
        return true;  // 초기 상태는 단순히 bridgeType만 설정
    }
}

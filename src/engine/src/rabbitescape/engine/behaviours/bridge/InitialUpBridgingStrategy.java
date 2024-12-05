package rabbitescape.engine.behaviours.bridge;

import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.Bridging.BridgeType;
import rabbitescape.engine.Rabbit;

public class InitialUpBridgingStrategy extends BaseBridgingStrategy implements BridgeTypeProvider {
    @Override
    public boolean execute(World world, Rabbit rabbit) {
        return true;
    }
    
    @Override
    public BridgeType getBridgeType() {
        return BridgeType.UP;
    }
}

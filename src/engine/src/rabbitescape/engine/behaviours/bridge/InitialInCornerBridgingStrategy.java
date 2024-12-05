package rabbitescape.engine.behaviours.bridge;

import rabbitescape.engine.World;
import rabbitescape.engine.Rabbit;

public class InitialInCornerBridgingStrategy extends BaseBridgingStrategy {
    @Override
    public boolean execute(World world, Rabbit rabbit) {
        return true;
    }
}

package rabbitescape.engine.behaviours.bridge;

import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import rabbitescape.engine.World;
import rabbitescape.engine.Rabbit;

public class InCornerUpLeftBridgingStrategy extends BaseBridgingStrategy {
    @Override
    public boolean execute(World world, Rabbit rabbit) {
        rabbit.onSlope = true;
        rabbit.y--;
        world.changes.addBlock(createBridgeBlock(rabbit.x, rabbit.y, BRIDGE_UP_LEFT));
        return true;
    }
}
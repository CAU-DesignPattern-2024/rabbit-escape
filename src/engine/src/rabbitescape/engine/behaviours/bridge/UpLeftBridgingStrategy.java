package rabbitescape.engine.behaviours.bridge;

import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import rabbitescape.engine.World;
import rabbitescape.engine.Rabbit;

public class UpLeftBridgingStrategy extends BaseBridgingStrategy {
    @Override
    public boolean execute(World world, Rabbit rabbit) {
        rabbit.x--;
        rabbit.y--;
        world.changes.addBlock(createBridgeBlock(rabbit.x, rabbit.y, BRIDGE_UP_LEFT));
        return true;
    }
}

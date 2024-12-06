package rabbitescape.engine.behaviours.bridge;

import static rabbitescape.engine.Block.Material.EARTH;
import rabbitescape.engine.Block;
import rabbitescape.engine.Block.Shape;

// Base class for bridging strategies
public abstract class BaseBridgingStrategy implements BridgingStrategy {
    protected Block createBridgeBlock(int x, int y, Shape shape) {
        return new Block(x, y, EARTH, shape, 0);
    }
}

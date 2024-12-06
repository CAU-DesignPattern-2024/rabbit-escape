package rabbitescape.engine.behaviours.bridge;

import rabbitescape.engine.World;
import rabbitescape.engine.Rabbit;

// Interface for bridging strategies
public interface BridgingStrategy {
    boolean execute(World world, Rabbit rabbit);
}

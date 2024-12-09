package rabbitescape.engine.commands;

import java.util.List;

import rabbitescape.engine.Block;
import rabbitescape.engine.World;
import rabbitescape.engine.World.NoBlockFound;
import rabbitescape.engine.util.Position;

public class RemoveBlockAtCommand implements Command {

    private final World world;
    private final int x;
    private final int y;

    public RemoveBlockAtCommand(World world, int x, int y) {
        this.world = world;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        Block block = world.getBlockAt(x, y);
        if (block == null) {
            throw new NoBlockFound(x, y);
        }

        world.blockTable.removeAll(List.of(block));
        world.recalculateWaterRegions(new Position(x, y));
    }

}

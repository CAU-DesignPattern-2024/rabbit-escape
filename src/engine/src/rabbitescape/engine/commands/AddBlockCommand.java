package rabbitescape.engine.commands;

import rabbitescape.engine.Block;
import rabbitescape.engine.World;
import rabbitescape.engine.util.Position;

public class AddBlockCommand implements Command {

    private final World world;
    private final Block block;

    public AddBlockCommand(World world, Block block) {
        this.world = world;
        this.block = block;
    }

    @Override
    public void execute() {
        // Add the block to the world
        world.blockTable.add(block);

        // Recalculate water regions for the block's position
        Position position = new Position(block.x, block.y);
        world.recalculateWaterRegions(position);
    }


}

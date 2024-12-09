package rabbitescape.engine.commands;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.World;

public class ExplodeAllCommand implements Command {

    private final World world;

    public ExplodeAllCommand(World world) {
        this.world = world;
    }

    @Override
    public void execute() {
        // Update the number of waiting rabbits to 0
        world.num_waiting = 0;

        // Set all rabbits' state to exploding
        for (Rabbit rabbit : world.rabbits) {
            rabbit.state = State.RABBIT_EXPLODING;
        }
    }

}

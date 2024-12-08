package rabbitescape.engine.commands;

import rabbitescape.engine.Rabbit;
import rabbitescape.engine.World;

public class SaveRabbitCommand implements Command {

    private final World world;
    private final Rabbit rabbit;

    public SaveRabbitCommand(World world, Rabbit rabbit) {
        this.world = world;
        this.rabbit = rabbit;
    }

    @Override
    public void execute() {
        // Increment the saved counter
        ++world.num_saved;

        // Remove the rabbit from the world and add it to the saved list
        world.rabbits.remove(rabbit);
    }

    public void undo() {
        // Decrement the saved counter
        --world.num_saved;

        // Add the rabbit back to the world
        world.rabbits.add(rabbit);
    }

}

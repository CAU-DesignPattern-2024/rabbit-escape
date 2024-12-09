package rabbitescape.engine.commands;

import rabbitescape.engine.Rabbit;
import rabbitescape.engine.World;

public class KillRabbitCommand implements Command {

    private final World world;
    private final Rabbit rabbit;
    
    public KillRabbitCommand(World world, Rabbit rabbit) {
        this.world = world;
        this.rabbit = rabbit;
    }

    @Override
    public void execute() {
        // Increment the killed counter if the rabbit is of type RABBIT
        if (rabbit.type == Rabbit.Type.RABBIT) {
            ++world.num_killed;
        }

        // Remove the rabbit from the world
        world.rabbits.remove(rabbit);
    }

}

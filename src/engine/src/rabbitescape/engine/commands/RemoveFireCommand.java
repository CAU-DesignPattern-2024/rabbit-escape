package rabbitescape.engine.commands;

import rabbitescape.engine.Fire;
import rabbitescape.engine.World;

public class RemoveFireCommand implements Command {

    private final World world;
    private final Fire fire;

    public RemoveFireCommand(World world, Fire fire) {
        this.world = world;
        this.fire = fire;
    }

    @Override
    public void execute() {
        world.things.remove(fire);
    }

}

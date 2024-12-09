package rabbitescape.engine.commands;

import rabbitescape.engine.Rabbit;
import rabbitescape.engine.World;

public class EnterRabbitCommand implements Command {
	
    private World world;
    private Rabbit rabbit;

    public EnterRabbitCommand(World world, Rabbit rabbit) {
        this.world = world;
        this.rabbit = rabbit;
    }

    @Override
    public void execute() {
    	--world.num_waiting;
   	 	world.rabbits.add(rabbit);
    	rabbit.calcNewState(world);
    }
    
    public Rabbit getRabbit() {
    	return rabbit;
    }

}

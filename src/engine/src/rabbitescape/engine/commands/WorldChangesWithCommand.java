package rabbitescape.engine.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rabbitescape.engine.Block;
import rabbitescape.engine.Fire;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.Thing;
import rabbitescape.engine.Token;
import rabbitescape.engine.World;
import rabbitescape.engine.World.UnableToAddToken;
import rabbitescape.engine.util.Position;
import rabbitescape.engine.WorldChanges;
import rabbitescape.engine.WorldStatsListener;

public class WorldChangesWithCommand extends WorldChanges
{

    private final World world;
    private final List<Command> commandQueue;
    private int saveCount=0;

    public WorldChangesWithCommand(World world, WorldStatsListener statsListener )
    {
		super(world, statsListener);
		this.world = world;
		this.commandQueue = new ArrayList<>();
    }

    @Override
    public synchronized void apply()
    {
    	boolean existsSave = false;
    	if(explodeAll) explodeAllRabbits();
    	Iterator<Command> iterator = commandQueue.iterator();
    	while (iterator.hasNext()) {
    	    Command command = iterator.next();
    	    command.execute();
    	    if(command instanceof SaveRabbitCommand) existsSave = true;
    	    iterator.remove(); 
    	}
    	if(existsSave) updateStats();
    	tokensToRemove.clear();
    }

    private synchronized void addCommand(Command command) {
        commandQueue.add(command);
    }

    @Override
    public synchronized void revert()
    {
    	commandQueue.clear();
    	world.num_saved-=saveCount;
    	saveCount = 0;
    }

    @Override
    public synchronized void enterRabbit( Rabbit rabbit )
    {
    	addCommand(new EnterRabbitCommand(world, rabbit));
    }

    @Override
    public synchronized void killRabbit( Rabbit rabbit )
    {
    	addCommand(new KillRabbitCommand(world, rabbit));
    }

    @Override
    public synchronized void saveRabbit( Rabbit rabbit )
    {
    	saveCount++;
    	addCommand(new SaveRabbitCommand(world, rabbit));
    }

    @Override
    public synchronized void addToken( int x, int y, Token.Type type )
    throws UnableToAddToken
    {
        Integer numLeft = world.abilities.get(type);

        if (numLeft == null) {
            throw new World.NoSuchAbilityInThisWorld(type);
        }

        if (numLeft == 0) {
            throw new World.NoneOfThisAbilityLeft(type);
        }
        
        if (x < 0 || y < 0 || x >= world.size.width || y >= world.size.height) {
            throw new World.CantAddTokenOutsideWorld(type, x, y, world.size);
        }

    	addCommand(new AddTokenCommand(world, x, y, type));
    }

    @Override
    public synchronized void removeToken( Token thing )
    {
    	tokensToRemove.add(thing);
    	addCommand(new RemoveTokenCommand(world, thing));
    }

    @Override
    public synchronized void removeFire( Fire thing )
    {
    	addCommand(new RemoveFireCommand(world, thing));
    }

    @Override
    public synchronized void addBlock( Block block )
    {
    	addCommand(new AddBlockCommand(world, block));
    }

    @Override
    public synchronized void removeBlockAt( int x, int y )
    {
       blocksJustRemoved.add( new Position( x, y ) );
    	addCommand(new RemoveBlockAtCommand(world, x, y));
    }

    @Override
    public synchronized List<Thing> tokensAboutToAppear()
    {
    	List<Thing> tokensToAdd = new ArrayList<>();
    	
    	for(Command cmd : commandQueue) {
    		if(cmd instanceof AddTokenCommand) tokensToAdd.add(((AddTokenCommand) cmd).getToken());
    	}
    	
        return tokensToAdd;
    }

    @Override
    public synchronized void explodeAllRabbits()
    {
    	explodeAll = true;
    	addCommand(new ExplodeAllCommand(world));
    }

    @Override
    public List<Rabbit> rabbitsJustEntered()
    {
        return rabbitsJustEntered;
    }

    @Override
    public void rememberWhatWillHappen()
    {
    	List<Rabbit> rabbitsToEnter = new ArrayList<>();
    	
    	for(Command cmd : commandQueue) {
    		if(cmd instanceof EnterRabbitCommand) rabbitsToEnter.add(((EnterRabbitCommand) cmd).getRabbit());
    	}
    	
        rabbitsJustEntered = rabbitsToEnter;
    }
}

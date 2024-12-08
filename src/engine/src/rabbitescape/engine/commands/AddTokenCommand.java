package rabbitescape.engine.commands;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.Token;
import rabbitescape.engine.World;

public class AddTokenCommand implements UndoableCommand {

    private final World world;
    private final Token.Type type;
    private Token token;
    private final int x, y;

    public AddTokenCommand(World world, int x, int y, Token.Type type) {
        this.world = world;
        this.type = type;
        this.x = x;
        this.y = y;
        token = new Token(x, y, type, world);
    }
	
	@Override
	public void execute() {
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

        Block block = world.getBlockAt(x, y);
        if (BehaviourTools.s_isFlat(block)) {
            return;
        }
        
        world.abilities.put(type, numLeft - 1);
        System.out.println(""+token);
        world.things.add(token);
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}
	
	public Token getToken() {
		return this.token;
	}

}

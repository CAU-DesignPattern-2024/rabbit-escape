package rabbitescape.engine.commands;

import rabbitescape.engine.Token;
import rabbitescape.engine.World;

public class RemoveTokenCommand implements Command {

    private final World world;
    private final Token token;

    public RemoveTokenCommand(World world, Token token) {
        this.world = world;
        this.token = token;
    }

    @Override
    public void execute() {
        world.things.remove(token);
    }

}

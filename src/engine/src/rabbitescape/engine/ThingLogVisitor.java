package rabbitescape.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThingLogVisitor implements ThingVisitor {
	
    private static final Logger logger = Logger.getLogger(ThingLogVisitor.class.getName());

    private static ThingLogVisitor instance;

    private ThingLogVisitor() {
    }

    public static ThingLogVisitor getInstance() {
        if (instance == null) {
            synchronized (ThingLogVisitor.class) {
                if (instance == null) {
                    instance = new ThingLogVisitor();
                }
            }
        }
        return instance;
    }

    @Override
    public void visit(Rabbit rabbit) {
    	logger.log(Level.INFO, String.format("Logging Rabbit: %d, Position: (%d, %d), Direction: %s, State: %s", 
                rabbit.index, rabbit.x, rabbit.y, rabbit.dir, rabbit.stateName()));
    }

	@Override
	public void visit(Entrance token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Exit exit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Fire fire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Pipe pipe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Token token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WaterRegion waterRegion) {
		// TODO Auto-generated method stub
		
	}


}
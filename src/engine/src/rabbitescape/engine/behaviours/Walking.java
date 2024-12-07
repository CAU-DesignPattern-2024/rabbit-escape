package rabbitescape.engine.behaviours;

import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.Block.Shape.*;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.walk.DownSlopeHandler;

public class Walking extends Behaviour
{
    @Override
    public void cancel()
    {
    }
    
    private static class StateCalc {

        private final BehaviourTools t;

        StateCalc(BehaviourTools t) {
            this.t = t;
        }

        public State newState() {
            // chain 순서: Flat → UpSlope → DownSlope
            StateHandler flatHandler = new FlatGroundHandler(t);
            StateHandler upSlopeHandler = new UpSlopeHandler(t);
            StateHandler downSlopeHandler = new DownSlopeHandler(t);

            flatHandler.setNext(upSlopeHandler);
            upSlopeHandler.setNext(downSlopeHandler);

            return flatHandler.handle();
        }
    }

    @Override
    public boolean checkTriggered( Rabbit rabbit, World world )
    {
        return false; // To avoid cancelling other behaviours, return false
    }

    @Override
    public State newState(BehaviourTools t, boolean triggered) {
        return new StateCalc(t).newState();
    }

    @Override
    @SuppressWarnings("fallthrough")
    public boolean behave( World world, Rabbit rabbit, State state )
    {
        switch ( state )
        {
            case RABBIT_WALKING_LEFT:
            {
                --rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_WALKING_RIGHT:
            {
                ++rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_LOWERING_LEFT_END:
            {
                --rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_RISING_LEFT_START:
            case RABBIT_LOWERING_AND_RISING_LEFT:
            case RABBIT_RISING_AND_LOWERING_LEFT:
            {
                --rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_LOWERING_RIGHT_END:
            {
                ++rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_RISING_RIGHT_START:
            case RABBIT_LOWERING_AND_RISING_RIGHT:
            case RABBIT_RISING_AND_LOWERING_RIGHT:
            {
                ++rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_RISING_LEFT_END:
            {
                --rabbit.y;
                --rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_RISING_LEFT_CONTINUE:
            {
                --rabbit.y;
                --rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_RISING_RIGHT_END:
            {
                --rabbit.y;
                ++rabbit.x;
                rabbit.onSlope = false;
                return true;
            }
            case RABBIT_RISING_RIGHT_CONTINUE:
            {
                --rabbit.y;
                ++rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_LOWERING_LEFT_CONTINUE:
            case RABBIT_LOWERING_LEFT_START:
            {
                ++rabbit.y;
                --rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_LOWERING_RIGHT_CONTINUE:
            case RABBIT_LOWERING_RIGHT_START:
            {
                ++rabbit.y;
                ++rabbit.x;
                rabbit.onSlope = true;
                return true;
            }
            case RABBIT_TURNING_LEFT_TO_RIGHT:
                rabbit.onSlope = false; // Intentional fall-through
            case RABBIT_TURNING_LEFT_TO_RIGHT_RISING:
            case RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING:
            {
                rabbit.dir = RIGHT;
                checkJumpOntoSlope( world, rabbit );
                return true;
            }
            case RABBIT_TURNING_RIGHT_TO_LEFT:
                rabbit.onSlope = false; // Intentional fall-through
            case RABBIT_TURNING_RIGHT_TO_LEFT_RISING:
            case RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING:
            {
                rabbit.dir = LEFT;
                checkJumpOntoSlope( world, rabbit );
                return true;
            }
            default:
            {
                throw new AssertionError(
                    "Should have handled all states in Walking or before,"
                    + " but we are in state " + state.name()
                );
            }
        }
    }



    /**
     * If we turn around near a slope, we jump onto it
     */
    private void checkJumpOntoSlope( World world, Rabbit rabbit )
    {
        Block thisBlock = world.getBlockAt( rabbit.x, rabbit.y );
        if ( isBridge( thisBlock ) )
        {
            Block aboveBlock = world.getBlockAt( rabbit.x, rabbit.y - 1 );
            if ( rabbit.onSlope && isBridge( aboveBlock ) )
            {
                rabbit.y--;
            }
            else
            {
                rabbit.onSlope = true;
            }
        }
    }

    private boolean isBridge( Block block )
    {
        return (
               block != null
            && (
                   block.shape == BRIDGE_UP_LEFT
                || block.shape == BRIDGE_UP_RIGHT
            )
        );
    }
}

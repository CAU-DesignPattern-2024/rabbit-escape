package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Token.Type.*;
import static rabbitescape.engine.Block.Material.*;
import static rabbitescape.engine.Block.Shape.*;

import java.util.Map;
import java.util.HashMap;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.bridge.*;

public class Bridging extends Behaviour
{
    public enum BridgeType
    {
        ALONG,
        UP,
        DOWN_UP
    }

    private int smallSteps = 0;
    private int bigSteps = 0;
    private BridgeType bridgeType = BridgeType.ALONG;

    private static Map<State, BridgingStrategy> strategies = new HashMap<>();

    static {
        initBridgeStrategies();
    }

    @Override
    public void cancel()
    {
        bigSteps = 0;
        smallSteps = 0;
    }

    @Override
    public boolean checkTriggered( Rabbit rabbit, World world )
    {
        nextStep();

        if ( bigSteps <= 0 )
            // Only pick up a token if we've finished, and we can bridge
        {
            BehaviourTools t = new BehaviourTools( rabbit, world );

            State possibleState = bridgingState( t, 3, 3, bridgeType );

            if ( possibleState != null ) // Only pick up if we can bridge
            {
                return t.pickUpToken( bridge );
            }
        }
        return false;
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            smallSteps = 3;
            bigSteps = 3;
        }

        State ret = bridgingState( t, bigSteps, smallSteps, bridgeType );

        if ( ret == null )
        {
            bigSteps = 0;
        }

        if ( bigSteps <= 0 )
        {
            smallSteps = 0;
            return null;   // Finished bridging
        }

        return ret;
    }

    private static State bridgingState(
        BehaviourTools t,
        int bs,
        int ss,
        BridgeType bt
    )
    {
        Block hereBlock = t.blockHere();

        Rabbit rabbit = t.rabbit;
        World world = t.world;

        if ( startingIntoToWall( world, rabbit, bs ) )
        {
            return stateIntoWall( t, rabbit, world, ss );
        }

        boolean slopeUp = isSlopeUp( rabbit, hereBlock );
        int nx = nextX( rabbit );
        int ny = nextY( rabbit, slopeUp );

        Block nextBlock = world.getBlockAt( nx, ny );

        Block belowNextBlock = world.getBlockAt( nx, rabbit.y );
        Block twoAboveHereBlock = world.getBlockAt( rabbit.x, rabbit.y - 2 );
        Block aboveNextBlock = world.getBlockAt( nx, ny - 1 );

        if (
            (
                   // Something in the way
                   nextBlock != null
                && nextBlock.riseDir() != rabbit.dir
            ) || (
                   Blocking.blockerAt( t.world, nx, ny )
            ) || (
                   // Clip land
                   belowNextBlock != null
                && belowNextBlock.riseDir() != rabbit.dir
            ) || (
                    // Bang head next
                    aboveNextBlock != null
                 && BehaviourTools.isSolid( aboveNextBlock )
            ) || (
                    // Bang head here, mid-build
                    bs < 3
                 && BehaviourTools.s_isFlat( twoAboveHereBlock )
            )
        )
        {
            return null; // Stop bridging
        }

        boolean slopeDown = (
               ( hereBlock != null )
            && ( hereBlock.riseDir() == Direction.opposite( rabbit.dir ) )
        );

        switch( ss )
        {
            case 3:
            {
                if ( slopeUp )
                {
                    return t.rl(
                        RABBIT_BRIDGING_UP_RIGHT_1,
                        RABBIT_BRIDGING_UP_LEFT_1
                    );
                }
                else if ( slopeDown )
                {
                    return t.rl(
                        RABBIT_BRIDGING_DOWN_UP_RIGHT_1,
                        RABBIT_BRIDGING_DOWN_UP_LEFT_1
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_RIGHT_1,
                        RABBIT_BRIDGING_LEFT_1
                    );
                }
            }
            case 2:
            {
                switch( bt )
                {
                    case ALONG:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_RIGHT_2,
                            RABBIT_BRIDGING_LEFT_2
                        );
                    }
                    case UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_UP_RIGHT_2,
                            RABBIT_BRIDGING_UP_LEFT_2
                        );
                    }
                    case DOWN_UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_DOWN_UP_RIGHT_2,
                            RABBIT_BRIDGING_DOWN_UP_LEFT_2
                        );
                    }
                    default:
                    {
                        throw new AssertionError(
                            "Unexpected bridge type: " + bt );
                    }
                }
            }
            case 1:
            {
                switch( bt )
                {
                    case ALONG:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_RIGHT_3,
                            RABBIT_BRIDGING_LEFT_3
                        );
                    }
                    case UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_UP_RIGHT_3,
                            RABBIT_BRIDGING_UP_LEFT_3
                        );
                    }
                    case DOWN_UP:
                    {
                        return t.rl(
                            RABBIT_BRIDGING_DOWN_UP_RIGHT_3,
                            RABBIT_BRIDGING_DOWN_UP_LEFT_3
                        );
                    }
                    default:
                    {
                        throw new AssertionError(
                            "Unexpected bridge type: " + bt );
                    }
                }
            }
            default:
            {
                return null;
            }
        }
    }

    private static State stateIntoWall(
        BehaviourTools t, Rabbit rabbit, World world, int ss )
    {
        // We are facing a wall.  This makes us a bit keener to
        // bridge.
        Block thisBlock = world.getBlockAt( rabbit.x, rabbit.y );

        boolean slopeUp = isSlopeUp( rabbit, thisBlock );
        int bx = behindX( rabbit );
        int ny = nextY( rabbit, slopeUp );

        // Don't bridge if there is no block behind us (we're not in a hole)
        if ( isSlope( thisBlock ) && world.getBlockAt( bx, ny ) == null )
        {
            return null;
        }

        switch( ss )
        {
            case 3:
            {
                if ( isSlope( thisBlock ) )
                {
                    // Special behaviour where we bridge higher up because we
                    // are already on top of a slope.

                    Block twoAbove = world.getBlockAt( rabbit.x, rabbit.y - 2 );

                    if ( twoAbove == null || twoAbove.isBridge() ) {
                        return t.rl(
                            RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_1,
                            RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1
                        );
                    }
                    else
                    {
                        // We would hit our head, so don't bridge.
                        return null;
                    }
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_1,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_1
                    );
                }
            }
            case 2:
            {
                if ( isSlope( thisBlock ) )
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_2,
                        RABBIT_BRIDGING_IN_CORNER_UP_LEFT_2
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_2,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_2
                    );
                }
            }
            case 1:
            {
                if ( isSlope( thisBlock ) )
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_3,
                        RABBIT_BRIDGING_IN_CORNER_UP_LEFT_3
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_BRIDGING_IN_CORNER_RIGHT_3,
                        RABBIT_BRIDGING_IN_CORNER_LEFT_3
                    );
                }
            }
            default:
            {
                return null;
            }
        }
    }

    private static boolean startingIntoToWall(
        World world,
        Rabbit rabbit,
        int bs
    )
    {
        Block hereBlock = world.getBlockAt( rabbit.x, rabbit.y );

        boolean slopeUp = isSlopeUp( rabbit, hereBlock );
        int nx = nextX( rabbit );
        int ny = nextY( rabbit, slopeUp );

        Block nextBlock = world.getBlockAt( nx, ny );

        return (
           bs == 3
        )
        &&
        (
               nextBlock != null
            &&
            (
                   nextBlock.riseDir() != rabbit.dir
                || nextBlock.shape == FLAT
            )
         );
    }

    private static boolean isSlopeUp( Rabbit rabbit, Block hereBlock )
    {
        return ( hereBlock != null )
          && ( hereBlock.riseDir() == rabbit.dir );
    }

    private static int nextY( Rabbit rabbit, boolean slopeUp )
    {
        int ret = rabbit.y;
        ret += slopeUp ? -1 : 0;
        return ret;
    }

    private static int nextX( Rabbit rabbit )
    {
        int ret = rabbit.x;
        ret += rabbit.dir == Direction.RIGHT ? 1 : -1;
        return ret;
    }

    private static int behindX( Rabbit rabbit )
    {
        int ret = rabbit.x;
        ret += rabbit.dir == Direction.RIGHT ? -1 : 1;
        return ret;
    }

    private void nextStep()
    {
        --smallSteps;
        if ( smallSteps <= 0 )
        {
            --bigSteps;
            smallSteps = 3;
        }
    }

    private static boolean isSlope( Block thisBlock )
    {
        return ( thisBlock != null && thisBlock.shape != FLAT );
    }

    @Override
    public boolean behave( World world, Rabbit rabbit, State state )
    {
        boolean handled = moveRabbit( world, rabbit, state );

        if ( handled )
        {
            // If we're bridging, we're on a slope
            rabbit.onSlope = true;
        }

        return handled;
    }

    private boolean moveRabbit(World world, Rabbit rabbit, State state) {
        BridgingStrategy strategy = strategies.get(state);
        if (strategy != null) {
            boolean handled = strategy.execute(world, rabbit);
            if (handled) {
                bridgeType = strategy.getBridgeType();
            }
            return handled;
        }
        return false;
    }

    // Initialize bridge strategies
    private static void initBridgeStrategies() {
        // InitialAlongBridgingStrategy
        BridgingStrategy initialAlongBridgingStrategy = new InitialAlongBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_RIGHT_1, initialAlongBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_RIGHT_2, initialAlongBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_LEFT_1, initialAlongBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_LEFT_2, initialAlongBridgingStrategy);

        // InitialUpBridgingStrategy
        BridgingStrategy initialUpBridgingStrategy = new InitialUpBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_UP_RIGHT_1, initialUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_UP_RIGHT_2, initialUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_UP_LEFT_1, initialUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_UP_LEFT_2, initialUpBridgingStrategy);

        // InitialDownUpBridgingStrategy
        BridgingStrategy initialDownUpBridgingStrategy = new InitialDownUpBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_DOWN_UP_RIGHT_1, initialDownUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_DOWN_UP_RIGHT_2, initialDownUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_DOWN_UP_LEFT_1, initialDownUpBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_DOWN_UP_LEFT_2, initialDownUpBridgingStrategy);

        // InitialInCornerBridgingStrategy
        BridgingStrategy initialInCornerBridgingStrategy = new InitialInCornerBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_IN_CORNER_RIGHT_1, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_LEFT_1, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_RIGHT_2, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_LEFT_2, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_1, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_2, initialInCornerBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_LEFT_2, initialInCornerBridgingStrategy);

        // RightBridgingStrategy
        BridgingStrategy rightBridgingStrategy = new RightBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_RIGHT_3, rightBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_DOWN_UP_RIGHT_3, rightBridgingStrategy);

        // LeftBridgingStrategy
        BridgingStrategy leftBridgingStrategy = new LeftBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_LEFT_3, leftBridgingStrategy);
        strategies.put(RABBIT_BRIDGING_DOWN_UP_LEFT_3, leftBridgingStrategy);

        // UpRightBridgingStrategy
        BridgingStrategy upRightBridgingStrategy = new UpRightBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_UP_RIGHT_3, upRightBridgingStrategy);

        // UpLeftBridgingStrategy
        BridgingStrategy upLeftBridgingStrategy = new UpLeftBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_UP_LEFT_3, upLeftBridgingStrategy);

        // InCornerRightBridgingStrategy
        BridgingStrategy inCornerRightBridgingStrategy = new InCornerRightBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_IN_CORNER_RIGHT_3, inCornerRightBridgingStrategy);

        // InCornerLeftBridgingStrategy
        BridgingStrategy inCornerLeftBridgingStrategy = new InCornerLeftBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_IN_CORNER_LEFT_3, inCornerLeftBridgingStrategy);
    
        // InCornerUpRightBridgingStrategy
        BridgingStrategy inCornerUpRightBridgingStrategy = new InCornerUpRightBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_RIGHT_3, inCornerUpRightBridgingStrategy);

        // InCornerUpLeftBridgingStrategy
        BridgingStrategy inCornerUpLeftBridgingStrategy = new InCornerUpLeftBridgingStrategy();
        strategies.put(RABBIT_BRIDGING_IN_CORNER_UP_LEFT_3, inCornerUpLeftBridgingStrategy);
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfNotDefault(
            saveState,
            "Bridging.bridgeType",
            bridgeType.toString(),
            BridgeType.ALONG.toString()
        );

        BehaviourState.addToStateIfGtZero(
            saveState, "Bridging.bigSteps", bigSteps
        );

        BehaviourState.addToStateIfGtZero(
            saveState, "Bridging.smallSteps", smallSteps
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        bridgeType = BridgeType.valueOf(
            BehaviourState.restoreFromState(
                saveState, "Bridging.bridgeType", bridgeType.toString()
            )
        );

        bigSteps = BehaviourState.restoreFromState(
            saveState, "Bridging.bigSteps", bigSteps
        );

        smallSteps = BehaviourState.restoreFromState(
            saveState, "Bridging.smallSteps", smallSteps
        );

        if ( smallSteps > 0 )
        {
            ++smallSteps;
        }
    }
}

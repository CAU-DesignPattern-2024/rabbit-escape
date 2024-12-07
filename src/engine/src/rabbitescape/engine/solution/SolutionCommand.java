package rabbitescape.engine.solution;

import static rabbitescape.engine.util.Util.*;

import java.util.Arrays;

import rabbitescape.engine.err.RabbitEscapeException;
import rabbitescape.engine.util.Util;

public class SolutionCommand implements Component
{
    public final class WaitActionInMultiActionCommand
        extends RabbitEscapeException
    {
        private static final long serialVersionUID = 1L;
        final public String command;

        public WaitActionInMultiActionCommand( String command )
        {
            this.command = command;
        }
    }

    public final Component[] actions;

    /**
     * If the supplied array (vararg) of CommandActions is empty
     * a default WaitAction of 1 step will be created. The solution fragment
     * ";;" has implied ones, "1;1;".
     */
    public SolutionCommand( CommandAction... actions )
    {
        if ( 0 == actions.length )
        {
            this.actions = new CommandAction[] { new WaitAction( 1 ) };
        }
        else
        {
            this.actions = actions;
        }
        checkWaitInMultiAction();
    }

    private void checkWaitInMultiAction()
    {
        if ( actions.length > 1 )
        {
            for ( Component a: actions )
            {
                if ( a instanceof WaitAction )
                {
                    throw new WaitActionInMultiActionCommand( this.toString() );
                }
            }
        }
    }

    @Override
    public String toString()
    {
        return "SolutionCommand( "
            + Util.join( ", ", toStringList( actions ) )
            + " )";
    }

    @Override
    public boolean equals( Object other )
    {
        if ( ! ( other instanceof SolutionCommand ) )
        {
            return false;
        }
        SolutionCommand otherSolution = (SolutionCommand)other;

        return Arrays.deepEquals( actions, otherSolution.actions );
    }

    /**
     * Try to combine two commands. If this is not possible then return null.
     */
    public static SolutionCommand tryToSimplify(
        SolutionCommand existingCmd, 
        SolutionCommand newCmd 
    )
    {
        if( null == existingCmd || null == newCmd )
        {
            return null;
        }

        CommandAction action1 = null, action2 = null;

        if ( existingCmd instanceof CommandAction && newCmd instanceof CommandAction) {
            action1 = (CommandAction) existingCmd.actions[0];
            action2 = (CommandAction) newCmd.actions[0];
        }
        

        if (
               action1 instanceof WaitAction
            && action2 instanceof WaitAction
        )
        {
            WaitAction wait1 = (WaitAction)action1;
            WaitAction wait2 = (WaitAction)action2;
            return new SolutionCommand(
                new WaitAction( wait1.steps + wait2.steps ) );
        }
        return null;
    }

    @Override
    public int hashCode()
    {
        return Arrays.deepHashCode( actions );
    }

    public CommandAction lastAction()
    {
        if ( actions.length == 0 )
        {
            return null;
        }
        else
        {
            return (CommandAction) actions[ actions.length - 1 ];
        }
    }
}

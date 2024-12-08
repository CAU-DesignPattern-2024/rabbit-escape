package rabbitescape.engine.solution;

import rabbitescape.engine.ActionSerialiser;

public class WaitAction implements CommandAction
{
    public final int steps;

    public WaitAction( int steps )
    {
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return "WaitAction( " + steps + " )";
    }

    @Override
    public boolean equals( Object otherObj )
    {
        if ( ! ( otherObj instanceof WaitAction ) )
        {
            return false;
        }
        WaitAction other = (WaitAction)otherObj;

        return ( steps == other.steps );
    }

    @Override
    public int hashCode()
    {
        return steps;
    }

    @Override
    public void typeSwitch( CommandActionTypeSwitch actionTypeSwitch )
    {
        actionTypeSwitch.caseWaitAction( this );
    }

    @Override
    public String serialise() {
        ActionSerialiser s = new ActionSerialiser();
        this.typeSwitch(s);
        return s.ret;
    }
}

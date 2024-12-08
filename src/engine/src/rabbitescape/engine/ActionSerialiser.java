package rabbitescape.engine;

import rabbitescape.engine.solution.*;

public class ActionSerialiser implements CommandActionTypeSwitch
{
    public String ret = null;

    @Override
    public void caseWaitAction( WaitAction waitAction )
    {
        if ( waitAction.steps == 1 )
        {
            ret = "";
        }
        else
        {
            ret = String.valueOf( waitAction.steps );
        }
    }

    @Override
    public void caseSelectAction( SelectAction selectAction )
    {
        ret = selectAction.type.name();
    }

    @Override
    public void caseAssertStateAction( AssertStateAction targetStateAction )
    {
        ret = targetStateAction.targetState.name();
    }

    @Override
    public void casePlaceTokenAction( PlaceTokenAction placeTokenAction )
    {
        ret = "(" + placeTokenAction.x + "," + placeTokenAction.y + ")";
    }

    @Override
    public void caseUntilAction( UntilAction untilAction )
    {
        ret = "until:" + untilAction.targetState.name();
    }
}
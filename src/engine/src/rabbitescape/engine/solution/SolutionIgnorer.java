package rabbitescape.engine.solution;

public class SolutionIgnorer implements SolutionRecorderTemplate
{
    public SolutionIgnorer()
    {
    }

    @Override
    public void append( Component component )
    {
    }


    @Override
    public void appendStepEnd()
    {
    }

    @Override
    public String getRecord()
    {
        return "";
    }

}


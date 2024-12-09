package rabbitescape.engine.solution;

public interface SolutionRecorderTemplate
{
    public void appendStepEnd();
    public String getRecord();
    public void append(Component action);
}

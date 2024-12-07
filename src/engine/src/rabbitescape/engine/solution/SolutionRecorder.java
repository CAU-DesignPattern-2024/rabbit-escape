package rabbitescape.engine.solution;

import java.util.ArrayList;
import java.util.List;

public class SolutionRecorder implements SolutionRecorderTemplate
{
    private List<Component> commandInProgress;
    private final List<Component> solutionInProgress;

    public SolutionRecorder()
    {
        commandInProgress = new ArrayList<Component>();
        solutionInProgress = new ArrayList<Component>();
    }

    @Override
    public void append(Component component) {
        if (component instanceof CommandAction) {
            commandInProgress.add(component);
        } else if (component instanceof SolutionCommand) {
            appendSolutionCommand((SolutionCommand) component);
        } else if (component instanceof Solution) {
            appendSolution((Solution) component);
        }
    }

    private void appendSolutionCommand( SolutionCommand newCmd )
    {
        int prevCmdIndex = solutionInProgress.size() - 1;
        SolutionCommand prevCmd =   prevCmdIndex >= 0
                                  ? (SolutionCommand) solutionInProgress.get( prevCmdIndex )
                                  : null ;
        SolutionCommand combCmd =
            SolutionCommand.tryToSimplify( prevCmd, newCmd );
        if(combCmd == null)
        {
            solutionInProgress.add( newCmd );
        }
        else
        { // Replace previous with combined
            solutionInProgress.set( prevCmdIndex, combCmd );
        }
    }

    private void appendSolution(Solution solution) {
        for (Component command : solution.commands) {
            if (command instanceof SolutionCommand) {
                append((SolutionCommand) command);
            }
        }
    }

    // @Override
    // public void appendStepEnd()
    // {
    //     CommandAction[] aA = new CommandAction[commandInProgress.size()];
    //     aA = commandInProgress.toArray( aA );
    //     append( new SolutionCommand( aA ) );
    //     // Prepare to collect actions in the next step.
    //     commandInProgress = new ArrayList<CommandAction>();
    // }

    @Override
    public void appendStepEnd() {
        if (!commandInProgress.isEmpty()) {
            // Ensure all components are of type CommandAction
            CommandAction[] actions = commandInProgress.stream()
                .filter(a -> a instanceof CommandAction) // Filter only CommandAction
                .map(a -> (CommandAction) a)             // Cast to CommandAction
                .toArray(CommandAction[]::new);          // Collect into an array
            
            if (actions.length != commandInProgress.size()) {
                throw new IllegalArgumentException("commandInProgress contains non-CommandAction elements.");
            }
            
            append(new SolutionCommand(actions));
            commandInProgress.clear();
        }
    }




    @Override
    public String getRecord()
    {
        SolutionCommand[] cA = new SolutionCommand[solutionInProgress.size()];
        Solution s = new Solution( solutionInProgress.toArray( cA ) );
        return SolutionParser.serialise( s );
    }
}

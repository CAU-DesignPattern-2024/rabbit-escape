package rabbitescape.engine.solution;

import static rabbitescape.engine.util.Util.*;

import java.util.Arrays;

import rabbitescape.engine.util.Util;

public class Solution implements Component
{
    public Component[] commands;

    public Solution( SolutionCommand... commands )
    {
        this.commands = commands;
    }

    public void add(Component component) {
        if (component instanceof Solution) {
            // Solution을 추가할 때, 내부의 SolutionCommand들을 추출하여 추가
            Solution solution = (Solution) component;
            for (Component innerComponent : solution.commands) {
                if (innerComponent instanceof SolutionCommand) {
                    commands = Arrays.copyOf(commands, commands.length + 1);
                    commands[commands.length - 1] = innerComponent;  // SolutionCommand 추가
                }
            }
        } else {
            // Solution이 아닌 경우 일반적인 add 처리
            commands = Arrays.copyOf(commands, commands.length + 1);
            commands[commands.length - 1] = component;
        }
    }
    

    @Override
    public String toString()
    {
        return "Solution( "
            + Util.join( ", ", toStringList( commands ) )
            + " )";
    }

    @Override
    public boolean equals( Object other )
    {
        if ( ! ( other instanceof Solution ) )
        {
            return false;
        }
        Solution otherSolution = (Solution)other;

        return Arrays.deepEquals( commands, otherSolution.commands );
    }

    @Override
    public int hashCode()
    {
        return Arrays.deepHashCode( commands );
    }

    @Override
    public String serialise() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < commands.length; i++) {
            result.append(commands[i].serialise());
            
            if (i < commands.length - 1) {
                result.append(SolutionParser.COMMAND_DELIMITER); // ';' 추가
            }
        }

        return result.toString();
    }
}

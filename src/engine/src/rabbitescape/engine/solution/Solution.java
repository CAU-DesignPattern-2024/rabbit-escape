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
            commands = Arrays.copyOf(commands, commands.length+1);
            commands[commands.length-1] = component;
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
    public String serialize() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < commands.length; i++) {
            result.append(commands[i].serialize());
            
            if (i < commands.length - 1) {
                result.append(SolutionParser.COMMAND_DELIMITER); // ';' 추가
            }
        }

        return result.toString();
    }
}

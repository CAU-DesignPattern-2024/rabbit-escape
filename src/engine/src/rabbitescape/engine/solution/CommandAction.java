package rabbitescape.engine.solution;

/**
 * Something that can be an action in a solution command, e.g.
 * "1" for a WaitAction
 * "until:Lost" for an UntilAction
 * "bash" for a SelectAction
 */
public abstract class CommandAction implements Component {
    abstract void typeSwitch( CommandActionTypeSwitch actionTypeSwitch );
    @Override
    public String serialize() {
        ActionSerializer s = new ActionSerializer();
        this.typeSwitch(s);
        return s.ret;
    }
}

package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;

abstract class StateHandler {
    protected StateHandler next;

    public void setNext(StateHandler next) {
        this.next = next;
    }

    public abstract boolean handle(World world, Rabbit rabbit, State state);
}

package rabbitescape.engine.behaviours;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;

public abstract class StateHandler {
    protected final BehaviourTools t;
    private StateHandler nextHandler;

    public StateHandler(BehaviourTools t) {
        this.t = t;
    }

    public void setNext(StateHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public State handle() {
        State result = process();
        if (result != null) {
            return result;
        }
        if (nextHandler != null) {
            return nextHandler.handle();
        }
        throw new IllegalStateException("No state handler could handle the state");
    }

    protected abstract State process();
}

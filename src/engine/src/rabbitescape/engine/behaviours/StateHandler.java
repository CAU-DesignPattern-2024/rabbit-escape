package rabbitescape.engine.behaviours;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;

public abstract class StateHandler {
    protected final BehaviourTools t;
    protected final StateHandler next;

    public StateHandler(BehaviourTools t, StateHandler next) {
        this.t = t;
        this.next = next;
    }

    public State handle() {
        if (canHandle()) {
            return computeState();
        } else if (next != null) {
            return next.handle();
        } else {
            throw new IllegalStateException("No handler could process the request.");
        }
    }

    protected abstract boolean canHandle();

    protected abstract State computeState();
}

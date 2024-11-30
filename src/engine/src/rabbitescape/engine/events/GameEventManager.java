package rabbitescape.engine.events;

import java.util.*;

public class GameEventManager {
    private final Map<EventType, List<GameEventListener>> listeners;

    public GameEventManager() {
        this.listeners = new HashMap<>();
        for (EventType type : EventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public void addEventListener(EventType type, GameEventListener listener) {
        List<GameEventListener> typeListeners = listeners.get(type);
        if (!typeListeners.contains(listener)) {
            typeListeners.add(listener);
        }
    }

    public void removeEventListener(EventType type, GameEventListener listener) {
        List<GameEventListener> typeListeners = listeners.get(type);
        typeListeners.remove(listener);
    }

    public void fireEvent(GameEvent event) {
        List<GameEventListener> typeListeners = listeners.get(event.getType());
        for (GameEventListener listener : typeListeners) {
            listener.onGameEvent(event);
        }
    }
}

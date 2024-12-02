package rabbitescape.engine.events;

import java.util.Map;

public interface GameEvent {
    EventType getType();
    Map<String, Object> getEventData();
}

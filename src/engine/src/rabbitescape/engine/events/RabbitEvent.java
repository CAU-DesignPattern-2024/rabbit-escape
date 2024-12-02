package rabbitescape.engine.events;

import rabbitescape.engine.Rabbit;
import rabbitescape.engine.util.Position;

import java.util.HashMap;
import java.util.Map;

public class RabbitEvent implements GameEvent {
    private final EventType type;
    private final Rabbit rabbit;
    private final Position position;

    public RabbitEvent(EventType type, Rabbit rabbit, Position position) {
        this.type = type;
        this.rabbit = rabbit;
        this.position = position;
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public Map<String, Object> getEventData() {
        Map<String, Object> data = new HashMap<>();
        data.put("rabbit", rabbit);
        data.put("position", position);
        return data;
    }
}

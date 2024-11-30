package rabbitescape.engine.events;

import java.util.HashMap;
import java.util.Map;

public class LevelWinEvent implements GameEvent {
    private final EventType type;
    private final boolean won;

    public LevelWinEvent(boolean won) {
        this.type = won ? EventType.LEVEL_WON : EventType.LEVEL_LOST;
        this.won = won;
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public Map<String, Object> getEventData() {
        Map<String, Object> data = new HashMap<>();
        data.put("won", won);
        return data;
    }
}

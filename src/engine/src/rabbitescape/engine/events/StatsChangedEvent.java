package rabbitescape.engine.events;

import java.util.HashMap;
import java.util.Map;

public class StatsChangedEvent implements GameEvent {
    private final int numSaved;
    private final int numToSave;

    public StatsChangedEvent(int numSaved, int numToSave) {
        this.numSaved = numSaved;
        this.numToSave = numToSave;
    }

    @Override
    public EventType getType() {
        return EventType.STATS_CHANGED;
    }

    @Override
    public Map<String, Object> getEventData() {
        Map<String, Object> data = new HashMap<>();
        data.put("numSaved", numSaved);
        data.put("numToSave", numToSave);
        return data;
    }
}

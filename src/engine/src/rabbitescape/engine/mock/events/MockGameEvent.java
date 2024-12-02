package rabbitescape.engine.mock.events;

import rabbitescape.engine.events.EventType;
import rabbitescape.engine.events.GameEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple GameEvent implementation for testing purposes.
 */
public class MockGameEvent implements GameEvent {
    private EventType type;
    private String testData;

    public MockGameEvent() {
        this.type = EventType.RABBIT_MOVED;
        this.testData = "default";
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public Map<String, Object> getEventData() {
        Map<String, Object> data = new HashMap<>();
        data.put("testData", testData);
        if (type == EventType.LEVEL_WON || type == EventType.LEVEL_LOST) {
            data.put("won", type == EventType.LEVEL_WON);
        }
        return data;
    }

    public String getTestData() {
        return testData;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }
}

package rabbitescape.engine.events;

import java.util.HashMap;
import java.util.Map;

public class TestGameEvent implements GameEvent {
    private final EventType type;
    private final String testData;

    public TestGameEvent(EventType type, String testData) {
        this.type = type;
        this.testData = testData;
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public Map<String, Object> getEventData() {
        Map<String, Object> data = new HashMap<>();
        data.put("testData", testData);
        return data;
    }

    public String getTestData() {
        return testData;
    }
}

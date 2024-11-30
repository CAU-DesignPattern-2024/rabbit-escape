package rabbitescape.engine.events;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class GameEventManagerTest {
    
    private GameEventManager manager;
    private TestEventListener listener;
    
    private static class TestEventListener implements GameEventListener {
        private final List<GameEvent> receivedEvents = new ArrayList<>();
        
        @Override
        public void onGameEvent(GameEvent event) {
            receivedEvents.add(event);
        }
        
        public List<GameEvent> getReceivedEvents() {
            return receivedEvents;
        }
        
        public void clearEvents() {
            receivedEvents.clear();
        }
    }
    
    @Before
    public void setUp() {
        manager = new GameEventManager();
        listener = new TestEventListener();
    }
    
    @Test
    public void addEventListener_shouldAddListener() {
        // Act
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        TestGameEvent event = new TestGameEvent();
        event.setTestData("test");
        manager.fireEvent(event);
        
        // Assert
        assertEquals(1, listener.getReceivedEvents().size());
        GameEvent receivedEvent = listener.getReceivedEvents().get(0);
        assertEquals(EventType.RABBIT_MOVED, receivedEvent.getType());
        assertEquals("test", receivedEvent.getEventData().get("testData"));
    }
    
    @Test
    public void removeEventListener_shouldRemoveListener() {
        // Arrange
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        
        // Act
        manager.removeEventListener(EventType.RABBIT_MOVED, listener);
        TestGameEvent event = new TestGameEvent();
        event.setTestData("test");
        manager.fireEvent(event);
        
        // Assert
        assertEquals(0, listener.getReceivedEvents().size());
    }
    
    @Test
    public void fireEvent_shouldNotifyOnlyMatchingListeners() {
        // Arrange
        TestEventListener otherListener = new TestEventListener();
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        manager.addEventListener(EventType.LEVEL_WON, otherListener);
        
        // Act
        TestGameEvent event = new TestGameEvent();
        event.setTestData("test");
        manager.fireEvent(event);
        
        // Assert
        assertEquals(1, listener.getReceivedEvents().size());
        assertEquals(0, otherListener.getReceivedEvents().size());
    }
    
    @Test
    public void multipleListeners_shouldAllReceiveEvents() {
        // Arrange
        TestEventListener listener2 = new TestEventListener();
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        manager.addEventListener(EventType.RABBIT_MOVED, listener2);
        
        // Act
        TestGameEvent event = new TestGameEvent();
        event.setTestData("test");
        manager.fireEvent(event);
        
        // Assert
        assertEquals(1, listener.getReceivedEvents().size());
        assertEquals(1, listener2.getReceivedEvents().size());
    }
}

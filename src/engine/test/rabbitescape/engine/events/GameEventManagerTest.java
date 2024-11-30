package rabbitescape.engine.events;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class GameEventManagerTest {
    
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
    
    @Test
    public void addEventListener_shouldAddListener() {
        // Arrange
        GameEventManager manager = new GameEventManager();
        TestEventListener listener = new TestEventListener();
        
        // Act
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        manager.fireEvent(new TestGameEvent(EventType.RABBIT_MOVED, "test"));
        
        // Assert
        assertThat(listener.getReceivedEvents().size(), is(1));
        assertThat(
            ((TestGameEvent)listener.getReceivedEvents().get(0)).getTestData(),
            is("test")
        );
    }
    
    @Test
    public void removeEventListener_shouldRemoveListener() {
        // Arrange
        GameEventManager manager = new GameEventManager();
        TestEventListener listener = new TestEventListener();
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        
        // Act
        manager.removeEventListener(EventType.RABBIT_MOVED, listener);
        manager.fireEvent(new TestGameEvent(EventType.RABBIT_MOVED, "test"));
        
        // Assert
        assertThat(listener.getReceivedEvents().size(), is(0));
    }
    
    @Test
    public void fireEvent_shouldOnlyNotifyListenersOfSpecificType() {
        // Arrange
        GameEventManager manager = new GameEventManager();
        TestEventListener rabbitListener = new TestEventListener();
        TestEventListener tokenListener = new TestEventListener();
        
        manager.addEventListener(EventType.RABBIT_MOVED, rabbitListener);
        manager.addEventListener(EventType.TOKEN_ADDED, tokenListener);
        
        // Act
        manager.fireEvent(new TestGameEvent(EventType.RABBIT_MOVED, "rabbit"));
        
        // Assert
        assertThat(rabbitListener.getReceivedEvents().size(), is(1));
        assertThat(tokenListener.getReceivedEvents().size(), is(0));
    }
    
    @Test
    public void fireEvent_shouldNotifyMultipleListeners() {
        // Arrange
        GameEventManager manager = new GameEventManager();
        TestEventListener listener1 = new TestEventListener();
        TestEventListener listener2 = new TestEventListener();
        
        manager.addEventListener(EventType.RABBIT_MOVED, listener1);
        manager.addEventListener(EventType.RABBIT_MOVED, listener2);
        
        // Act
        manager.fireEvent(new TestGameEvent(EventType.RABBIT_MOVED, "test"));
        
        // Assert
        assertThat(listener1.getReceivedEvents().size(), is(1));
        assertThat(listener2.getReceivedEvents().size(), is(1));
    }
    
    @Test
    public void addEventListener_shouldNotAddSameListenerTwice() {
        // Arrange
        GameEventManager manager = new GameEventManager();
        TestEventListener listener = new TestEventListener();
        
        // Act
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        manager.addEventListener(EventType.RABBIT_MOVED, listener);
        manager.fireEvent(new TestGameEvent(EventType.RABBIT_MOVED, "test"));
        
        // Assert
        assertThat(listener.getReceivedEvents().size(), is(1));
    }
}

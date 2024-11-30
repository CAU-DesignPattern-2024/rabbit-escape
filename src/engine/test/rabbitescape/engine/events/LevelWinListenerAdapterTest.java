package rabbitescape.engine.events;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import rabbitescape.engine.LevelWinListener;

public class LevelWinListenerAdapterTest {
    
    private GameEventManager eventManager;
    private TestLevelWinListener originalListener;
    private TestGameEventListener eventListener;
    private LevelWinListenerAdapter adapter;
    
    private static class TestLevelWinListener implements LevelWinListener {
        private boolean wonCalled = false;
        private boolean lostCalled = false;
        
        @Override
        public void won() {
            wonCalled = true;
        }
        
        @Override
        public void lost() {
            lostCalled = true;
        }
        
        public void reset() {
            wonCalled = false;
            lostCalled = false;
        }
    }
    
    private static class TestGameEventListener implements GameEventListener {
        private GameEvent lastEvent = null;
        
        @Override
        public void onGameEvent(GameEvent event) {
            lastEvent = event;
        }
        
        public GameEvent getLastEvent() {
            return lastEvent;
        }
        
        public void reset() {
            lastEvent = null;
        }
    }
    
    @Before
    public void setUp() {
        eventManager = new GameEventManager();
        originalListener = new TestLevelWinListener();
        eventListener = new TestGameEventListener();
        adapter = new LevelWinListenerAdapter(originalListener, eventManager);
        
        eventManager.addEventListener(EventType.LEVEL_WON, eventListener);
        eventManager.addEventListener(EventType.LEVEL_LOST, eventListener);
    }
    
    @Test
    public void won_shouldCallOriginalListenerAndFireEvent() {
        // Act
        adapter.won();
        
        // Assert
        assertThat(originalListener.wonCalled, is(true));
        assertThat(originalListener.lostCalled, is(false));
        
        GameEvent event = eventListener.getLastEvent();
        assertThat(event, is(notNullValue()));
        assertThat(event.getType(), is(EventType.LEVEL_WON));
        assertThat((Boolean)event.getEventData().get("won"), is(true));
    }
    
    @Test
    public void lost_shouldCallOriginalListenerAndFireEvent() {
        // Act
        adapter.lost();
        
        // Assert
        assertThat(originalListener.wonCalled, is(false));
        assertThat(originalListener.lostCalled, is(true));
        
        GameEvent event = eventListener.getLastEvent();
        assertThat(event, is(notNullValue()));
        assertThat(event.getType(), is(EventType.LEVEL_LOST));
        assertThat((Boolean)event.getEventData().get("won"), is(false));
    }
    
    @Test
    public void adapter_shouldWorkWithNullOriginalListener() {
        // Arrange
        adapter = new LevelWinListenerAdapter(null, eventManager);
        
        // Act & Assert - should not throw exception
        adapter.won();
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_WON));
        
        adapter.lost();
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_LOST));
    }
}

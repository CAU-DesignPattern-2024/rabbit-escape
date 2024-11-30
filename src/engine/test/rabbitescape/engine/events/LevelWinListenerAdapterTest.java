package rabbitescape.engine.events;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
import rabbitescape.engine.LevelWinListener;

public class LevelWinListenerAdapterTest {
    
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
    
    @Test
    public void won_shouldCallOriginalListenerAndFireEvent() {
        // Arrange
        GameEventManager eventManager = new GameEventManager();
        TestLevelWinListener originalListener = new TestLevelWinListener();
        TestGameEventListener eventListener = new TestGameEventListener();
        LevelWinListenerAdapter adapter = new LevelWinListenerAdapter(originalListener, eventManager);
        
        eventManager.addEventListener(EventType.LEVEL_WON, eventListener);
        
        // Act
        adapter.won();
        
        // Assert
        assertThat(originalListener.wonCalled, is(true));
        assertThat(originalListener.lostCalled, is(false));
        
        assertThat(eventListener.getLastEvent(), is(notNullValue()));
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_WON));
        assertThat((Boolean)eventListener.getLastEvent().getEventData().get("won"), is(true));
    }
    
    @Test
    public void lost_shouldCallOriginalListenerAndFireEvent() {
        // Arrange
        GameEventManager eventManager = new GameEventManager();
        TestLevelWinListener originalListener = new TestLevelWinListener();
        TestGameEventListener eventListener = new TestGameEventListener();
        LevelWinListenerAdapter adapter = new LevelWinListenerAdapter(originalListener, eventManager);
        
        eventManager.addEventListener(EventType.LEVEL_LOST, eventListener);
        
        // Act
        adapter.lost();
        
        // Assert
        assertThat(originalListener.wonCalled, is(false));
        assertThat(originalListener.lostCalled, is(true));
        
        assertThat(eventListener.getLastEvent(), is(notNullValue()));
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_LOST));
        assertThat((Boolean)eventListener.getLastEvent().getEventData().get("won"), is(false));
    }
    
    @Test
    public void adapter_shouldWorkWithNullOriginalListener() {
        // Arrange
        GameEventManager eventManager = new GameEventManager();
        TestGameEventListener eventListener = new TestGameEventListener();
        LevelWinListenerAdapter adapter = new LevelWinListenerAdapter(null, eventManager);
        
        eventManager.addEventListener(EventType.LEVEL_WON, eventListener);
        eventManager.addEventListener(EventType.LEVEL_LOST, eventListener);
        
        // Act & Assert - should not throw exception
        adapter.won();
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_WON));
        
        adapter.lost();
        assertThat(eventListener.getLastEvent().getType(), is(EventType.LEVEL_LOST));
    }
    
    @Test
    public void events_shouldContainCorrectData() {
        // Arrange
        GameEventManager eventManager = new GameEventManager();
        TestGameEventListener eventListener = new TestGameEventListener();
        LevelWinListenerAdapter adapter = new LevelWinListenerAdapter(null, eventManager);
        
        eventManager.addEventListener(EventType.LEVEL_WON, eventListener);
        eventManager.addEventListener(EventType.LEVEL_LOST, eventListener);
        
        // Act & Assert for win
        adapter.won();
        assertThat(eventListener.getLastEvent().getEventData().containsKey("won"), is(true));
        assertThat((Boolean)eventListener.getLastEvent().getEventData().get("won"), is(true));
        
        // Act & Assert for loss
        adapter.lost();
        assertThat(eventListener.getLastEvent().getEventData().containsKey("won"), is(true));
        assertThat((Boolean)eventListener.getLastEvent().getEventData().get("won"), is(false));
    }
}

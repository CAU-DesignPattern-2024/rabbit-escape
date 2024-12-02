package rabbitescape.engine.events;

import rabbitescape.engine.LevelWinListener;

public class LevelWinListenerAdapter implements LevelWinListener {
    private final LevelWinListener originalListener;
    private final GameEventManager eventManager;

    public LevelWinListenerAdapter(LevelWinListener originalListener, GameEventManager eventManager) {
        this.originalListener = originalListener;
        this.eventManager = eventManager;
    }

    @Override
    public void won() {
        // 기존 리스너 호출
        if (originalListener != null) {
            originalListener.won();
        }
        
        // 새로운 이벤트 발생
        eventManager.fireEvent(new LevelWinEvent(true));
    }

    @Override
    public void lost() {
        // 기존 리스너 호출
        if (originalListener != null) {
            originalListener.lost();
        }
        
        // 새로운 이벤트 발생
        eventManager.fireEvent(new LevelWinEvent(false));
    }
}

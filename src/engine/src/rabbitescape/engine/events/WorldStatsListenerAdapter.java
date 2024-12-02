package rabbitescape.engine.events;

import rabbitescape.engine.WorldStatsListener;

public class WorldStatsListenerAdapter implements WorldStatsListener {
    private final WorldStatsListener originalListener;
    private final GameEventManager eventManager;

    public WorldStatsListenerAdapter(WorldStatsListener originalListener, GameEventManager eventManager) {
        this.originalListener = originalListener;
        this.eventManager = eventManager;
    }

    @Override
    public void worldStats(int numSaved, int numToSave) {
        // 기존 리스너 호출
        if (originalListener != null) {
            originalListener.worldStats(numSaved, numToSave);
        }
        
        // 새로운 이벤트 발생
        eventManager.fireEvent(new StatsChangedEvent(numSaved, numToSave));
    }
}

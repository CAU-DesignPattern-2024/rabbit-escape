package rabbitescape.engine.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryConfigStorage implements IConfigStorage {
    private static MemoryConfigStorage instance;
    private final Map<String, String> values;
    public List<String> saves;
    
    private MemoryConfigStorage() {
        values = new HashMap<String, String>();
        saves = new ArrayList<String>();
    }
    
    public static synchronized MemoryConfigStorage getInstance() {
        if (instance == null) {
            instance = new MemoryConfigStorage();
        }
        return instance;
    }
    
    @Override
    public void set(String key, String value) {
        values.put(key, value);
    }
    
    @Override
    public String get(String key) {
        return values.get(key);
    }
    
    @Override
    public void save(Config config) {
        saves.add(values.get(Config.CFG_VERSION));
    }
    
    // 테스트를 위한 리셋 메서드
    public static void reset() {
        instance = null;
    }
}

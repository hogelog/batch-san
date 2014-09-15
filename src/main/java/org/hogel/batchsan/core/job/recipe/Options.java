package org.hogel.batchsan.core.job.recipe;

import java.util.HashMap;
import java.util.Map;

public class Options extends HashMap<String, Object> {
    public Options(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public Options(int initialCapacity) {
        super(initialCapacity);
    }

    public Options() {
    }

    public Options(Map<? extends String, ?> m) {
        super(m);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defValue) {
        Object obj = get(key);
        return obj == null ? defValue : (int) obj;
    }
}

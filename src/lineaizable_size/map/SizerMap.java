package lineaizable_size.map;

import java.util.Map;

/**
 * Created by Liat n on 17/02/2018.
 */
public abstract class SizerMap<K, V, A extends Map<K,V>> {
    A adt;
    SizerMap(A adt){
        this.adt = adt;
    }
    public abstract boolean put(K key, V value);
    public abstract boolean remove(K key);
    public abstract long getSize();
    public V get(K element){
        return adt.get(element);
    }
}

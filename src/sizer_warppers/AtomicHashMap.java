package sizer_warppers;

import lineaizable_size.map.AtomicSizerMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Liat n on 17/02/2018.
 */
public class AtomicHashMap<K,V> extends AtomicSizerMap<K, V, ConcurrentHashMap<K,V>> {
    public AtomicHashMap(){
        super(new ConcurrentHashMap<>());
    }
}

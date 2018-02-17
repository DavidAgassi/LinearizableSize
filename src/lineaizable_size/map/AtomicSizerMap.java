package lineaizable_size.map;

import lineaizable_size.LinearizableCounter;
import java.util.Map;

/**
 * Created by Liat n on 17/02/2018.
 */
public class AtomicSizerMap<K, V, A extends Map<K,V>> extends SizerMap<K, V, A>{
    private LinearizableCounter minSize = new LinearizableCounter();
    private LinearizableCounter maxSize = new LinearizableCounter();
    private A adt;
    public AtomicSizerMap(A adt){
        super(adt);
    }
    public long getSize(){
        long min;
        long max = maxSize.sum();
        while (true){
            min = minSize.sum();
            if(min>=max){
                return min;
            }
            max = maxSize.sum();
            if(min>=max){
                return max;
            }
        }
    }
    public boolean put(K key, V value){
        maxSize.add(1);
        if(adt.put(key, value)==null){
            minSize.add(1);
            return true;
        }else {
            maxSize.add(-1);
            return false;
        }
    }
    public boolean remove(K key){
        minSize.add(-1);
        if(adt.remove(key)!=null){
            maxSize.add(-1);
            return true;
        }else {
            minSize.add(1);
            return false;
        }
    }
}
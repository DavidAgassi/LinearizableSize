package lineaizable_size.collection;

import lineaizable_size.LinearizableCounter;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Liat n on 17/02/2018.
 */

public class AtomicSizerCollection<E, A extends Collection<E>> extends SizerCollection<E, A>{
    private LinearizableCounter minSize = new LinearizableCounter();
    private LinearizableCounter maxSize = new LinearizableCounter();
    public AtomicSizerCollection(A adt){
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
    public boolean add(E element){
        maxSize.add(1);
        if(adt.add(element)){
            minSize.add(1);
            return true;
        }else {
            maxSize.add(-1);
            return false;
        }
    }
    public boolean remove(E element){
        minSize.add(-1);
        if(adt.remove(element)){
            maxSize.add(-1);
            return true;
        }else {
            minSize.add(1);
            return false;
        }
    }
}

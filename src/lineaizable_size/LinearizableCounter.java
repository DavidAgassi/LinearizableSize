package lineaizable_size;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Liat n on 17/02/2018.
 */
public class LinearizableCounter{
    private LongAdder counter = new LongAdder();
    private AtomicBoolean dirty = new AtomicBoolean();
    public void add(long x){
        dirty.set(true);
        counter.add(x);
    }
    public long sum(){
        long s;
        while (true){
            dirty.set(false);
            s = counter.sum();
            if(dirty.get()){
                return s;
            }
        }
    }
}

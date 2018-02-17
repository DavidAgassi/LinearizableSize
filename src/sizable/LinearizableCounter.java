package sizable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Liat n on 17/02/2018.
 */
public class LinearizableCounter extends LongAdder{
    private AtomicBoolean dirty = new AtomicBoolean();
    public void add(long x){
        dirty.set(true);
        super.add(x);
    }
    public long sum(){
        long s;
        while (true){
            dirty.set(false);
            s = super.sum();
            if(dirty.get()){
                return s;
            }
        }
    }
}

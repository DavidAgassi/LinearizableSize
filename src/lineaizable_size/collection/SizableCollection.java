package lineaizable_size.collection;

import lineaizable_size.Sizable;
import java.util.Collection;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Liat n on 17/02/2018.
 */
public class SizableCollection<E, A extends Collection<E>, C extends LongAdder> extends Sizable {
    private A collection;
    public SizableCollection(A adt, Class<C> counterClass){
        super(counterClass);
        this.collection = adt;
    }
    public boolean add(E element){
        maxSize.add(1);
        if(collection.add(element)){
            minSize.add(1);
            return true;
        }else {
            maxSize.add(-1);
            return false;
        }
    }
    public boolean remove(E element){
        minSize.add(-1);
        if(collection.remove(element)){
            maxSize.add(-1);
            return true;
        }else {
            minSize.add(1);
            return false;
        }
    }
    public boolean contains(E element){
        return collection.contains(element);
    }
}

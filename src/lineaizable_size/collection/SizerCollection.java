package lineaizable_size.collection;

import java.util.Collection;

/**
 * Created by Liat n on 17/02/2018.
 */
public abstract class SizerCollection<E, A extends Collection<E>> {
    A adt;
    SizerCollection(A adt){
        this.adt = adt;
    }
    public abstract boolean add(E element);
    public abstract boolean remove(E element);
    public abstract long getSize();
    public boolean contains(E element){
        return adt.contains(element);
    }
}

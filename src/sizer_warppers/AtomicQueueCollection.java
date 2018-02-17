package sizer_warppers;

import lineaizable_size.collection.AtomicSizerCollection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Liat n on 17/02/2018.
 */
public class AtomicQueueCollection<E> extends AtomicSizerCollection<E, ConcurrentLinkedQueue<E>> {
    public AtomicQueueCollection(){
        super(new ConcurrentLinkedQueue<>());
    }
}

package sizer_warppers;

import lineaizable_size.collection.SimpleSizerCollection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Liat n on 17/02/2018.
 */

public class SimpleQueueCollection<E> extends SimpleSizerCollection<E, ConcurrentLinkedQueue<E>> {
public SimpleQueueCollection(){
        super(new ConcurrentLinkedQueue<E>());
        }
}

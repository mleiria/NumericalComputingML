/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Stream;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;

/**
 *
 * @author manuel
 */
public class BagTest extends TestCase{
    private static final Logger LOG = getLogger("mlearningLog");
    
    private final Double[] data = new Double[]{1., 2., 3., 4., 5., 6., 7., 8., 9.};
    private final Bag<Double> b = new Bag<>();
    
    @Override
    protected void setUp(){
        final Stream<Double> stream = Arrays.stream(data);
        stream.forEach(b::add);
    }
    
    public void testSize(){
        assertEquals(9, b.size());
    }
    
    public void testIterate(){
        int total = 0;
        final Iterator<Double> it = b.iterator();
        LOG.info("BAG:");
        while(it.hasNext()){
            LOG.info(it.next());
            total++;
        }
        assertEquals(9, total);
    }
    
    
}

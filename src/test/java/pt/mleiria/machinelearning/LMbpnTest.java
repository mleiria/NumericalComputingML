/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.neuralnet.LMbpn;
import static pt.mleiria.machinelearning.neuralnet.LMbpn.test;
import static pt.mleiria.machinelearning.neuralnet.LMbpn.train;
import static pt.mleiria.machinelearning.neuralnet.LMbpn.trainedEpochs;

/**
 *
 * @author manuel
 */
public class LMbpnTest extends TestCase {
    private static final Logger mlearningLog = getLogger("mlearningLog");
    int i ;
    int[] inp= {1, 0, 0,};
    int[] out = {0, 0};
    
    public void testTrain(){
        for(i = 0; i < 500; i++){
            train(1);
        }
        mlearningLog.info("Trained Epochs: " + trainedEpochs);
        test(inp, out);
        assertEquals(1, out[0]);
        assertEquals(0, out[1]);
    }
    
}

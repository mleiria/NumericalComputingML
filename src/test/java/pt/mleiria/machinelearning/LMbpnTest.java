/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.neuralnet.LMbpn;

/**
 *
 * @author manuel
 */
public class LMbpnTest extends TestCase {
    private static final Logger mlearningLog = Logger.getLogger("mlearningLog");
    int i ;
    int[] inp= {1, 0, 0,};
    int[] out = {0, 0};
    
    public void testTrain(){
        for(i = 0; i < 500; i++){
            LMbpn.train(1);
        }
        mlearningLog.info("Trained Epochs: " + LMbpn.trainedEpochs);
        LMbpn.test(inp, out);
        Assert.assertEquals(1, out[0]);
        Assert.assertEquals(0, out[1]);
    }
    
}

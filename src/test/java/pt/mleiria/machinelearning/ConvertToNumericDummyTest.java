package pt.mleiria.machinelearning;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.preprocess.ConvertToNumericDummy;

public class ConvertToNumericDummyTest extends TestCase{

	private final String[] flowersClass = new String[]{"Iris-setosa", "Iris-versicolor", "Iris-virginica"};
	
	
	
	public void testFlowerMap(){
		ConvertToNumericDummy conv = new ConvertToNumericDummy();
		
		for(String str : flowersClass){
			conv.put(str);
		}
		assertEquals(3, conv.getSize());
		assertEquals("Iris-setosa", conv.getRealValue(0));
		assertEquals("Iris-virginica", conv.getRealValue(2));
	}
}

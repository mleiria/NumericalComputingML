/**
 * 
 */
package pt.mleiria.machinelearning.preprocess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manuel
 *
 */
public class ConvertToNumericDummy {

		
	private List<String> lst = new ArrayList<String>();
	/**
	 * 
	 * @param realValue
	 * @return
	 */
	public double put(final String realValue){
		if(lst.contains(realValue)){
			return lst.indexOf(realValue);
		}else{
			lst.add(realValue);
			return lst.size() - 1;
		}
	}
	/**
	 * 
	 * @param dummyValue
	 * @return
	 */
	public String getRealValue(final double dummyValue){
		return lst.get((int)dummyValue);
	}
	/**
	 * 
	 * @return
	 */
	public int getSize(){
		return lst.size();
	}

}

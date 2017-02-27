/**
 * 
 */
package pt.mleiria.numericalAnalysis.utils;

/**
 * @author manuel
 *
 */
public class VUtils<Num> {
	
	

	
	public String showContents(Num[] array){
		StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                sb.append(array[i]);
            } else {
                sb.append(array[i]).append("; ");
            }
        }
        sb.append("}");
        return sb.toString();
	}
	
	public static void main(String[] args){
		Double[] dblArr = new Double[]{1.0, 2.0, 3.0, 4.0};
		VUtils<Double> vu = new VUtils<Double>();
		System.out.println(vu.showContents(dblArr));
		
		VUtils<Integer> vu1 = new VUtils<Integer>();
		Integer[] intArr = new Integer[]{1, 2, 3, 4};
		System.out.println(vu1.showContents(intArr));
	}
}

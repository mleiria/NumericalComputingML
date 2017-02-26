/**
 * 
 */
package pt.mleiria.machinelearning.matrixAlgebra;

/**
 * @author manuel
 *
 */
public class GaussJordanDecomposition {

	private double[][] rows;

	private Matrix currentMatrix;
	private final Matrix inverse;
	private Matrix lower;
	private Matrix upper;

	/**
	 * 
	 * @param m
	 */
	public GaussJordanDecomposition(final double[][] m) {
		final int n = m.length;
		if (m[0].length != n)
			throw new IllegalArgumentException(
					"Illegal system: a" + n + " by " + m[0].length + " matrix is not a square matrix");
		initialize(m);
		inverse = new Matrix(n).identity();
	}
	/**
	 * 
	 * @param m
	 */
	private void initialize(final double[][] m){
		final int size = m.length; 
		rows = new double[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				rows[i][j] = m[i][j];
			}
		}
		m = new Matrix(rows);
	}
	/**
	 * 
	 * @param m
	 */
	public GaussJordanDecomposition(final Matrix m) {
		this(m.components);
	}
	/**
	 * 
	 */
	public void decompose() {
		final int n = rows.length;
		for (int i = 0; i < n; i++)
			pivotingStep(i);
		lower = new Matrix(inverse.toComponents());
		upper = new Matrix(rows);
		for (int i = n - 1; i >= 0; i--)
			jordanElimination(i);
		for (int i = 0; i < n ; i++){
			final double inversePivot = 1.0 / rows[i][i];
			for(int j = 0; j < n; j++){
				inverse.components[i][j] *= inversePivot;
			}
		}
		
	}
	/**
	 * 
	 * @param p
	 */
	private void pivotingStep(final int p) {
		//swapRows(p, largestPivot(p));
		gaussElimination(p);
		return;
	}
	/**
	 * 
	 * @param p
	 * @throws ArithmeticException
	 */
	private void gaussElimination(final int p) throws ArithmeticException {
		final double inversePivot = 1 / rows[p][p];
		double r;
		int n = rows.length;
		int m = rows[0].length;
		for (int i = p + 1; i < n; i++) {
			r = inversePivot * rows[i][p];
			inverse.components[i][p] = r;
			for (int j = p; j < m; j++)
				rows[i][j] -= rows[p][j] * r;
		}
	}
	/**
	 * 
	 * @param p
	 * @throws ArithmeticException
	 */
	private void jordanElimination(final int p) throws ArithmeticException {
		final double inversePivot = 1 / rows[p][p];
		double r;
		for (int i = p - 1; i >= 0; i--) {
			r = inversePivot * rows[i][p];
			inverse.components[i][p] = r;
			for (int j = p; j >= 0; j--)
				rows[i][j] -= rows[p][j] * r;
		}
	}
	/**
	 * 
	 * @param p
	 * @param q
	 */
	private void swapRows(final int p, final int q) {
		if (p != q) {
			double temp;
			int m = rows[p].length;
			for (int j = 0; j < m; j++) {
				temp = rows[p][j];
				rows[p][j] = rows[q][j];
				rows[q][j] = temp;
			}
		}
		return;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	private int largestPivot(final int p) {
		double pivot = Math.abs(rows[p][p]);
		int answer = p;
		double x;
		for (int i = p + 1; i < rows.length; i++) {
			x = Math.abs(rows[i][p]);
			if (x > pivot) {
				answer = i;
				pivot = x;
			}
		}
		return answer;
	}
	/**
	 * 
	 * @return
	 */
	public Matrix getLowerMatrix(){
		if(null != lower){
			return lower;
		}else{
			decompose();
			return lower;
		}
	}
	/**
	 * 
	 * @return
	 */
	public Matrix getUpperMatrix(){
		if(null != upper){
			return upper;
		}else{
			decompose();
			return upper;
		}
	}
	
	
	
	
	public static void main(String[] args){
		double[][] rows = new double[3][3];
		rows[0][0] = 2.0;
		rows[0][1] = 1.0;
		rows[0][2] = 4.0;
		rows[1][0] = 6.0;
		rows[1][1] = 1.0;
		rows[1][2] = 0.0;
		rows[2][0] = -1.0;
		rows[2][1] = 2.0;
		rows[2][2] = -10.0;
		
		Matrix A = new Matrix(rows);
		System.out.println("A\n"+ A.toString());
		GaussJordanDecomposition gjd = new GaussJordanDecomposition(rows);
		gjd.decompose();
		System.out.println("U\n"+gjd.getUpperMatrix().toString());
		
		System.out.println("L\n"+gjd.getLowerMatrix().toString());
		
		System.out.println("LU\n"+gjd.getLowerMatrix().multiply(gjd.getUpperMatrix()).toString());
		System.out.println(A.multiply(gjd.inverse));
	}
}

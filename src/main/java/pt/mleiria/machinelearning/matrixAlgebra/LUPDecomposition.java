package pt.mleiria.machinelearning.matrixAlgebra;

/**
 * Lower Upper Permutation (LUP) decomposition
 *
 * 
 */
public class LUPDecomposition {

    /**
     * Rows of the system
     */
    private double[][] rows;
    /**
     * Permutation
     */
    private int[] permutation = null;
    /**
     * Permutation's parity
     */
    private int parity = 1;

    /**
     * Constructor method
     *
     * @param components double[][]
     */
    public LUPDecomposition(final double[][] components) {
        final int n = components.length;
        if (components[0].length != n) {
            throw new IllegalArgumentException("Illegal system: a" + n + " by "
                    + components[0].length + " matrix is not a square matrix");
        }
        rows = components;
        initialize();
    }

    /**
     * Constructor method.
     *
     * @param m Matrix
     */
    public LUPDecomposition(final Matrix m) {
        if (!m.isSquare()) {
            throw new IllegalArgumentException(
                    "Supplied matrix is not a square matrix");
        }
        initialize(m.components);
    }

    /**
     * Constructor method.
     *
     * @param m SymmetricMatrix
     */
    public LUPDecomposition(final SymmetricMatrix m) {
        initialize(m.components);
    }

    /**
     * @return double[]
     * @param xTilde double[]
     */
    private double[] backwardSubstitution(final double[] xTilde) {
        final int n = rows.length;
        final double[] answer = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            answer[i] = xTilde[i];
            for (int j = i + 1; j < n; j++) {
                answer[i] -= rows[i][j] * answer[j];
            }
            answer[i] /= rows[i][i];
        }
        return answer;
    }

    private void decompose() {
        final int n = rows.length;
        permutation = new int[n];
        for (int i = 0; i < n; i++) {
            permutation[i] = i;
        }
        parity = 1;
        try {
            for (int i = 0; i < n; i++) {
                swapRows(i, largestPivot(i));
                pivot(i);
            }
        } catch (ArithmeticException e) {
            parity = 0;
        };
    }

    /**
     * @return boolean	true if decomposition was done already
     */
    private boolean decomposed() {
        if (parity == 1 && permutation == null) {
            decompose();
        }
        return parity != 0;
    }

    /**
     * @return double[]
     * @param c double[]
     */
    public double determinant() {
        if (!decomposed()) {
            return Double.NaN;
        }
        double determinant = parity;
        for (int i = 0; i < rows.length; i++) {
            determinant *= rows[i][i];
        }
        return determinant;
    }

    /**
     * @return double[]
     * @param c double[]
     */
    private double[] forwardSubstitution(final double[] c) {
        final int n = rows.length;
        final double[] answer = new double[n];
        for (int i = 0; i < n; i++) {
            answer[i] = c[permutation[i]];
            for (int j = 0; j <= i - 1; j++) {
                answer[i] -= rows[i][j] * answer[j];
            }
        }
        return answer;
    }
    /**
     * 
     */
    private void initialize() {
        permutation = null;
        parity = 1;
    }

    /**
     * @param components double[][] components obtained from constructor
     * methods.
     */
    private void initialize(final double[][] components) {
        final int n = components.length;
        rows = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rows[i][j] = components[i][j];
            }
        }
        initialize();
    }

    /**
     * @return double[]
     * @param c double[]
     */
    public double[][] inverseMatrixComponents() {
        if (!decomposed()) {
            return null;
        }
        final int n = rows.length;
        final double[][] inverseRows = new double[n][n];
        double[] column = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                column[j] = 0;
            }
            column[i] = 1;
            column = solve(column);
            for (int j = 0; j < n; j++) {
                inverseRows[i][j] = column[j];
            }
        }
        return inverseRows;
    }

    /**
     * @return int
     * @param k int
     */
    private int largestPivot(final int k) {
        double maximum = Math.abs(rows[k][k]);
        double abs;
        int index = k;
        for (int i = k + 1; i < rows.length; i++) {
            abs = Math.abs(rows[i][k]);
            if (abs > maximum) {
                maximum = abs;
                index = i;
            }
        }
        return index;
    }

    /**
     * @param k int
     */
    private void pivot(final int k) {
        final double inversePivot = 1 / rows[k][k];
        final int k1 = k + 1;
        final int n = rows.length;
        for (int i = k1; i < n; i++) {
            rows[i][k] *= inversePivot;
            for (int j = k1; j < n; j++) {
                rows[i][j] -= rows[i][k] * rows[k][j];
            }
        }
    }

    /**
     * @return double[]
     * @param c double[]
     */
    public double[] solve(final double[] c) {
        return decomposed()
                ? backwardSubstitution(forwardSubstitution(c))
                : null;
    }

    /**
     * @return double[]
     * @param c double[]
     */
    public Vector solve(final Vector c) {
        final double[] components = solve(c.components);
        if (components == null) {
            return null;
        }
        return components == null ? null : new Vector(components);
    }

    /**
     * @param i int
     * @param k int
     */
    private void swapRows(final int i, final int k) {
        if (i != k) {
            double temp;
            for (int j = 0; j < rows.length; j++) {
                temp = rows[i][j];
                rows[i][j] = rows[k][j];
                rows[k][j] = temp;
            }
            int nTemp;
            nTemp = permutation[i];
            permutation[i] = permutation[k];
            permutation[k] = nTemp;
            parity = -parity;
        }
    }

    /**
     * Make sure the supplied matrix components are those of a symmetric matrix
     *
     * @param components double
     */
    public static void symmetrizeComponents(final double[][] components) {
        for (int i = 0; i < components.length; i++) {
            for (int j = i + 1; j < components.length; j++) {
                components[i][j] += components[j][i];
                components[i][j] *= 0.5;
                components[j][i] = components[i][j];
            }
        }
    }

    /**
     * Returns a String that represents the value of this object.
     *
     * @return a string representation of the receiver
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        char[] separator = {'[', ' '};
        int n = rows.length;
        for (int i = 0; i < n; i++) {
            separator[0] = '{';
            for (int j = 0; j < n; j++) {
                sb.append(separator);
                sb.append(rows[i][j]);
                separator[0] = ' ';
            }
            sb.append('}');
            sb.append('\n');
        }
        if (permutation != null) {
            sb.append(parity == 1 ? '+' : '-');
            sb.append("( " + permutation[0]);
            for (int i = 1; i < n; i++) {
                sb.append(", " + permutation[i]);
            }
            sb.append(')');
            sb.append('\n');
        }
        return sb.toString();
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
    	LUPDecomposition lup = new LUPDecomposition(rows);
		Matrix inv = new Matrix(lup.inverseMatrixComponents());
		System.out.println("A^1\n"+inv.toString());
		System.out.println("A.A^1\n"+A.multiply(inv));
    }
}

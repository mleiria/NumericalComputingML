package pt.mleiria.machinelearning.matrixAlgebra;

import static java.lang.Math.pow;
import java.util.Random;

/**
 *
 * @author manuel
 */
public class Matrix {

    protected double[][] components;
    protected LUPDecomposition lupDecomposition = null;

    /**
     *
     * @param a
     */
    public Matrix(final double[][] a) {
        components = a;
    }

    /**
     * Creates a Matrix with v.dimension rows and one column
     *
     * @param v
     */
    public Matrix(final Vector v) {
        components = new double[v.dimension()][1];

        for (int i = 0; i < v.dimension(); i++) {
            components[i][0] = v.component(i);
        }
    }

    /**
     * Creates a zero matrix of given dimensions
     *
     * @param n number of rows
     * @param m number of columns
     */
    public Matrix(final int n, final int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal matrix size.");
        }
        components = new double[n][m];
    }

    /**
     * Creates a square matrix
     *
     * @param dimension
     */
    public Matrix(final int dimension) {
        components = new double[dimension][dimension];
    }

    /**
     *
     * @return true if the receiver is a square matrix
     */
    public boolean isSquare() {
        return rows() == columns();
    }

    /**
     *
     * @return the number of rows in the Matrix
     */
    public int rows() {
        return components.length;
    }

    /**
     *
     * @return the number of columns in the Matrix
     */
    public int columns() {
        return components[0].length;
    }

    /**
     * @return Matrix transpose of the receiver
     */
    public Matrix transpose() {
        int n = rows();
        int m = columns();
        double[][] newComponents = new double[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newComponents[j][i] = components[i][j];
            }
        }
        return new Matrix(newComponents);
    }

    /**
     * @return double matrix coords(n,m)
     * @param n int
     * @param m int
     */
    public double component(final int n, final int m) {
        return components[n][m];
    }

    /**
     * Clear the Matrix
     */
    public void clear() {
        // columns size
        int m = components[0].length;
        // rows size
        int n = components.length;
        // for each row
        for (int i = 0; i < n; i++) {
            // for each column
            for (int j = 0; j < m; j++) {
                components[i][j] = 0;
            }
        }
    }

    /**
     * @return double[][] a copy of the components of the receiver.
     */
    public double[][] toComponents() {
        int n = rows();
        int m = columns();
        double[][] answer = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                answer[i][j] = components[i][j];
            }
        }
        return answer;
    }

    /**
     * @return true if the supplied matrix is equal to the receiver.
     * @param matrix Matrix
     */
    public boolean equals(final Matrix matrix) {
        int n = this.rows();
        if (n != matrix.rows()) {
            return false;
        }
        int m = this.columns();
        if (m != matrix.columns()) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (components[i][j] != matrix.components[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return double[][] the components of the product of the receiver with the
     * transpose of the supplied matrix
     * @param a Matrix
     */
    protected double[][] productWithTransposedComponents(Matrix a) {
        int p = this.columns();
        int n = this.rows();
        int m = a.rows();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += components[i][k] * a.components[j][k];
                }
                newComponents[i][j] = sum;
            }
        }
        return newComponents;
    }

    /**
     * @return Matrix inverse of the receiver or pseudoinverse if the receiver
     * is not a square matrix.
     * @exception java.lang.ArithmeticException if the receiver is a singular
     * matrix.
     */
    public Matrix inverse() throws ArithmeticException {
        try {
            return new Matrix(lupDecomposition().inverseMatrixComponents());
        } catch (IllegalArgumentException e) {
            return new Matrix(transposedProduct().inverse().productWithTransposedComponents(this));
        }
    }

    /**
     * @return SymmetricMatrix the transposed product of the receiver with
     * itself.
     */
    public SymmetricMatrix transposedProduct() {
        return new SymmetricMatrix(transposedProductComponents(this));
    }

    /**
     * @return MatrixAlgebra.Matrix product of the tranpose of the receiver with
     * the supplied matrix
     * @param a MatrixAlgebra.Matrix
     * @exception IllegalArgumentException If the number of rows of the receiver
     * are not equal to the number of rows of the supplied matrix.
     */
    public Matrix transposedProduct(Matrix a) {
        if (a.rows() != rows()) {
            throw new IllegalArgumentException("Operation error: cannot multiply a tranposed " + rows() + " by "
                    + columns() + " matrix with a " + a.rows() + " by " + a.columns() + " matrix");
        }
        return new Matrix(transposedProductComponents(a));
    }

    /**
     * @return double[][] the components of the product of the transpose of the
     * receiver with the supplied matrix.
     * @param a MatrixAlgebra.Matrix
     */
    protected double[][] transposedProductComponents(Matrix a) {
        int p = this.rows();
        int n = this.columns();
        int m = a.columns();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += components[k][i] * a.components[k][j];
                }
                newComponents[i][j] = sum;
            }
        }
        return newComponents;
    }

    /**
     * @return LUPDecomposition the LUP decomposition of the receiver.
     */
    protected LUPDecomposition lupDecomposition() {
        if (lupDecomposition == null) {
            lupDecomposition = new LUPDecomposition(this);
        }
        return lupDecomposition;
    }

    /**
     * Computes the components of the sum of the receiver and a supplied matrix.
     *
     * @return double[][]
     * @param a Matrix
     */
    protected double[][] addComponents(Matrix a) {
        int n = this.rows();
        int m = this.columns();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newComponents[i][j] = components[i][j] + a.components[i][j];
            }
        }
        return newComponents;
    }

    /**
     * Returns a string representation of the Matrix.
     *
     * @return java.lang.String
     */
    @Override
    public String toString() {
        final StringBuffer sb;
        sb = new StringBuffer();
        char[] separator = {'[', ' '};
        int n = rows();
        int m = columns();
        for (int i = 0; i < n; i++) {
            separator[0] = '{';
            for (int j = 0; j < m; j++) {
                sb.append(separator);
                sb.append(components[i][j]);
                separator[0] = ' ';
            }
            sb.append('}');
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the Matrix with numRows
     *
     * @param numRows
     * @return java.lang.String
     */
    public String toString(final int numRows) {
        final StringBuffer sb;
        sb = new StringBuffer();
        char[] separator = {'[', ' '};
        int n = numRows;
        int m = columns();
        for (int i = 0; i < n; i++) {
            separator[0] = '{';
            for (int j = 0; j < m; j++) {
                sb.append(separator);
                sb.append(components[i][j]);
                separator[0] = ' ';
            }
            sb.append('}');
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     *
     * @param matrix
     * @return
     */
    public Matrix add(final Matrix matrix) {
        if (matrix.rows() != rows() || matrix.columns() != columns()) {
            throw new IllegalArgumentException("Illegal Matrix size");
        }
        final int n = this.rows();
        final int m = this.columns();
        final double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newComponents[i][j] = components[i][j] + matrix.components[i][j];
            }
        }
        return new Matrix(newComponents);
    }

    /**
     * Subtract the Matrix matrix from the receiver
     *
     * @param matrix
     * @return
     */
    public Matrix subtract(final Matrix matrix) {
        if (matrix.rows() != rows() || matrix.columns() != columns()) {
            throw new IllegalArgumentException("Illegal Matrix size");
        }
        final Matrix auxMatrix = matrix.multiply(-1);
        return add(auxMatrix);
    }

    /**
     * Wise multiplication
     *
     * @param value
     * @return
     */
    public Matrix multiply(final double value) {
        final int n = this.rows();
        final int m = this.columns();
        final double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newComponents[i][j] = components[i][j] * value;
            }
        }
        return new Matrix(newComponents);
    }

    /**
     * Product of the receiver with the supplied matrix
     *
     * @param matrix
     * @return Matrix
     */
    public Matrix multiply(final Matrix matrix) {
        if (matrix.rows() != columns()) {
            throw new IllegalArgumentException("Illegal size.");
        }
        return new Matrix(productComponents(matrix));
    }

    /**
     * Computes the product of the matrix with a vector.
     *
     * @return Vector
     * @param v Vector
     */
    public Vector product(Vector v) {
        int n = this.rows();
        int m = this.columns();
        if (v.dimension() != m) {
            throw new IllegalArgumentException("Product error: " + n + " by " + m
                    + " matrix cannot by multiplied with vector of dimension " + v.dimension());
        }
        return secureProduct(v);
    }

    /**
     * Computes the product of the matrix with a vector.
     *
     * @return Vector
     * @param v Vector
     */
    protected Vector secureProduct(Vector v) {
        int n = this.rows();
        int m = this.columns();
        double[] vectorComponents = new double[n];
        for (int i = 0; i < n; i++) {
            vectorComponents[i] = 0;
            for (int j = 0; j < m; j++) {
                vectorComponents[i] += components[i][j] * v.components[j];
            }
        }
        return new Vector(vectorComponents);
    }

    /**
     * @param matrix
     * @return double[][] The components of the product of the receiver with the
     * supplied matrix
     */
    protected double[][] productComponents(final Matrix matrix) {
        int p = this.columns();
        int n = this.rows();
        int m = matrix.columns();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < p; k++) {
                    sum += components[i][k] * matrix.components[k][j];
                }
                newComponents[i][j] = sum;
            }
        }
        return newComponents;
    }

    /**
     * @return double[][]
     * @param a MatrixAlgebra.Matrix
     */
    protected double[][] subtractComponents(Matrix a) {
        int n = this.rows();
        int m = this.columns();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newComponents[i][j] = components[i][j] - a.components[i][j];
            }
        }
        return newComponents;
    }

    /**
     * @return double[][]
     * @param a double
     */
    protected double[][] productComponents(double a) {
        int n = this.rows();
        int m = this.columns();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newComponents[i][j] = a * components[i][j];
            }
        }
        return newComponents;
    }

    /**
     * Same as product(Matrix a), but without dimension checking.
     *
     * @return MatrixAlgebra.Matrix product of the receiver with the supplied
     * matrix
     * @param a MatrixAlgebra.Matrix
     */
    protected Matrix secureProduct(Matrix a) {
        return new Matrix(productComponents(a));
    }

    /**
     * Splits the receiver in two Matrices
     *
     * @param numCols number of columns of the Matrix[1]
     * @return Matrix[2] where Matrix[0] = Matrix[rows, cols-numCols] Matrix[1]
     * = Matrix[rows, numCols
     */
    public Matrix[] split(final int numCols) {
        if (numCols >= columns()) {
            throw new IllegalArgumentException("numCols must be smaller than columns");
        }
        final int rows = this.rows();
        final int cols = this.columns();
        final int diffCols = cols - numCols;
        final double[][] a = new double[rows][diffCols];
        final double[][] b = new double[rows][numCols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j < diffCols) {
                    a[i][j] = components[i][j];
                } else {
                    b[i][diffCols - j] = components[i][j];
                }
            }
        }
        final Matrix[] result = new Matrix[2];
        final Matrix aa = new Matrix(a);
        final Matrix bb = new Matrix(b);
        result[0] = aa;
        result[1] = bb;
        return result;

    }

    /**
     *
     * @param colIndex
     * @return a Column vector
     */
    public Vector getColumn(final int colIndex) {
        if (colIndex > columns() || colIndex < 0) {
            throw new IllegalArgumentException("Column index out of range.");
        }
        final double[] vComps = new double[rows()];
        for (int i = 0; i < rows(); i++) {
            vComps[i] = component(i, colIndex);
        }
        return new Vector(vComps);
    }

    /**
     *
     * @param rowIndex
     * @return a Row vector
     */
    public Vector getRow(final int rowIndex) {
        if (rowIndex > rows() || rowIndex < 0) {
            throw new IllegalArgumentException("Row index out of range.");
        }
        final double[] vComps = new double[columns()];
        for (int i = 0; i < columns(); i++) {
            vComps[i] = component(rowIndex, i);
        }
        return new Vector(vComps);
    }

    /**
     * Splits the receiver in two Matrices Matrix[0] is the train Matrix
     * Matrix[1] is the test Matrix
     *
     * @param percentageOfTrainingData
     * @param isShuffle whether to shuffle or not the data
     * @return Matrix[2]
     */
    public Matrix[] trainTestSplit(double percentageOfTrainingData, final boolean isShuffle) {
        final int rows = rows();
        final int cols = columns();
        final int dim = (int) (rows() * percentageOfTrainingData);

        double[][] train = new double[dim][cols];
        double[][] test = new double[rows - dim][cols];

        if (isShuffle) {
            final Random rnd = new Random();
            for (int i = rows - 1; i > 0; i--) {
                final int index = rnd.nextInt(i + 1);
                final double[] a = components[index];
                components[index] = components[i];
                components[i] = a;
            }
        }
        for (int i = 0; i < rows; i++) {
            if (i < dim) {
                System.arraycopy(components[i], 0, train[i], 0, cols);
            } else {
                System.arraycopy(components[i], 0, test[i - dim], 0, cols);
            }
        }
        return new Matrix[]{new Matrix(train), new Matrix(test)};
    }

    /**
     * Add a column of ones in column[0] to the receiver
     *
     * @return
     */
    public Matrix addOnes() {
        final int rows = rows();
        final int columns = columns() + 1;

        double[][] newComponents = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            newComponents[i][0] = 1.0;
            for (int j = 1; j < columns; j++) {
                newComponents[i][j] = component(i, j - 1);
            }
        }
        return new Matrix(newComponents);
    }

    /**
     * Append a column in the end of the receiver
     *
     * @param v
     * @return
     */
    public Matrix append(final Vector v) {
        final int rows = rows();
        if (v.dimension() != rows()) {
            throw new IllegalArgumentException(v.dimension() + " not equal to " + rows);
        }

        final int columns = columns() + 1;

        double[][] newComponents = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            newComponents[i][columns - 1] = v.component(i);
            for (int j = 0; j < columns - 1; j++) {
                newComponents[i][j] = component(i, j);
            }
        }
        return new Matrix(newComponents);
    }

    /**
     *
     * @param exponent
     * @return
     */
    public Matrix power(double exponent) {
        final double[][] newComponents = new double[rows()][columns()];
        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < columns(); j++) {
                newComponents[i][j] = pow(components[i][j], exponent);
            }
        }
        return new Matrix(newComponents);
    }

    /**
     * Sum of array elements over a given axis
     *
     * @param axis 1 for rows and 2 for cols
     * @return
     */
    public Matrix sum(int axis) {
        if (axis != 1 && axis != 2) {
            throw new IllegalArgumentException("1 - Rows; 2 - Columns");
        }
        double[][] newComponents = null;
        if (axis == 1) {
            newComponents = new double[rows()][1];
            for (int i = 0; i < rows(); i++) {
                double result = 0;
                for (int j = 0; j < columns(); j++) {
                    result += components[i][j];
                }
                newComponents[i][0] = result;
            }
        }
        if (axis == 2) {
            newComponents = new double[1][columns()];
            for (int i = 0; i < columns(); i++) {
                double result = 0;
                for (int j = 0; j < rows(); j++) {
                    result += components[i][j];
                }
                newComponents[0][i] = result;
            }
        }
        return new Matrix(newComponents);

    }

    /**
     *
     * @param order
     * @return
     */
    public Matrix polynomialOrder(final int order) {
        Matrix augmented = new Matrix(components);
        for (int i = 2; i <= order; i++) {
            final double[] v = new double[rows()];
            for (int j = 0; j < v.length; j++) {
                v[j] = pow(component(j, 1), i);
            }
            augmented = new Matrix(augmented.append(new Vector(v)).toComponents());
        }
        return augmented;
    }
}

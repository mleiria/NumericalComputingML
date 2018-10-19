/*
 * Vector implementation
 */
package pt.mleiria.machinelearning.matrixAlgebra;

import static java.lang.Math.random;
import static java.lang.Math.sqrt;
import static java.lang.System.arraycopy;
import static java.util.Arrays.stream;
import java.util.DoubleSummaryStatistics;
import static java.util.stream.Collectors.summarizingDouble;

/**
 *
 * @author manuel
 */
public class Vector {

    protected double[] components;

    /**
     * Create a vector of given dimension.
     *
     * @param comp double[]
     */
    public Vector(final double comp[]) {
        final int n = comp.length;
        if (n <= 0) {
            throw new IllegalArgumentException("Empty Vector not allowed");
        }
        components = new double[n];
        arraycopy(comp, 0, components, 0, n);
    }

    /**
     * Create a vector of given dimension.
     *
     * @param dimension int dimension of the vector;
     */
    public Vector(final int dimension) {
        if (dimension <= 0) {
            throw new IllegalArgumentException("Vector dimension must be positive");
        }
        components = new double[dimension];
        clear();
    }

    /**
     * Accumulate x in the receiver
     *
     * @param x
     */
    public void accumulate(final double[] x) {
        if (this.dimension() != x.length) {
            throw new IllegalArgumentException("Attempt to add a "
                    + this.dimension() + "-dimension vector to a "
                    + x.length + "-dimension array");
        }
        for (int i = 0; i < this.dimension(); i++) {
            components[i] += x[i];
        }
    }

    /**
     * Accumulate v in the receiver
     *
     * @param v
     */
    public void accumulate(final Vector v) {
        if (this.dimension() != v.dimension()) {
            throw new IllegalArgumentException("Attempt to add a "
                    + this.dimension() + "-dimension vector to a "
                    + v.dimension() + "-dimension vector");
        }
        for (int i = 0; i < this.dimension(); i++) {
            components[i] += v.components[i];
        }
    }

    /**
     * Adds a vector to the receiver
     *
     * @param v
     * @return
     */
    public Vector add(final Vector v) {
        if (this.dimension() != v.dimension()) {
            throw new IllegalArgumentException("Attempt to add a "
                    + this.dimension() + "-dimension vector to a "
                    + v.dimension() + "-dimension vector");
        }
        double[] newComponents = new double[this.dimension()];
        for (int i = 0; i < this.dimension(); i++) {
            newComponents[i] = components[i] + v.components[i];
        }
        return new Vector(newComponents);
    }

    /**
     *
     * @param v
     * @return
     */
    public Vector subtract(Vector v) {
        v = v.product(-1);
        return add(v);
    }

    /**
     * @return Matrix	tensor product with the specified vector
     * @param v Vector	second vector to build tensor product with.
     */
    public Matrix tensorProduct(Vector v) {
        int n = dimension();
        int m = v.dimension();
        double[][] newComponents = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newComponents[i][j] = components[i] * v.components[j];
            }
        }
        return new Matrix(newComponents);
    }
    /**
     * 
     * @param v
     * @return  Vector wise multiplication where res[i] = a[i] * b[i]
     */
    public Vector wiseMultiplication(final Vector v){
        final double[] res = new double[dimension()];
        for(int i = 0; i < dimension(); i++){
            res[i] = component(i) * v.component(i);
        }
        return new Vector(res);
    }

    /**
     * Sets all components of the receiver to 0.
     */
    public final void clear() {
        for (int i = 0; i < components.length; i++) {
            components[i] = 0;
        }
    }

    /**
     * @return double
     * @param n int
     */
    public double component(int n) {
        return components[n];
    }

    /**
     * Returns the dimension of the vector.
     *
     * @return int
     */
    public int dimension() {
        return components.length;
    }

    /**
     * @return true if the supplied vector is equal to the receiver
     * @param v
     */
    public boolean equals(final Vector v) {
        int n = this.dimension();
        if (v.dimension() != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (v.components[i] != components[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the norm of the receiver
     */
    public double norm() {
        double sum = 0;
        for (int i = 0; i < components.length; i++) {
            sum += components[i] * components[i];
        }
        return sqrt(sum);
    }

    /**
     *
     * @param x
     * @return the receiver normalized by x
     */
    public Vector normalizedBy(final double x) {
        for (int i = 0; i < this.dimension(); i++) {
            components[i] /= x;
        }
        return this;
    }

    /**
     *
     * @param d
     * @return
     */
    public Vector product(final double d) {
        double newComponents[] = new double[components.length];
        for (int i = 0; i < components.length; i++) {
            newComponents[i] = d * components[i];
        }
        return new Vector(newComponents);
    }

    /**
     * Compute the scalar product (or dot product) of two vectors.
     *
     * @return double the scalar product of the receiver with the argument
     * @param v
     */
    public double product(final Vector v) {
        int n = v.dimension();
        if (components.length != n) {
            throw new IllegalArgumentException("Dimension of Vector v does not match.");
        }
        return secureProduct(v);
    }

    /**
     * Computes the product of the transposed vector with a matrix
     *
     * @return Vector
     * @param a Matrix
     */
    public Vector product(final Matrix a) {
        int n = a.rows();
        int m = a.columns();
        if (this.dimension() != n) {
            throw new IllegalArgumentException(
                    "Product error: transposed of a " + this.dimension()
                    + "-dimension vector cannot be multiplied with a "
                    + n + " by " + m + " matrix");
        }
        return secureProduct(a);
    }

    /**
     * @param x double
     * @return
     */
    public Vector scaledBy(final double x) {
        for (int i = 0; i < this.dimension(); i++) {
            components[i] *= x;
        }
        return this;
    }

    /**
     * @return double the dot product of the receiver with the argument
     * @param v
     */
    protected double secureProduct(final Vector v) {
        double sum = 0;
        for (int i = 0; i < v.dimension(); i++) {
            sum += components[i] * v.components[i];
        }
        return sum;
    }

    /**
     * Computes the product of the transposed vector with a matrix
     *
     * @return Vector
     * @param a Matrix
     */
    protected Vector secureProduct(final Matrix a) {
        final int n = a.rows();
        final int m = a.columns();
        final double[] vectorComponents = new double[m];
        for (int j = 0; j < m; j++) {
            vectorComponents[j] = 0;
            for (int i = 0; i < n; i++) {
                vectorComponents[j] += components[i] * a.components[i][j];
            }
        }
        return new Vector(vectorComponents);
    }

    /**
     * Creates a matrix with random elements uniformly distributed on the
     * interval (0, 1).
     *
     * @return Vector
     */
    public Vector rand() {
        final int m = dimension();
        final double[] v = new double[m];
        for (int i = 0; i < m; i++) {
            v[i] = random();
        }
        return new Vector(v);
    }

    /**
     *
     * @param p
     * @return
     */
    public double norm(final double p) {
        if (p < 1.0) {
            throw new IllegalArgumentException("p must be >= 1");
        }
        double res = 0.0;
        for (int i = 0; i < components.length; i++) {
            res += Math.pow(Math.abs(components[i]), p);
        }
        return Math.pow(res, 1.0 / p);
    }

    /**
     * @return double[]	a copy of the components of the receiver.
     */
    public double[] toComponents() {
        int n = dimension();
        double[] answer = new double[n];
        arraycopy(components, 0, answer, 0, n);
        return answer;

    }

    /**
     *
     * @return Statistics for the vector
     */
    public DoubleSummaryStatistics getSummaryStatistics() {
        return stream(components).mapToObj(d -> d)
                .collect(summarizingDouble(Double::doubleValue));
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return java.lang.String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        char[] separator = {'[', ' '};
        for (int i = 0; i < components.length; i++) {
            sb.append(separator);
            sb.append(components[i]);
            separator[0] = ',';
        }
        sb.append(']');
        return sb.toString();
    }

}

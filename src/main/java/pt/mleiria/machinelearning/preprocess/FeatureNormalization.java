package pt.mleiria.machinelearning.preprocess;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
/**
 *
 * @author manuel
 */
public interface FeatureNormalization {
    
    Matrix normalize(final Matrix matrix);
    
    Vector normalize(final Vector vector);
}

package pt.mleiria.machinelearning.preprocess;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
/**
 *
 * @author manuel
 */
public interface FeatureNormalization {
    
    Matrix normalize(final Matrix matrix);
}

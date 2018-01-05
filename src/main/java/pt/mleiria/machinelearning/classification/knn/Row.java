/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.classification.knn;

import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public abstract class Row {

    /**
     * Method that returns the tag or class of the example
     *
     * @return The tag or class of the examples
     */
    public abstract String getTag();

    /**
     * Method that return the values of the attributes of the example as an
     * array of doubles
     *
     * @return The values of the attributes of the example
     */
    public abstract Vector getExample();

    @Override
    public String toString() {
        return getExample().toString() + " : "  + getTag();
    }
    
    
}

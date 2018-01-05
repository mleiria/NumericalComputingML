/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.jblas;

import org.jblas.DoubleMatrix;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class TesteJblas {

    public static void main(String[] args) {
        DoubleMatrix a = DoubleMatrix.rand(10000, 10000);
        DoubleMatrix b = DoubleMatrix.rand(10000, 10000);
        
        DoubleMatrix res = a.mmul(b);
        
        System.out.println(res.toString());
    }

}

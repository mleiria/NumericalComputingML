/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.classification;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import static java.lang.System.out;
import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.functions.Log;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class LRTest {
    public static void main(String[] args){
        double[][] components = new double[3][2];
        components[0][0] = 1;
        components[1][0] = 2;
        components[2][0] = 3;
        components[0][1] = 4;
        components[1][1] = 5;
        components[2][1] = 6;
        Matrix featuresX = new Matrix(components);
        double[] thetaComponents = new double[]{0, 0};
        double[] yComponents = new double[]{1,2,3};
        Vector y = new Vector(yComponents);
        Vector theta = new Vector(thetaComponents);
        Vector a = featuresX.product(theta);
        out.println("[+]a:\n"+a.toString());
        Matrix b = new Sigmoid().value(new Matrix(a));
        out.println("[+]b:\n"+b.toString());
        Matrix c = new Log().value(b);
        out.println("[+]c:\n"+c.toString());
        Matrix d = c.transpose();
        out.println("[+]d:\n"+d.toString());
        double e = d.product(y).component(0);
        out.println(e);
        Matrix ones = MatrixUtils.Ones(b.rows(), b.columns());
        out.println("[+]ones:\n"+ones.toString());
        Matrix f = ones.subtract(b);
        out.println("[+]f:\n"+f.toString());
        Matrix g = new Log().value(f);
        out.println("[+]g:\n"+g.toString());
        Matrix h = g.transpose();
        out.println("[+]h:\n"+h.toString());
        Vector vOnes = MatrixUtils.toVector(MatrixUtils.Ones(y.dimension(), 1));
        out.println("[+]vOnes:\n"+vOnes.toString());
        Vector i = vOnes.subtract(y);
        out.println("[+]i:\n"+i.toString());
        double j = h.product(i).component(0);
        out.println(j);
        double coeff = -1.0 / featuresX.rows();
        out.println("[+]Final:"+coeff * e + j);
        
        
        
        
        
        
        
        
    }
}

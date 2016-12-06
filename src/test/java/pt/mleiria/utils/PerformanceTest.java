package pt.mleiria.utils;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.numericalAnalysis.utils.TimeLog;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author manuel
 */
public class PerformanceTest {

    public static void main(String[] args) {
        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        for (int i = 100; i < 3000; i = i + 150) {

            final Matrix a = new Matrix(i).rand();
            final Matrix b = new Matrix(i).rand();

            TimeLog tl = new TimeLog();
            final Matrix mul = a.multiply(b);
            double time = tl.elapsedTime();
            System.out.println(i + "," + time);
            x.append(i).append(",");
            y.append(time).append(",");
        }
        System.out.println(x.toString());
        System.out.println(y.toString());
        
        System.out.println("--------------------");
        
        x = new StringBuilder();
        y = new StringBuilder();
        for (int i = 100; i < 3000; i = i + 150) {

            final Matrix a = new Matrix(i).rand();
            final Vector b = new Vector(i).rand();

            TimeLog tl = new TimeLog();
            final Vector mul = b.product(a);
            double time = tl.elapsedTime();
            System.out.println(i + "," + time);
            x.append(i).append(",");
            y.append(time).append(",");
        }
        System.out.println(x.toString());
        System.out.println(y.toString());
        
        
        
    }

}

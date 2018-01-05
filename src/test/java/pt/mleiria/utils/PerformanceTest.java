package pt.mleiria.utils;

import static java.lang.System.out;
import static java.util.Collections.shuffle;
import java.util.List;
import java.util.Random;
import static java.util.stream.Collectors.toList;
import static java.util.stream.DoubleStream.of;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import static pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils.rand;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.numericalAnalysis.utils.StopWatch;

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
        // matrixTest();

        /*
	 * int size = 10; double[] arrS = new double[size]; for(int i = 0; i <
	 * size; i++){ arrS[i] = Math.random(); if(i <
	 * 5)System.out.print(arrS[i] + " ; "); } double[] arrF = arrS;
	 * 
	 * shuffleArraysCollections(arrS); shuffleArraysFicherYates(arrF);
         */
        dotProduct();
        //matrixTest();
    }

    private static void dotProduct() {

        Matrix ma = MatrixUtils.rand(7000, 7000);
        Matrix mb = MatrixUtils.rand(7000, 7000);
        StopWatch tl = new StopWatch();
        Matrix finalV = ma.multiply(mb);
        out.println(tl.elapsedTime());
        //out.println(finalV.toString());

    }

    private static void shuffleArraysFicherYates(double[] arr) {
        int size = arr.length;
        // Fisher-Yates
        StopWatch tl = new StopWatch();
        final Random rnd = new Random();
        for (int i = size - 1; i > 0; i--) {
            final int index = rnd.nextInt(i + 1);
            final double a = arr[index];
            arr[index] = arr[i];
            arr[i] = a;
        }
        out.println("\nElapsed time Fisher:" + tl.elapsedTime());
        for (int i = 0; i < 5; i++) {
            out.print(arr[i] + " ; ");
        }

    }

    private static void shuffleArraysCollections(double[] arr) {

        int size = arr.length;
        // Collections Shuffle
        StopWatch tl = new StopWatch();
        List<Double> d = of(arr).mapToObj(Double::valueOf).collect(toList());
        shuffle(d);
        for (int i = 0; i < size; i++) {
            arr[i] = d.get(i);
        }
        out.println("\nElapsed time Collections:" + tl.elapsedTime());
        for (int i = 0; i < 5; i++) {
            out.print(arr[i] + " ; ");
        }
    }

    private static void matrixTest() {
        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        for (int i = 100; i < 3000; i = i + 150) {

            final Matrix a = rand(i, i);
            final Matrix b = rand(i, i);

            StopWatch tl = new StopWatch();
            final Matrix mul = a.multiply(b);
            double time = tl.elapsedTime();
            out.println(i + "," + time);
            x.append(i).append(",");
            y.append(time).append(",");
        }
        out.println(x.toString());
        out.println(y.toString());

        out.println("--------------------");

        x = new StringBuilder();
        y = new StringBuilder();
        for (int i = 100; i < 3000; i = i + 150) {

            final Matrix a = rand(i, i);
            final Vector b = new Vector(i).rand();

            StopWatch tl = new StopWatch();
            final Vector mul = b.product(a);
            double time = tl.elapsedTime();
            out.println(i + "," + time);
            x.append(i).append(",");
            y.append(time).append(",");
        }
        out.println(x.toString());
        out.println(y.toString());
    }

}

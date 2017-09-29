package pt.mleiria.utils;

import static java.lang.System.out;
import java.util.Collections;
import static java.util.Collections.shuffle;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.stream.DoubleStream;
import static java.util.stream.DoubleStream.of;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import static pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils.rand;
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
	// matrixTest();

	/*
	 * int size = 10; double[] arrS = new double[size]; for(int i = 0; i <
	 * size; i++){ arrS[i] = Math.random(); if(i <
	 * 5)System.out.print(arrS[i] + " ; "); } double[] arrF = arrS;
	 * 
	 * shuffleArraysCollections(arrS); shuffleArraysFicherYates(arrF);
	 */

	// dotProduct();
	matrixTest();
    }

    private static void dotProduct() {
	int N = 500;
	int R = 200;
	double[][] t = new double[N][R];
	double[] u = new double[R];
	double[][] uu = new double[R][0];

	final Random r = new Random();

	for (int i = 0; i < N; i++) {
	    for (int j = 0; j < R; j++) {
		if (i == 0) {
		    u[j] = r.nextDouble();
		    uu[j][0] = r.nextDouble();
		}
		t[i][j] = r.nextDouble();
	    }
	}
	Matrix m = new Matrix(t);
	Vector v = new Vector(u);

	Matrix mm = new Matrix(uu);

	TimeLog tl = new TimeLog();
	Matrix finalV = m.multiply(mm);
	out.println(tl.elapsedTime());
	out.println(finalV.toString());

    }

    private static void shuffleArraysFicherYates(double[] arr) {
	int size = arr.length;
	// Fisher-Yates
	TimeLog tl = new TimeLog();
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
	TimeLog tl = new TimeLog();
	List<Double> d = of(arr).mapToObj(Double::valueOf).collect(toList());
	shuffle(d);
	for (int i = 0; i < size; i++)
	    arr[i] = d.get(i);
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

	    TimeLog tl = new TimeLog();
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

	    TimeLog tl = new TimeLog();
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

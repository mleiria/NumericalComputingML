/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.numericalAnalysis.utils;

/**
 *
 * @author manuel
 */
public class ViewUtils {

    /**
     *
     * @param array
     * @return
     */
    public static String showArrayContents(double[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                sb.append(array[i]).append("\n");
            } else {
                sb.append(array[i]).append(";").append("\n");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     *
     * @param arr
     * @return
     */
    public static String showArrayContents(int[] arr) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]).append(";");
        }
        sb.append(arr[arr.length - 1]).append("]");
        return sb.toString();
    }

    /**
     *
     * @param arr
     * @return
     */
    public static String showArrayContents(String[] arr) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append(arr[i]).append(";");
        }
        sb.append(arr[arr.length - 1]).append("]");
        return sb.toString();
    }

    /**
     *
     * @param arr
     * @return
     */
    public static String showArrayContents(int[][] arr) {
        final StringBuffer sb = new StringBuffer("\n");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param arr
     * @return
     */
    public static String showArrayContents(double[][] arr) {
        final StringBuffer sb = new StringBuffer("\n");
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sb.append(arr[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param arr
     * @return
     */
    public static String showArrayContents(byte[] arr) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < arr.length - 1; i++) {
            sb.append((char) arr[i]).append(";");
        }
        sb.append(arr[arr.length - 1]).append("]");
        return sb.toString();
    }

}

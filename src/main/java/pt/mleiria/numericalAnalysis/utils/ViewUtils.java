/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.numericalAnalysis.utils;

import static java.lang.System.arraycopy;
import java.util.List;
import java.util.Map;

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
                sb.append(array[i]);
            } else {
                sb.append(array[i]).append(",");
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
    public static String showArrayContents(int[] array) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                sb.append(array[i]);
            } else {
                sb.append(array[i]).append(",");
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
        for (int[] arr1 : arr) {
            for (int j = 0; j < arr1.length; j++) {
                sb.append(arr1[j]).append(" ");
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
        for (double[] arr1 : arr) {
            for (int j = 0; j < arr1.length; j++) {
                sb.append(arr1[j]).append(" ");
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

    /**
     *
     * @param map
     * @return
     */
    public static String showSeparatedByClass(final Map<Integer, List<Double[]>> map) {
        final StringBuffer sb = new StringBuffer();
        for (Map.Entry<Integer, List<Double[]>> entry : map.entrySet()) {
            sb.append("\nKey:").append(entry.getKey()).append("\n");
            List<Double[]> valueLst = entry.getValue();
            for (Double[] d : valueLst) {
                sb.append("[");
                for (int i = 0; i < d.length - 1; i++) {
                    sb.append(d[i] + ":");
                }
                sb.append(d[d.length - 1]).append("]\n");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param data
     * @return
     */
    public static String showHtmlTable(final List<String[]> data) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<table>\n");
        for (final String[] row : data) {
            sb.append("<tr>");
            for (final String str : row) {
                sb.append("<td>" + str + "</td>");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }

    /**
     *
     * @param data
     * @param numColsToShow
     * @return
     */
    public static String showHtmlTable(final List<String[]> data, final int numColsToShow) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<table>\n");
        for (final String[] row : data) {
            final String[] subRow = new String[numColsToShow];

            arraycopy(row, 0, subRow, 0, numColsToShow);
            sb.append("<tr>");
            for (final String str : subRow) {
                sb.append("<td>" + str + "</td>");
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }

    /**
     *
     * @param data
     * @param colIndex
     * @return
     */
    public static String showArrayPyFormat(final List<String[]> data, int colIndex) {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (final String[] row : data) {
            sb.append(row[colIndex]);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

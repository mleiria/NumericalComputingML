/**
 *
 */
package pt.mleiria.numericalAnalysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * @author mleiria
 */
public class Utils {

    // Comprimento da linha

    private static int lineSize;

    /**
     * @param precision quantas casas decimais para a precisao
     * @return MathContext
     */
    public static MathContext setPrecision(int precision) {
        return new MathContext(precision, RoundingMode.HALF_EVEN);
    }

    /**
     * int -> ###,###,###,###,##0 double ->###,###,###,###,##0.00
     *
     * @param valorEntrada
     * @param mascara
     * @return o numero formatado
     */
    public static String formataNumero(double valorEntrada, String mascara) {
        NumberFormat formato = new DecimalFormat(mascara);
        String saida = formato.format(valorEntrada);
        return (saida);
    }

    /**
     * @return a data corrente no formato aaaa-mm-dd hh:mm:ss
     */
    public static String getCurrentDate() {
        Calendar cal = new GregorianCalendar();
		// Get the components of the date
        // int era = cal.get(Calendar.ERA); // 0=BC, 1=AD
        String year = String.valueOf(cal.get(Calendar.YEAR)); // 2002
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1); // 0=Jan,
        // 1=Feb,
        // ...
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)); // 1...
        String hour = String.valueOf(cal.get(Calendar.HOUR));
        String min = String.valueOf(cal.get(Calendar.MINUTE));
        String sec = String.valueOf(cal.get(Calendar.SECOND));

        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (min.length() == 1) {
            min = "0" + min;
        }
        if (sec.length() == 1) {
            sec = "0" + sec;
        }
        String currentDate = year + "-" + month + "-" + day + " " + hour + ":"
                + min + ":" + sec;
        return currentDate;
    }

    /**
     * @param fileName
     * @param isForAppend true se for para acrescentar ao ficheiro
     * @param data os dados vem sob a forma de um ArrayList<String[][2]> onde
     * String[i][0] contem os dados e String[i][1] contem a mascara
     */
    public static void appendToFile(String fileName, boolean isForAppend,
            ArrayList<String[][]> data) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(fileName,
                    isForAppend));
            Iterator<String[][]> iter = data.iterator();
            while (iter.hasNext()) {
                String[][] element = iter.next();
                for (int i = 0; i < element.length; i++) {
                    int mask = Integer.parseInt(element[i][1]) - element[i][0].length();
                    while (--mask >= 0) {
                        out.write(" ");
                    }
                    out.write(element[i][0]);
                }
                out.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
        
    }

    public static boolean clearFile(String fileName) {
        return (new File(fileName)).delete();
    }

    /**
     * Mostra o texto na consola alinhado a direita
     *
     * @param text o texto a mostrar
     *
     * @param width a largura da coluna
     *
     */
    public static void print(String text, int width) {
        int mask = width - text.length();
        while (--mask >= 0) {
            System.out.print(" ");
        }
        System.out.print(text);

        lineSize += width;
    }

    /**
     * Mostra um inteiro na consola alinhado a direita
     *
     * @param value o valor a mostrar
     * @param width the column width
     */
    public static void print(int value, int width) {
        print(Integer.toString(value), width);
    }

    /**
     * Mostra um float na consola alinhado a direita
     *
     * @param value o valor a mostrar
     */
    public static void print(float value, int width) {
        print(Float.toString(value), width);
    }

    /**
     * Mostra um double na consola alinhado a direita
     *
     * @param value o valor a mostrar
     * @param width a largura da coluna
     */
    public static void print(double value, int width) {
        print(Double.toString(value), width);
    }

    /**
     * Print a line.
     */
    public static void println() {
        System.out.println();
        lineSize = 0;
    }

    /**
     * Print an underline.
     */
    public static void underline() {
        System.out.println();
        for (int i = 0; i < lineSize; ++i) {
            System.out.print("-");
        }
        System.out.println();
        lineSize = 0;
    }
}

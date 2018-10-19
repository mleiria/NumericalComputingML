/**
 *
 */
package pt.mleiria.numericalAnalysis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.System.out;
import java.math.MathContext;
import java.math.RoundingMode;
import static java.math.RoundingMode.HALF_EVEN;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
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
        return new MathContext(precision, HALF_EVEN);
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
        String year = valueOf(cal.get(YEAR)); // 2002
        String month = valueOf(cal.get(MONTH) + 1); // 0=Jan,
        // 1=Feb,
        // ...
        String day = valueOf(cal.get(DAY_OF_MONTH)); // 1...
        String hour = valueOf(cal.get(HOUR));
        String min = valueOf(cal.get(MINUTE));
        String sec = valueOf(cal.get(SECOND));

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
                for (String[] element1 : element) {
                    int mask = parseInt(element1[1]) - element1[0].length();
                    while (--mask >= 0) {
                        out.write(" ");
                    }
                    out.write(element1[0]);
                }
                out.write("\n");
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
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
            out.print(" ");
        }
        out.print(text);

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
        out.println();
        lineSize = 0;
    }

    /**
     * Print an underline.
     */
    public static void underline() {
        out.println();
        for (int i = 0; i < lineSize; ++i) {
            out.print("-");
        }
        out.println();
        lineSize = 0;
    }
}

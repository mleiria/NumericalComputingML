/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.perceptron;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import pt.mleiria.machinelearning.distributions.Gaussian;
import pt.mleiria.machinelearning.functions.Step;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;

/**
 *
 * @author manuel.leiria
 */
public class Perceptron {
    /*
    Dimensao dos dados de input
    */
    private final int nIn;     
    /*
    Vector de pesos do perceptron
    */
    private final double[] w;
    
    private final OneVarFunction<Double> activationFunction;
    /**
     * 
     * @param nIn 
     * @param activationFunction 
     */
    public Perceptron(int nIn, OneVarFunction<Double> activationFunction) {
        this.nIn = nIn;
        this.activationFunction = activationFunction;
        w = new double[nIn];
    }
    /**
     * 
     * @param x
     * @param t
     * @param learningRate
     * @return 
     */
    public int train(double[] x, int t, double learningRate) {
        int classified = 0;
        double c = 0.;

        // Verifica se os dados estao classificados correctamente
        for (int i = 0; i < nIn; i++) {
            c += w[i] * x[i] * t;
        }
        /*
        Aplica o metodo do gradiente descendente se os dados 
        nao estao bem classificados
        */
        if (c > 0) {
            classified = 1;
        } else {
            for (int i = 0; i < nIn; i++) {
                w[i] += learningRate * x[i] * t;
            }
        }
        return classified;
    }
    /**
     * 
     * @param x
     * @return 
     */
    public int predict(double[] x) {
        double preActivation = 0.;
        for (int i = 0; i < nIn; i++) {
            preActivation += w[i] * x[i];
        }
        return activationFunction.value(preActivation).intValue();
    }
    /**
     * 
     * @param train_N
     * @param test_N
     * @param train_X
     * @param train_T
     * @param test_X
     * @param test_T 
     */
    public void loadData(final int train_N, final int test_N, final double[][] train_X, final int[] train_T, final double[][] test_X, final int[] test_T){
        
        
        final Random rng = new Random(1234);  // seed
        final Gaussian g1 = new Gaussian(-2.0, 1.0, Optional.of(rng));
        final Gaussian g2 = new Gaussian(2.0, 1.0, Optional.of(rng));

        // data set  classe 1
        for (int i = 0; i < train_N / 2 - 1; i++) {
            train_X[i][0] = g1.random();
            train_X[i][1] = g2.random();
            train_T[i] = 1;
        }
        for (int i = 0; i < test_N / 2 - 1; i++) {
            test_X[i][0] = g1.random();
            test_X[i][1] = g2.random();
            test_T[i] = 1;
        }

        // data set classe 2
        for (int i = train_N / 2; i < train_N; i++) {
            train_X[i][0] = g2.random();
            train_X[i][1] = g1.random();
            train_T[i] = -1;
        }
        for (int i = test_N / 2; i < test_N; i++) {
            test_X[i][0] = g2.random();
            test_X[i][1] = g1.random();
            test_T[i] = -1;
        }
    }

    public static void main(String[] args) {

        //
        //Inicializa os dados para teste e validacao
        //
        final int train_N = 1000;  // Numero de dados para treinar
        final int test_N = 200;   // Numero de dados para testar
        final int nIn = 2;        // Dimensao dos dados

        double[][] train_X = new double[train_N][nIn];  // Dados de input para treinar
        int[] train_T = new int[train_N];               // Labels de treino

        double[][] test_X = new double[test_N][nIn];  // Dados de input para testar
        int[] test_T = new int[test_N];               // Labels de teste
        int[] predicted_T = new int[test_N];          // Labels previstas pelo modelo

        final int epochs = 2000;   // Numero maximo de epocas de treino
        final double learningRate = 1.;  // Taxa de aprendizagem
         //
        // Modelo SingleLayer Neural Networks
        //
        int epoch = 0;  // epocas de treino

        final Perceptron classifier = new Perceptron(nIn, new Step());
        classifier.loadData(train_N, test_N, train_X, train_T, test_X, test_T);
        
        // Treinar o modelo
        while (true) {
            int classified_ = 0;

            for (int i = 0; i < train_N; i++) {
                classified_ += classifier.train(train_X[i], train_T[i], learningRate);
            }

            if (classified_ == train_N) {
                break;  // Quando todos os dados estao classificados corretamente
            }
            epoch++;
            if (epoch > epochs) {
                break;
            }
        }

        // Testar o modelo
        for (int i = 0; i < test_N; i++) {
            predicted_T[i] = classifier.predict(test_X[i]);
        }

        //
        // Avaliar o modelo
        //
        int[][] confusionMatrix = new int[2][2];
        double accuracy = 0.;
        double precision = 0.;
        double recall = 0.;

        for (int i = 0; i < test_N; i++) {

            if (predicted_T[i] > 0) {
                if (test_T[i] > 0) {
                    accuracy += 1;
                    precision += 1;
                    recall += 1;
                    confusionMatrix[0][0] += 1;
                } else {
                    confusionMatrix[1][0] += 1;
                }
            } else {
                if (test_T[i] > 0) {
                    confusionMatrix[0][1] += 1;
                } else {
                    accuracy += 1;
                    confusionMatrix[1][1] += 1;
                }
            }

        }

        accuracy /= test_N;
        precision /= confusionMatrix[0][0] + confusionMatrix[1][0];
        recall /= confusionMatrix[0][0] + confusionMatrix[0][1];
        out.println(Arrays.toString(classifier.w));
        out.println("----------------------------");
        out.printf("Accuracy:  %.1f %%\n", accuracy * 100);
        out.printf("Precision: %.1f %%\n", precision * 100);
        out.printf("Recall:    %.1f %%\n", recall * 100);

    }
}

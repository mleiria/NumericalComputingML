/**
 * 
 */
package dummy;


import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.mathUtils.Function.RootFunctions;
import pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder.SecantRootFinder;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;

/**
 * @author Manuel
 *
 */
public class Starter {
	public static String getHello(){
		return "Hello";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//Start Logger
			//Insert Header
			
			String[][] dataHeader = new String[][]{{"n","5"}, {"xm","20"}, {"f(xm)","20"}, {"em","20"}};
			DataAccumulator.feedAccumulator(dataHeader);
			
			//End Logger
			
                        /*
			BissectionRootFinder brf = new BissectionRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, 0, 2);
			brf.findRoot();
			*/
			
			SecantRootFinder srf = new SecantRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, 0, 2, 0, 2);
			srf.findRoot();
			/**/
			/*
			NewtonRootFinder nrf = new NewtonRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, 0, 2, 1);
			nrf.findRoot();
			*/
			/*
			FixedPointRootFinder fprf = new FixedPointRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, new OneVariableFunction(){
				public double value(double x){
					return (x + 2 / x) * 0.5 ;
				}
			}, 2);
			fprf.findRoot();
			*/
			/*
			RegulaFalsiRootFinder rf = new RegulaFalsiRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, 0, 2);
			rf.findRoot();
			*/
			/*
			ImprovedRegulaFalsiRootFinder irf = new ImprovedRegulaFalsiRootFinder(RootFunctions.function("x^2 - 2"), 50, 5E-6, 0, 2);
			irf.findRoot();
			*/
		}catch(InvalidIntervalException ex){
			System.out.print("Intervalo inválido");
		}catch(IterationCountExceededException ex){
			System.out.print("O algorimto falhou!");
		}
	}

}

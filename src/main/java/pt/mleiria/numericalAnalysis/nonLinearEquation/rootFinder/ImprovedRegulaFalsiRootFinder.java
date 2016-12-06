/**
 * 
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;


import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author Manuel
 * 
 */
public class ImprovedRegulaFalsiRootFinder extends RegulaFalsiRootFinder {
	
	protected double positionF;
	protected double positionG;

	/**
	 * @param f
	 * 			a funcao a encontrar o zero
	 * @param maxIters o numero maximo de iteraccoes admitidas
	 * @param comparisonTollerance limite de erro admissivel. A iteraccao para quando o valor
	 *            encontrado for <= comparisonTollerance
	 * @param a coordenada x do limite inferior do intervalo
	 * @param b coordenada x do limite superior do intervalo
	 * @throws InvalidIntervalException
	 */
	public ImprovedRegulaFalsiRootFinder(OneVarFunction f, int maxIters,
			double comparisonTollerance, double a, double b)
			throws InvalidIntervalException {
		super(f, maxIters, comparisonTollerance, a, b);
		//Inicializacao
		this.positionF = f.value(a);
		this.positionG = f.value(b);
	}
	/**
	 * @param f
	 *            a funcao a encontrar o zero
	 * @param maxIters o numero maximo de iteraccoes permitidas
	 * @param a
	 *            coordenada x do limite inferior do intervalo
	 * @param b
	 *            coordenada x do limite superior do intervalo
	 * @throws InvalidIntervalException
	 */	
	public ImprovedRegulaFalsiRootFinder(OneVarFunction f, int maxIters, double a, double b)
	throws InvalidIntervalException {
		super(f, maxIters, a, b);
		//Inicializacao
		this.positionF = f.value(a);
		this.positionG = f.value(b);
	}
	/**
	 * Computa a posicao seguinte de x
	 */
	public void computeNextPosition() {

		xm1 = bm - positionG * ((bm - am) / (positionG - positionF));
	}
	/**
	 * O intervalo [a, b] onde se quer encontrar o erro
	 * 
	 * @throws IterationCountExceededException
	 */
	public void findRoot() throws IterationCountExceededException {
		// Start Logger
		String[][] data = new String[][] {
				{ String.valueOf(getIterationCount()), "5" },
				{ Utils.formataNumero(xm1, "##0.00000000"), "20" },
				{
						Utils.formataNumero(f.value(xm1),
								"##0.00000000"), "20" },
				{
						Utils.formataNumero(1.41421356 - xm1,
								"##0.00000000"), "20" } };
		DataAccumulator.feedAccumulator(data);

		Utils.print(String.valueOf(getIterationCount()), 5);
		Utils.print(Utils.formataNumero(xm1,
				"##0.00000000"), 20);
		Utils.print(Utils.formataNumero(f
				.value(xm1), "##0.00000000"), 20);
		Utils.print(Utils.formataNumero(
				1.41421356 - xm1, "##0.00000000"), 20);
		Utils.println();
		// End Logger

		checkIterationCount();
		computeNextPosition();
		n++;
		if (hasConverged()) {
			// Start Logger
			data = new String[][] {
					{ String.valueOf(getIterationCount()), "5" },
					{ Utils.formataNumero(xm1, "##0.00000000"),
							"20" },
					{
							Utils.formataNumero(f.value(xm1),
									"##0.00000000"), "20" },
					{
							Utils.formataNumero(1.41421356 - xm1,
									"##0.00000000"), "20" } };
			DataAccumulator.feedAccumulator(data);
			Utils.clearFile("ImprovedRegulaFalsi.txt");
			Utils.appendToFile("ImprovedRegulaFalsi.txt", true,
					DataAccumulator.getData());
			DataAccumulator.reset();
			// End Logger
			reset();
		} else {
			if (f.value(xm1) * f.value(am) < 0.0) {
				bm = xm1;
				positionG = f.value(xm1);
				if(f.value(xm) * f.value(xm1) > 0)
					positionF /= 2;
			} else {
				am = xm1;
				positionF = f.value(xm1);
				if(f.value(xm) * f.value(xm1) > 0)
					positionG /= 2;
			}
			xm = xm1;
			findRoot();
		}
	}

}

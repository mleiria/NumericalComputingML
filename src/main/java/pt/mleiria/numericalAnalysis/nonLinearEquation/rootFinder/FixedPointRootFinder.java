/**
 * 
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;


import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import pt.mleiria.numericalAnalysis.utils.Epsilon;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author Manuel
 * 
 */
public class FixedPointRootFinder extends AbstractRootFinder {

	private double xm, xm1;

	private OneVarFunction iteratorFunction;

	public FixedPointRootFinder(OneVarFunction f, int maxIters,
			double comparisonTollerance, OneVarFunction iteratorFunction,
			double x_0) {
		super(f, maxIters, comparisonTollerance);
		// Inicializacao
		this.iteratorFunction = iteratorFunction;
		this.xm = x_0;
	}

	public FixedPointRootFinder(OneVarFunction f, int maxIters,
			OneVarFunction iteratorFunction, double x_0) {
		super(f, maxIters, Epsilon.doubleValue());
		// Inicializacao
		this.iteratorFunction = iteratorFunction;
		this.xm = x_0;
	}

	/* (non-Javadoc)
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#computeNextPosition()
	 */
	@Override
	public void computeNextPosition() {
		xm1 = iteratorFunction.value(xm);
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#findRoot()
	 */
	@Override
	public void findRoot() throws IterationCountExceededException {
//		 Start Logger
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
			Utils.clearFile("FixedPoint.txt");
			Utils.appendToFile("FixedPoint.txt", true,
					DataAccumulator.getData());
			DataAccumulator.reset();
			// End Logger
			reset();
		} else {
			xm = xm1;
			findRoot();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#hasConverged()
	 */
	@Override
	public boolean hasConverged() {
		if ((Math.abs(xm1 - xm) <= comparisonTollerance)
				|| (f.value(xm1) <= comparisonTollerance && f.value(xm1) >= -comparisonTollerance)) {
			return true;
		} else {
			return false;
		}
	}

}

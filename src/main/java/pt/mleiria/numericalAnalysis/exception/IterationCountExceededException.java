/**
 * 
 */
package pt.mleiria.numericalAnalysis.exception;

/**
 * @author nb026
 *
 */
public class IterationCountExceededException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * IllegalDimension constructor comment.
	 */
	public IterationCountExceededException() {
		super();
	}

	/**
	 * IllegalDimension constructor comment.
	 * 
	 * @param s
	 *            java.lang.String
	 */
	public IterationCountExceededException(String s) {
		super(s);
	}
}

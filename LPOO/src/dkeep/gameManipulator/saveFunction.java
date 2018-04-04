package dkeep.gameManipulator;

/**
 * Interface to pass a function as an argument.
 *
 */
public interface saveFunction {
	/**
	 * Interface to save filename to the respective array.
	 * @param a Index in the array.
	 * @param fileName Filename to introduce into the array.
	 */
	public void save (Integer a, String fileName);
}
package timer;

import java.util.Iterator;

/**
 * Permit to create an Timer iterator
 */
public interface Timer extends Iterator<Integer>{
	/**
	 * Return the delay time
	 * @see java.util.Iterator#next()
	 */
	public Integer next();
}

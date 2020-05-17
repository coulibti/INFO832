package timer;

/**
 * Create a periodic timer
 */
public class PeriodicTimer implements Timer {

	/**
	 * The period of the timer
	 */
	private int period;
	/**
	 * The date of the next tick
	 */
	private int next;
	/**
	 * The fluctuation in the period
	 * (null = no fluctuation)
	 */
	private RandomTimer moreOrLess = null;
	
	/**
	 * Constructor : permit to fluctuate the period with a random value
	 * @param at int A date equals to the period
	 */
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}
	
	/**
	 * Constructor : permit to fluctuate the period with a random value
	 * @param at int A date equals to the period
	 * @param moreOrLess The fluctuation
	 * 
	 */

	
	/**
	 * Constructor
	 * @param period int The period
	 * @param at int A date
	 */
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}
	
	/**
	 * Constructor : permit to fluctuate the period with a random value
	 * @param period int The period
	 * @param at int A date
	 * @param moreOrLess The fluctuation
	 * 
	 */
	
	/**
	 * Getter of the period of this timer
	 * @return int The period of this timer
	 */
	public int getPeriod() {
		return this.period;
	}
	
	/**
	 * Iterator : Get the next step
	 * @return int The index of the next step
	 */
	@Override
	public Integer next() {
		

		if(this.moreOrLess != null) {
			this.next = this.period + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
		}else {
			this.next = this.period;
		}
		
		return next;
	}

	/**
	 * Iterator : Determine if there is a next step
	 * @return boolean Always True (it is a periodic timer)
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

}

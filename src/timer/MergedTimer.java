package timer;

/**
 * Permit to merge two timers
 */
public class MergedTimer implements Timer{
	
	/**
	 * The first merged time
	 */
	private Timer timer1;
	/**
	 * The second merged time
	 */
	private Timer timer2;

	/**
	 * Constructor
	 * @param timer1 Timer The first merged time
	 * @param timer2 Timer The second merged time
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Iterator : Determine if there is a next step
	 * @return boolean True = There is a next step
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}
	/**
	 * Iterator : Get the next step
	 * @return int The index of the next step
	 */
	@Override
	public Integer next() {
		if(this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}

}

package timer;

/*
 * Timer that can be used only once
 */
public class OneShotTimer  implements Timer {
	
	/**
	 * Date when the timer will be used
	 */
	private Integer at;
	/**
	 * Iterator : hasNext
	 * @see Timer
	 */
	private boolean hasNext;
	
	/**
	 * Constructor
	 * @param at int Date when the timer will be used
	 */
	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = true;
	}

	/**
	 * Iterator : Determine if there is a next step
	 * @return boolean True = There is a next step
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}
	/**
	 * Iterator : Get the next step
	 * @return int The index of the next step
	 */
	@Override
	public Integer next() {
		Integer next=this.at;
		this.at=null;
		this.hasNext = false;
		return next;
	}

}

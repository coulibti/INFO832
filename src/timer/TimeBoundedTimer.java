package timer;

/**
 * Timer that is bounded to an other timer
 */
public class TimeBoundedTimer implements Timer {
	/**
	 * The timer dependance
	 */
	private Timer timer2bound;
	/**
	 * When this timer starts
	 */
	private Integer startTime;
	/**
	 * When this timer stops
	 */
	private Integer stopTime;
	

	/**
	 * The time of this timer
	 */
	private int time=0;
	/**
	 * To know if there is a next step
	 */
	private boolean hasNext;

	/**
	 * Constructor
	 * @param timer2bound Timer The timer dependance
	 * @param startTime int When this timer starts
	 * @param stopTime int When this timer stops
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.init();
	}
	/**
	 * Constructor (the stop time is infinite, simulated here with the maximum value of an integer)
	 * @param timer2bound Timer The timer dependance
	 * @param startTime int When this timer starts
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = Integer.MAX_VALUE;
		this.init();
	}
	/**
	 * Constructor (following) : Initialize variables for Iterator
	 */
	private void init() {
		this.cpt = this.timer2bound.next();
		while (this.cpt < this.startTime) {
			this.cpt += this.timer2bound.next();
		}
		if(this.cpt <this.stopTime) {
			this.hasNext = true;
		}else {
			this.hasNext = false;
		}
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
		this.time+=this.cpt;
		if(this.time < this.stopTime) {
			this.cpt = this.timer2bound.next();
		}else {
			next = null;
			this.hasNext=false;
		}
		return next;
	}

}

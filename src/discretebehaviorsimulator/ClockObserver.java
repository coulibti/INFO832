package discretebehaviorsimulator;

/**
 * Objects who have this interface can observe the Clock singleton
 */
public interface ClockObserver {
	/**
	 * Method executed when time of Clock singleton change
	 * @param time int The new time
	 */
	public void clockChange(int time);
	/**
	 * Method executed when nextJump of Clock singleton change
	 * @param time int The new spent time at each step
	 */
	public void nextClockChange(int nextJump);
}

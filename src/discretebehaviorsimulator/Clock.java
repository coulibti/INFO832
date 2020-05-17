package discretebehaviorsimulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Manage time
 * (It is a Singleton)
 * @see ClockObserver
 */
public class Clock {
	/**
	 * The instance of the Singleton
	 */
	private static Clock instance = null;
	
	/**
	 * The time
	 */
	private int time;
	/**
	 * Spent time at each step
	 */
	private int nextJump;
	/**
	 * Determine if Clock can be changed by environment or not, like a semaphore
	 */
	private ReentrantReadWriteLock lock;
	/**
	 * Determine if this Clock is virtual or referenced
	 */
	private boolean virtual;
	
	/**
	 * List of observers of Clock events
	 */
	private Set<ClockObserver> observers;
	
	/**
	 * Initialize the Singleton
	 * @return Clock the instance
	 */
	private Clock() {
		this.time = 0;
		this.nextJump=0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<ClockObserver>(<>);
	}
	
	/**
	 * Get the Singleton
	 * @return Clock the instance
	 */
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	/**
	 * Add an observer of Clock events
	 * @param o ClockObserver The object that will observer Clock
	 */
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}
	/**
	 * Remove an observer of Clock events
	 * @param o ClockObserver The object that is not interested by Clock
	 */
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	/**
	 * Setter of this.virtual
	 * @param virtual boolean The new value to put
	 */
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	/**
	 * Getter of this.virtual
	 * @return boolean Value of this.virtual
	 */
	public boolean isVirtual() {
		return this.virtual;
	}
	
	/**
	 * Setter of this.nextJump
	 * (This method send an event to observers)
	 * @param nextJump int The new value to put
	 */
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}
	/**
	 * Add time to this.time
	 * (This method use a semaphore)
	 * (This method send an event to observers)
	 * @param time int The time to add at this.time
	 */
	public void increase(int time)  {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new Exception("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}
	/**
	 * Getter of the time
	 * (this.time if it's virtual, else real date)
	 * @return long The current time
	 */
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	/**
	 * Semaphore with this.lock :
	 * Permit to lock read access
	 */
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	/**
	 * Semaphore with this.lock :
	 * Permit to unlock read access
	 */
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	/**
	 * Semaphore with this.lock :
	 * Permit to lock write access
	 */
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}
	/**
	 * Semaphore with this.lock :
	 * Permit to unlock write access
	 */
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	/**
	 * Get the class in String type
	 * @return String this.time
	 */
	public String toString() {
		return ""+this.time;
	}
}

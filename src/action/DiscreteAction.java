package action;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import timer.Timer;

/**
 * DiscreteAction
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 * @see DiscreteActionDependant
 * @see DiscreteActionInterface
 * @see DiscreteActionOnOffDependant
 */
public class DiscreteAction implements DiscreteActionInterface {
	/**
	 * Object that will realize the action
	 */
	private Object object;
	/**
	 * Associated method to the action
	 */
	private Method method;
	
	/**
	 * Timer provides new lapsTime
	 */
	private Timer timmer;
	/*
	 * Obsolete, managed in timer
	 */
	/**
	 * Obsolete, managed in timer
	 */
	/**
	 * Waiting time, Null if never used
	 */
	private Integer lapsTime;
	
	/**
	 * Log gestion
	 */
	private Logger logger;

	/**
	 * Generate logger for the DiscreteAction
	 * @return DiscreteAction Instanced DiscreteAction
	 */
	private DiscreteAction() {
		// Start logger
			this.logger = Logger.getLogger("DAS");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);

	}
	
	/**
	 * Constructor
	 * @param o Object Object that will realize the action
	 * @param m String Name of a method of "o" parameter
	 * @param timmer Timer Timer
	 * @return DiscreteAction Instanced DiscreteAction
	 */
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		catch(Exception e){
			LOGGER.log("context", e);
		}
		this.timmer = timmer;
	}
	
	// ATTRIBUTION
	/**
	 * Consider spent time
	 * @param t int Time spent
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
	}

	// RECUPERATION
	/**
	 * Get the associated method of this action
	 * @return Method method of this intanced DiscreteAction
	 */
	public Method getMethod(){
		return method;
	}
	/**
	 * Get the current lasp time of this action
	 * @return Integer lasp time of this intanced DiscreteAction
	 */
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}
	/**
	 * Get the associated object of an action
	 * @return Object object of this intanced DiscreteAction
	 */
	public Object getObject(){
		return object;
	}



	// COMPARAISON
	/**
	 * Compare the laps time between two DiscreteAction
	 * @param c DiscreteActionInterface The DiscreteAction to compare
	 * @return int 0=Identic lasp time, 1=this>c, -1=c>this
	 */
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) { // no lapstime is equivalent to infinity 
			return 1;
		}
		if (c.getCurrentLapsTime() == null) {// no lapstime is equivalent to infinity 
			return -1;
		}
    	if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}
    	if(this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
    	}
		if(this.lapsTime == c.getCurrentLapsTime()){
			return 0;
		}
		return 0;
	}

	/**
	 * Transform DiscreteAction en String
	 * @return String Information about DiscreteAction
	 */
	public String toString(){
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timmer + "\n delay: " + this.lapsTime;

	}

	/**
	 * Put DiscreteAction at next action
	 * @return DiscreteActionInterface The same DiscreteAction at next step
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timmer.next();
		this.logger.log(Level.FINE, "[DA] operate next on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		return this;
	}

	/**
	 * Determine if there is a next step for this DiscreteAction
	 * @return boolean True=There is a next step
	 */
	public boolean hasNext() {
		Boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		return more;		
	}
	

}

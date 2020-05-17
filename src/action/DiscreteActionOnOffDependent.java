0package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

import timer.DateTimer;
import timer.Timer;

/**
 * @author flver
 * @see DiscreteAction
 * @see DiscreteActionInterface
 * @see DiscreteActionDependant
 */
public class DiscreteActionOnOffDependent implements DiscreteActionInterface {
	
	/**
	 * Action when the period starts
	 */
	protected DiscreteActionInterface onAction;
	/**
	 * Action when the period ends
	 */
	protected DiscreteActionInterface offAction;
	/**
	 * The lasp time
	 */
	protected DiscreteActionInterface currentAction;
	
	/**
	 * The lasp time
	 */
	private Integer currentLapsTime;
	/**
	 * Limit of a delay (default=0 : no limit)
	 */
	private Integer lastOffDelay=0;
	
	/**
	 * Construct an On Off dependence, first action (method) called is On, then method nextMethod() is called to select the next action.
	 * The default behavior of nextMethod() is to switch between On and Off actions.  It can be change by overloading. 
	 * 
	 * @param o Object Object thqt reqliwe the qction
	 * @param on Method Method to execute when the action starts
	 * @param timerOn Timer Timer for starting action
	 * @param offon Method Method to execute when the action ends
	 * @param timerOff Timer Timer for ending action
	 */
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}
	/**
	 * Transform dates to timelapse
	 * @param datesOn TreeSet<Integer>
	 * @param datesOff TreeSet<Integer>
	 * @param timeLaspeOn Vector<Integer>
	 * @param timeLapseOff Vector<Integer>
	 **/
	private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
		Vector<Integer> currentTimeLapse;
		TreeSet<Integer> currentDates;
		Integer date=0;
		if(datesOn.first()<datesOff.first()) {
			currentTimeLapse = timeLapseOn;
			currentDates = datesOn;
		}else {
			currentTimeLapse = timeLapseOff;	
			currentDates = datesOff;		
		}
		Integer nextDate;
		
		while(datesOn.size()>0 || datesOff.size()>0) {
			nextDate = currentDates.first();
		
			currentTimeLapse.add(nextDate - date);
			currentDates.remove(nextDate);
		
			date = nextDate;
			
			if(currentDates == datesOn) {
				currentDates = datesOff;
				currentTimeLapse = timeLapseOff;
			}else {
				currentDates = datesOn;
				currentTimeLapse = timeLapseOn;			
			}
		}
		
	}
	/**
	 * Create a dependant action on a period
	 * @param o Object Concerned object
	 * @param on String Method name of o, to execute when the period begin
	 * @param datesOn Vector<Integer> Dates when the action is put on
	 * @param off String Method name of o, to execute when the period finish
	 * @param datesOff Vector<Integer> Dates when the action is put off
	 **/
	public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));
		
		
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
	/**
	 * Change the current action to the next one
	 */
	public void nextAction(){
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
			this.currentAction = this.currentAction.next();
			this.lastOffDelay = this.currentAction.getCurrentLapsTime();
		}else{
			this.currentAction = this.onAction;
			this.currentAction = this.currentAction.next();
			this.currentAction.spendTime(this.lastOffDelay);
		}
	}
	/**
	 * Apply spent time on the current action
	 * @param t int Time to spend
	 */
	public void spendTime(int t) {
		this.currentAction.spendTime(t);
	}
	/**
	 * Get the associated method of the current action
	 * @return Method method of the current action
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}
	/**
	 * Get the current lasp time of the current action
	 * @return Integer lasp time of the current action
	 */
	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}
	/**
	 * Get the associated object of the current action
	 * @return Object object of the current action
	 */
	public Object getObject() {
		return this.currentAction.getObject();
	}
	/**
	 * Compare the laps time between the current action and an other one
	 * @param c DiscreteActionInterface The action to compare
	 * @return int 0=Identic lasp time, 1=this>c, -1=c>this
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}
	/**
	 * Put DiscreteAction at next action
	 * @return DiscreteActionInterface The same DiscreteAction at next step
	 */
	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}
	/**
	 * Determine if there is a next step for this DiscreteAction
	 * @return boolean True=There is a next step
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}
}

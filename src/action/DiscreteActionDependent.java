
package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * @author flver
 * @see DiscreteAction
 * @see DiscreteActionInterface
 * @see DiscreteActionOnOffDependant
 */
public class DiscreteActionDependent implements DiscreteActionInterface {
	
	/**
	 * The reference of actions
	 */
	protected DiscreteAction baseAction;
	/**
	 * Dependant actions of baseAction
	 */
	protected TreeSet<DiscreteAction> depedentActions;
	/**
	 * (This object can be iterable = can be used with foreach keyword)
	 */
	private Iterator<DiscreteAction> it;
	/**
	 * The current action (linked with it)
	 */
	protected DiscreteAction currentAction;
	
	
	/**
	 * Construct a series of dependent actions, first action (method) called is baseMethodName, then method nextMethod() is called to select the next action. 
	 * 
	 * @param o Object Object that contains the method
	 * @param baseMethodName String Name of the method
	 * @param timerBase Timer Timer of the action
	 */	
	public DiscreteActionDependent(Object o, String baseMethodName, Timer timerBase){
		this.baseAction = new DiscreteAction(o, baseMethodName, timerBase);
		this.depedentActions = new TreeSet<DiscreteAction>();
		this.it = this.depedentActions.iterator();
		this.currentAction = this.baseAction;
	}
	
	/**
	 * Add a dependant action
	 * @param o Object Object that contains the method
	 * @param depentMethodName String Name of the method
	 * @param timerDependance Timer Timer of the action
	 */
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
	}
	

	/**
	 * Create a dependant action on a period
	 * @param o Wo Concerned object
	 * @param on String Method name of o, to execute when the period begin
	 * @param datesOn Vector<Integer> Dates when the action is put on
	 * @param off String Method name of o, to execute when the period finish
	 * @param datesOff Vector<Integer> Dates when the action is put off
	 **/
	@SuppressWarnings("unchecked")
	public DiscreteActionDependent(Wo o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		
		this.baseAction = new DiscreteAction(o, on, timeLapseOn);
		this.offAction = new DiscreteAction(o, off, timeLapseOff);
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.baseAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
*/

	/**
	 * Reinitalize timeStamps of dependant actions
	 * @deprecated
	 */
	private void reInit() {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		}
	}
	
	/**
	 * Change the method of the action to the next one
	 */
	public void nextMethod(){
		if (this.currentAction == this.baseAction){
			this.it = this.depedentActions.iterator();
			this.currentAction = this.it.next();
		}else if(this.currentAction == this.depedentActions.last()){
			this.currentAction = this.baseAction;
			this.reInit();
		}else {
			this.currentAction = this.it.next();
		}
	}
	
	/**
	 * Spend time on each dependant action
	 * @param t int Time to spend
	 */
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
	}

	/**
	 * Update time laps
	 * @deprecated
	 */
	public void updateTimeLaps() {
		this.nextMethod();	
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
	 * Determine if the list of actions is empty
	 * @return boolean True=There is none
	 */
	public Boolean isEmpty() {
		return !this.hasNext();
	}
	/**
	 * Put DiscreteAction at next action
	 * @return DiscreteActionInterface The same DiscreteAction at next step (itself here)
	 */
	public DiscreteActionInterface next() {
		Method method = this.getMethod();
		Object object = this.getObject();
		return this;
	}
	/**
	 * Determine if there is a next step for this DiscreteAction
	 * @return boolean True=There is a next step
	 */
	public boolean hasNext() {
		return this.baseAction.hasNext() || !this.depedentActions.isEmpty();		
	}

}

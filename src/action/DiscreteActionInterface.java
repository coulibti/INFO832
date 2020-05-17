package action;

import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * @see DiscreteAction
 * @see DiscreteActionDependant
 * @see DiscreteActionOnOffDependant
 */
public interface DiscreteActionInterface extends Comparable<DiscreteActionInterface>, Iterator<DiscreteActionInterface>{

	/**
	 * Spend time of action
	 * @param t int Time to spend
	 */
	public void spendTime(int t);
	/**
	 * Get the associated method of the action
	 * @return Method method of the action
	 */
	public Method getMethod();
	/**
	 * Get the current lasp time of the action
	 * @return Integer lasp time of the action
	 */
	public Integer getCurrentLapsTime();
	/**
	 * Get the associated object of the action
	 * @return Object object of the action
	 */
	public Object getObject();

	// COMPARAISON
	/**
	 * Compare the laps time between the current action and an other one
	 * @param c DiscreteActionInterface The action to compare
	 * @return int 0=Identic lasp time, 1=this>c, -1=c>this
	 */
	public int compareTo(DiscreteActionInterface c);
	/**
	 * Itaration management
	 * @return DiscreteActionInterface The same DiscreteAction at next step
	 */
	public DiscreteActionInterface next();

}

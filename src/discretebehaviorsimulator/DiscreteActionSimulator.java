
package discretebehaviorsimulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteAction;
import action.DiscreteActionInterface;


/**
 * Main script that can be excuted
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 */
public class DiscreteActionSimulator implements Runnable {
	private string context="context";

	/**
	 * The thread where this object is running
	 */
	private Thread t;
	/**
	 * To know if the script is running on this thread
	 */
	private boolean running = false;
	
	/**
	 * The singleton Clock
	 */
	private Clock globalTime;
	
	/**
	 * List of created actions
	 */
	private ArrayList<DiscreteActionInterface> actionsList = new ArrayList<>();
	
	/**
	 * Number of iterations for the simulation
	 */
	private int nbLoop;
	/**
	 * Increment to do at each iteration
	 * (-1 = no limit)
	 */
	private int step;
	
	/**
	 * The logger
	 */
	private Logger logger;
	/**
	 * The file where logs will be stored
	 */
	private FileHandler logFile;
	/**
	 * The console where logs will be displayed
	 */
	private ConsoleHandler logConsole; 

	/**
	 * Manage the logger
	 * @return DiscreteActionSimulator Instanced DiscreteActionSimulator with a logger
	 */
	public DiscreteActionSimulator(){
		
		// Start logger
		this.logger = Logger.getLogger("DAS");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");
			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		}catch(Exception e){
			LOGGER.log(context, e);
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);
		

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	
	/**
	 * Setter of this.nbLoop
	 * @param nbLoop int The number of loop for the simulation (<1 = infinite)
	 */
	public void setNbLoop(int nbLoop){
		if(nbLoop>0){
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}
	
	/**
	 * Add an action in simulation
	 * @param c DiscreteActionInterface The action to add
	 */
	public void addAction(DiscreteActionInterface c){

		if(c.hasNext()) {
			// add to list of actions, next is call to the action exist at the first time
			this.actionsList.add(c.next());

			// sort the list for ordered execution 
			Collections.sort(this.actionsList);
		}
	}


	/**
	 * Get the current laps time
	 * @return int The laps time of the first action in the list
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}
	/**
	 * Run an action
	 * (This method is threaded)
	 * @return int laps time of the running action
	 */
	private int runAction(){
		String act = "[DAS] run action ";
		String act2 = "after ";
		String act3 = " time units\n";

		// Run the first action
		int sleepTime = 0;



		DiscreteActionInterface currentAction = this.actionsList.get(0);
		Object o = currentAction.getObject();
		Method m = currentAction.getMethod();
		sleepTime = currentAction.getCurrentLapsTime();
		
		try {
			//Thread.sleep(sleepTime); // Real time can be very slow
			Thread.yield();
			//Thread.sleep(1000); // Wait message bus sends
			if(this.globalTime!=null) {
				this.globalTime.increase(sleepTime);
			}
			m.invoke(o);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, act + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + act2 + sleepTime + act3);
				logger.log(act+ m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + act2+ sleepTime + act3);
			}else {
				this.logger.log(java.util.logging.Level.FINE, act + m.getName() , " on " + o.getClass().getName() , ":" + o.hashCode() + act2+ sleepTime + act3);
				logger.log(act + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + act2 + sleepTime + act3);
			
			}
			
		}catch (Exception e) {
			LOGGER.log(context, e);
		}

		return sleepTime;
	}

	/**
	 * Setter of laps times in actions
	 * @param runningTimeOf1stCapsul int The time to spend
	 */
	private void updateTimes(int runningTimeOf1stCapsul){
		String act4 = "[DAS] reset action ";
		
		// update time laps off all actions
		for(int i=1 ; i < this.actionsList.size(); i++){

			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}


		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.addElement(a);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, act4 + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + ACT3);
				logger.log(act4 + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + ACT3);
			}else {
				this.logger.log(Level.FINE, act4 + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + ACT3);
				logger.log(act4 + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + ACT3);
			}
			Collections.sort(this.actionsList);
		}
	}

	/**
	 * Launch the simulation
	 */
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;

		logger.log("LANCEMENT DU THREAD " + t.getName() + " \n");

		while(running && !finished){

			if(!this.actionsList.isEmpty()){
				logger.log(this);
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				}catch(Exception e) {
					LOGGER.log(context, e);
				}
			}else{
				logger.log("NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			logger.log("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		}else {
			logger.log("DAS: " + (count) + " actions done!");
		}
	}

	/**
	 * Start the thread
	 */
	public void start(){
		this.running = true;
		this.t.start();
	}
	/**
	 * Stop the thread
	 */
	public void stop(){
		logger.log("STOP THREAD " + t.getName() + "obj " + this);
		this.running = false;
	}
	
	/**
	 * Transform this object to String
	 * @return String The global situation of this object
	 */
	public String toString(){
		StringBuilder toS = new StringBuilder("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	/**
	 * Getter of this.running
	 * @return boolean True = The thread is running
	 */
	public boolean getRunning() {
		return this.running;
	}

}

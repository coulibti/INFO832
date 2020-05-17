package timer;

import java.util.Random;

/**
 * Create a random timer
 * @author Flavien Vernier
 */
public class RandomTimer implements Timer {
	
	/**
	 * Possibilities of methods to make the random value
	 */
	public enum randomDistribution {
		POISSON, EXP, POSIBILIST, GAUSSIAN;
	}

	/**
	 * The random motor
	 */
	private Random r = new Random();
	/**
	 * The random distribution from randomDistribution
	 */
	private randomDistribution distribution;
	/**
	 * The rate of the random model
	 */
	private double rate;
	/**
	 * The mean of the random model
	 */
	private double mean;
	/**
	 * The low limit of the random model
	 */
	private double lolim;
	/**
	 * The high limit of the random model
	 */
	private double hilim;

	/**
	 * Get the distribution model according to his name
	 * @param distributionName String The name of the distribution model
	 * @return The distribution model
	 */
	public static randomDistribution string2Distribution(String distributionName){
		return RandomTimer.randomDistribution.valueOf(RandomTimer.randomDistribution.class, distributionName.toUpperCase());
	}
	/**
	 * Get the distribution name according to his model
	 * @param distribution randomDistribution The distribution model
	 * @return The name of the distribution model
	 */
	public static String distribution2String(randomDistribution distribution){
		return distribution.name();
	}
	
	/**
	 * Constructor
	 * @param distribution randomDistribution Name of the distribution model
	 * @param param double Constraints
	 * @throws Exception Use this method only for exponential & Poisson distribution models
	 */
	public RandomTimer(randomDistribution distribution, double param) throws Exception{
		if(distribution == randomDistribution.EXP ){
			this.distribution = distribution;
			this.rate = param;
			this.mean = 1/param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}else if(distribution == randomDistribution.POISSON){
			this.distribution = distribution;
			this.rate = Double.NaN;
			this.mean = param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}else{
			throw new Exception("Bad Timer constructor for selected distribution");
		}
	}
	/**
	 * Constructor
	 * @param distribution randomDistribution Name of the distribution model
	 * @param lolim int The minimum constraint
	 * @param hilim int The maximum constraint
	 * @throws Exception Use this method only for gaussian & posibilist distribution models
	 */
	public RandomTimer(randomDistribution distribution, int lolim, int hilim) throws Exception{
		if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			this.distribution = distribution;
			this.mean = double(lolim) + (hilim - lolim)/2;
			this.rate = Double.NaN;
			this.lolim = lolim;
			this.hilim = hilim;
		}else{
			throw new Exception("Bad Timer constructor for selected distribution");
		}
	}
	
	/**
	 * Getter of the distribution model name
	 * @return String The name of the distribution model
	 **/
	public String getDistribution(){
		return this.distribution.name();
	}
	/**
	 * Getter of the parameters of the distribution model
	 * @return String The parameters of the distribution model
	 **/
	public String getDistributionParam() {
		if(distribution == randomDistribution.EXP ){
			return "rate: " + this.rate;
		}else if(distribution == randomDistribution.POISSON){
			return "mean: " + this.mean;
		}else if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			return "lolim: " + this.lolim + " hilim: " + this.hilim;
		}
		
		return "null";
	}
	
	/**
	 * Getter of this.mean
	 * @return double The mean of the random timer
	 **/
	public double getMean(){
		return this.mean;
	}
	
	/**
	 * Transform this timer to String
	 * @return String The timer according to the distribution model
	 **/
	public String toString(){
		String s = this.getDistribution();
		switch (this.distribution){
		case POSIBILIST :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		case POISSON :
			s += " mean:" + this.mean;
			break;
		case EXP :
			s += " rate:" + this.rate;
			break;
		case GAUSSIAN :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		}
		
		return s;
	}

	/**
	 * Iterator : Get the next step
	 * @return Interger The index of the next step according to the distribution model (-1 = Wrong distribution model)
	 * @see methodInvocator.RandomTimer#next(int)
	 */
	@Override
	public Integer next(){
		switch (this.distribution){
		case POSIBILIST :
			return this.nextTimePosibilist();
		case POISSON :
			return this.nextTimePoisson();
		case EXP :
			return this.nextTimeExp();
		case GAUSSIAN :
			return this.nextTimeGaussian();
		}
		return -1; // Theoretically impossible !!!
	}
	
	/**
	 * Iterator : Get the next step
	 * Equivalent to methodInvocator.RandomTimer#next()
	 * @param since int Time reference (has no effect)
	 * @return Interger The index of the next step
	 * @see methodInvocator.RandomTimer#next(int)
	 **/

	/**
	 * Iterator : Get the next step in probabilist model
	 * (Give good mean but wrong variance)
	 * @return int The index of the next step
	 */
	private int nextTimePosibilist(){
	    return (int)this.lolim + (int)(this.r.nextInt() * (this.hilim - this.lolim));
	}
	
	/**
	 * Iterator : Get the next step in exponential model
	 * (Give good mean but wrong variance)
	 * @return int The index of the next step
	 */
	private int nextTimeExp(){
	    return (int)(-Math.log(1.0 - this.r.nextInt()) / this.rate);
	}
	
	/**
	 * Iterator : Get the next step in Poisson model
	 * (Give good mean and variance)
	 * @return int The index of the next step
	 */
	private int nextTimePoisson() {
	    
	    double l = Math.exp(-this.mean);
	    int k = 0;
	    double p = 1.0;
	    do {
	        p = p * this.r.nextDouble();
	        k++;
	    } while (p > l);
	    return k - 1;
	}   		
	    
	/**
	 * Iterator : Get the next step in Gaussian model
	 * @return int The index of the next step
	 */
	private int nextTimeGaussian(){
		return (int)this.lolim + (int)((this.r.nextGaussian() + 1.0)/2.0 * (this.hilim - this.lolim));
	}
	
	/**
	 * Iterator : Determine if there is a next step
	 * @return boolean Always True (it is a random timer)
	 */
	@Override
	public boolean hasNext() {
		return true;
	}
}

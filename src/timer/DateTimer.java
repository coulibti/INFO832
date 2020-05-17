package timer;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class DateTimer  implements Timer {
	
	/**
	 * list of lasp times
	 */
	ArrayList lapsTimes<>;
	/**
	 * The iterator
	 * @see Timer
	 */
	Iterator<Integer> it;
	
	/**
	 * Constructor
	 * @param dates TreeSet<Integer> The dates of events (lasp times are between each dates)
	 */
	public DateTimer(SortedSet<Integer> dates) {
		this.lapsTimes = new ArrayList<>();
		Integer last;
		Integer current=0;
		
		Iterator<Integer> itr = dates.iterator();
		while (itr.hasNext()) {
			last = current;
			current = itr.next();
			this.lapsTimes.add(current-last);
		}
		this.it = this.lapsTimes.iterator();

	}
	
	/**
	 * Constructor
	 * @param laspTimes Vector<Integer> The lasp times
	 */
	public DateTimer(ArrayList<> lapsTimes) {
		this.lapsTimes = new ArrayList<>(lapsTimes);
		this.it = this.lapsTimes.iterator();
	}

	/**
	 * Iterator : Determine if there is a next step
	 * @return boolean True = There is a next step
	 */
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}
	/**
	 * Iterator : Get the next step
	 * @return int The index of the next step
	 */
	@Override
	public Integer next() {
		return it.next();
	}

}

package timer_test;
import timer.DateTimer;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeSet;
import org.junit.Test;

public class DateTimerTest {
    

    @Test
    public void testDateTimer() {
        TreeSet<Integer> d = new TreeSet<Integer>();
        DateTimer dt = new DateTimer(d);
        assertEquals(dt.it, dt.lapsTimes.iterator());
        assertNotNull(dt.lapsTimes);
    }

    @Test
    public void testHasNext() {
        TreeSet<Integer> d = new TreeSet<Integer>();
        DateTimer dt = new DateTimer(d);
        assertNotNull(dt.hasNext());
    }

    @Test
    public void testNext() {
        TreeSet<Integer> d = new TreeSet<Integer>();
        DateTimer dt = new DateTimer(d);
        assertNotNull(dt.next());
    }

}
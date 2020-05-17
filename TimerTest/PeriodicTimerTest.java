package timer_test;
import timer.PeriodicTimer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class PeriodicTimerTest {

    @Test
    public void testGetPeriod() {
        int at = 1;
        PeriodicTimer pt = new PeriodicTimer(at);
        assertEquals(pt.period, pt.getPeriod());
    }

    @Test
    public void testNext() {
        int at = 1;
        PeriodicTimer pt = new PeriodicTimer(at);
        assertNotNull(pt.next());
    }

    @Test
    public void testHasNext() {
        int at = 1;
        PeriodicTimer pt = new PeriodicTimer(at);
        assertTrue(pt.hasNext());
    }
    
}
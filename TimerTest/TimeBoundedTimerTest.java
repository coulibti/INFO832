package timer_test;
import timer.TimeBoundedTimer;
import timer.Timer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class TimeBoundedTimerTest {

    @Test
    public void testHasNext() {
        Timer timer2bound = new Timer();
        int startTime = 2;
        TimeBoundedTimer t = new TimeBoundedTimer(timer2bound, startTime);
        assertEquals(t.hasNext, t.hasNext());
    }

    @Test
    public void testNext() {
        Timer timer2bound = new Timer();
        int startTime = 2;
        TimeBoundedTimer t = new TimeBoundedTimer(timer2bound, startTime);

        t.time = 1;
        t.stopTime = 2;
        assertEquals(t.next, t.next());

        t.time = 3;
        assertNull(t.next());
    }
    
}
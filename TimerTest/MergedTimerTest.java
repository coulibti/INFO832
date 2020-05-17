package timer_test;
import timer.MergedTimer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class MergedTimerTest {

    @Test
    public void testHasNext() {
        MergedTimer mt = new MergedTimer();
        if (mt.timer1.hasNext() && mt.timer2.hasNext()) {
            assertTrue(mt.hasNext());
        }
        else {
            assertFalse(mt.hasNext());
        }
    }

    @Test
    public void testNext() {
        MergedTimer mt = new MergedTimer();
        assertEquals(mt.timer1.next() + mt.timer2.next(), mt.next());
    }
    
}
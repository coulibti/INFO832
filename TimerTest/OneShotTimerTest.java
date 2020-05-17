package timer_test;
import timer.OneShotTimer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class OneShotTimerTest {

    @Test
    public void testHasNext() {
        int at = 1;
        OneShotTimer o = new OneShotTimer(at);
        assertTrue(o.hasNext());
    }

    @Test
    public void testNext() {
        int at = 1;
        OneShotTimer o = new OneShotTimer(at);
        assertEquals(o.at, o.next());
    }
    
}
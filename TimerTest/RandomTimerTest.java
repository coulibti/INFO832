package timer_test;
import timer.RandomTimer;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

public class RandomTimerTest {

    @Test
    public void testString2Distribution() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertEquals(distribution, rt.string2Distribution("EXP"));
    }

    @Test
    public void testDistribution2String() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertEquals("EXP", rt.distribution2String(distribution));
    }

    @Test
    public void testGetDistribution() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertEquals("EXP", rt.getDistribution());
    }

    @Test
    public void testGetDistributionParam() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertEquals("rate: " + rt.rate, rt.getDistributionParam());

        RandomTimer.randomDistribution distribution2 = RandomTimer.randomDistribution.POISSON;
        double param2 = 1.234;
        RandomTimer rt2 = new RandomTimer(distribution2, param2);
        assertEquals("mean: " + rt2.mean, rt.getDistributionParam());

        RandomTimer.randomDistribution distribution3 = RandomTimer.randomDistribution.GAUSSIAN;
        double param3 = 1.234;
        RandomTimer rt3 = new RandomTimer(distribution3, param3);
        assertEquals("lolim: " + rt3.lolim + " hilim: " + rt3.hilim, rt3.getDistributionParam());
    }

    @Test
    public void testGetMean() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertEquals(rt.getDistribution()+ "rate:" +rt.rate, rt.getMean());
    }
    
    @Test
    public void testHasNext() {
        RandomTimer.randomDistribution distribution = RandomTimer.randomDistribution.EXP;
        double param = 1.234;
        RandomTimer rt = new RandomTimer(distribution, param);
        assertTrue(rt.hasNext());
    }
}
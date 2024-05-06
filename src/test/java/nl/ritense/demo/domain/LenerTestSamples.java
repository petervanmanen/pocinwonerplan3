package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LenerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Lener getLenerSample1() {
        return new Lener().id(1L).opmerkingen("opmerkingen1");
    }

    public static Lener getLenerSample2() {
        return new Lener().id(2L).opmerkingen("opmerkingen2");
    }

    public static Lener getLenerRandomSampleGenerator() {
        return new Lener().id(longCount.incrementAndGet()).opmerkingen(UUID.randomUUID().toString());
    }
}

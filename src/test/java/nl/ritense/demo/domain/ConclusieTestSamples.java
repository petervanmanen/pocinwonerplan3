package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ConclusieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Conclusie getConclusieSample1() {
        return new Conclusie().id(1L);
    }

    public static Conclusie getConclusieSample2() {
        return new Conclusie().id(2L);
    }

    public static Conclusie getConclusieRandomSampleGenerator() {
        return new Conclusie().id(longCount.incrementAndGet());
    }
}

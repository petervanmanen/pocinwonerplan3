package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class VerlichtingsobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verlichtingsobject getVerlichtingsobjectSample1() {
        return new Verlichtingsobject().id(1L);
    }

    public static Verlichtingsobject getVerlichtingsobjectSample2() {
        return new Verlichtingsobject().id(2L);
    }

    public static Verlichtingsobject getVerlichtingsobjectRandomSampleGenerator() {
        return new Verlichtingsobject().id(longCount.incrementAndGet());
    }
}

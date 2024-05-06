package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwijsloopbaanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwijsloopbaan getOnderwijsloopbaanSample1() {
        return new Onderwijsloopbaan().id(1L);
    }

    public static Onderwijsloopbaan getOnderwijsloopbaanSample2() {
        return new Onderwijsloopbaan().id(2L);
    }

    public static Onderwijsloopbaan getOnderwijsloopbaanRandomSampleGenerator() {
        return new Onderwijsloopbaan().id(longCount.incrementAndGet());
    }
}

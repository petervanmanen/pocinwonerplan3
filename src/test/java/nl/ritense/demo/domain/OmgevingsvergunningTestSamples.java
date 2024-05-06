package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OmgevingsvergunningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omgevingsvergunning getOmgevingsvergunningSample1() {
        return new Omgevingsvergunning().id(1L);
    }

    public static Omgevingsvergunning getOmgevingsvergunningSample2() {
        return new Omgevingsvergunning().id(2L);
    }

    public static Omgevingsvergunning getOmgevingsvergunningRandomSampleGenerator() {
        return new Omgevingsvergunning().id(longCount.incrementAndGet());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OmgevingsdocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Omgevingsdocument getOmgevingsdocumentSample1() {
        return new Omgevingsdocument().id(1L);
    }

    public static Omgevingsdocument getOmgevingsdocumentSample2() {
        return new Omgevingsdocument().id(2L);
    }

    public static Omgevingsdocument getOmgevingsdocumentRandomSampleGenerator() {
        return new Omgevingsdocument().id(longCount.incrementAndGet());
    }
}

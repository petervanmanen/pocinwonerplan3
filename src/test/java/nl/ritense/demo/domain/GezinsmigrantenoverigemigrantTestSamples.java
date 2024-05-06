package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class GezinsmigrantenoverigemigrantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gezinsmigrantenoverigemigrant getGezinsmigrantenoverigemigrantSample1() {
        return new Gezinsmigrantenoverigemigrant().id(1L);
    }

    public static Gezinsmigrantenoverigemigrant getGezinsmigrantenoverigemigrantSample2() {
        return new Gezinsmigrantenoverigemigrant().id(2L);
    }

    public static Gezinsmigrantenoverigemigrant getGezinsmigrantenoverigemigrantRandomSampleGenerator() {
        return new Gezinsmigrantenoverigemigrant().id(longCount.incrementAndGet());
    }
}

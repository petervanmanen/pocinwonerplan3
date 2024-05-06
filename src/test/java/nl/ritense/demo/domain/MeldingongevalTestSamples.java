package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class MeldingongevalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Meldingongeval getMeldingongevalSample1() {
        return new Meldingongeval().id(1L);
    }

    public static Meldingongeval getMeldingongevalSample2() {
        return new Meldingongeval().id(2L);
    }

    public static Meldingongeval getMeldingongevalRandomSampleGenerator() {
        return new Meldingongeval().id(longCount.incrementAndGet());
    }
}

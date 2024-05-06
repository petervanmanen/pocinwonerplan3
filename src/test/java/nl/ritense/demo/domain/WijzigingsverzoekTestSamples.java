package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class WijzigingsverzoekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wijzigingsverzoek getWijzigingsverzoekSample1() {
        return new Wijzigingsverzoek().id(1L);
    }

    public static Wijzigingsverzoek getWijzigingsverzoekSample2() {
        return new Wijzigingsverzoek().id(2L);
    }

    public static Wijzigingsverzoek getWijzigingsverzoekRandomSampleGenerator() {
        return new Wijzigingsverzoek().id(longCount.incrementAndGet());
    }
}

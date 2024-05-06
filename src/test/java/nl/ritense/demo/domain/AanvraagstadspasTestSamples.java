package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagstadspasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraagstadspas getAanvraagstadspasSample1() {
        return new Aanvraagstadspas().id(1L);
    }

    public static Aanvraagstadspas getAanvraagstadspasSample2() {
        return new Aanvraagstadspas().id(2L);
    }

    public static Aanvraagstadspas getAanvraagstadspasRandomSampleGenerator() {
        return new Aanvraagstadspas().id(longCount.incrementAndGet());
    }
}

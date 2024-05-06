package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InonderzoekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inonderzoek getInonderzoekSample1() {
        return new Inonderzoek().id(1L).aanduidinggegevensinonderzoek("aanduidinggegevensinonderzoek1");
    }

    public static Inonderzoek getInonderzoekSample2() {
        return new Inonderzoek().id(2L).aanduidinggegevensinonderzoek("aanduidinggegevensinonderzoek2");
    }

    public static Inonderzoek getInonderzoekRandomSampleGenerator() {
        return new Inonderzoek().id(longCount.incrementAndGet()).aanduidinggegevensinonderzoek(UUID.randomUUID().toString());
    }
}

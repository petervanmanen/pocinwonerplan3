package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AansluitputTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aansluitput getAansluitputSample1() {
        return new Aansluitput().id(1L).aansluitpunt("aansluitpunt1").risicogebied("risicogebied1").type("type1");
    }

    public static Aansluitput getAansluitputSample2() {
        return new Aansluitput().id(2L).aansluitpunt("aansluitpunt2").risicogebied("risicogebied2").type("type2");
    }

    public static Aansluitput getAansluitputRandomSampleGenerator() {
        return new Aansluitput()
            .id(longCount.incrementAndGet())
            .aansluitpunt(UUID.randomUUID().toString())
            .risicogebied(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}

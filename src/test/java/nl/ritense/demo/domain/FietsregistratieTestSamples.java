package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FietsregistratieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fietsregistratie getFietsregistratieSample1() {
        return new Fietsregistratie().id(1L).gelabeld("gelabeld1").verwijderd("verwijderd1");
    }

    public static Fietsregistratie getFietsregistratieSample2() {
        return new Fietsregistratie().id(2L).gelabeld("gelabeld2").verwijderd("verwijderd2");
    }

    public static Fietsregistratie getFietsregistratieRandomSampleGenerator() {
        return new Fietsregistratie()
            .id(longCount.incrementAndGet())
            .gelabeld(UUID.randomUUID().toString())
            .verwijderd(UUID.randomUUID().toString());
    }
}

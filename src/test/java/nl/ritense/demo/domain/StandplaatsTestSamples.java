package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StandplaatsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Standplaats getStandplaatsSample1() {
        return new Standplaats().id(1L).adres("adres1").beschrijving("beschrijving1").naaminstelling("naaminstelling1");
    }

    public static Standplaats getStandplaatsSample2() {
        return new Standplaats().id(2L).adres("adres2").beschrijving("beschrijving2").naaminstelling("naaminstelling2");
    }

    public static Standplaats getStandplaatsRandomSampleGenerator() {
        return new Standplaats()
            .id(longCount.incrementAndGet())
            .adres(UUID.randomUUID().toString())
            .beschrijving(UUID.randomUUID().toString())
            .naaminstelling(UUID.randomUUID().toString());
    }
}

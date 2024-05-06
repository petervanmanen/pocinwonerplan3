package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KoppelingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Koppeling getKoppelingSample1() {
        return new Koppeling().id(1L).beschrijving("beschrijving1").toelichting("toelichting1");
    }

    public static Koppeling getKoppelingSample2() {
        return new Koppeling().id(2L).beschrijving("beschrijving2").toelichting("toelichting2");
    }

    public static Koppeling getKoppelingRandomSampleGenerator() {
        return new Koppeling()
            .id(longCount.incrementAndGet())
            .beschrijving(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}

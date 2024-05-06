package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class KandidaatTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Kandidaat getKandidaatSample1() {
        return new Kandidaat().id(1L).datumingestuurd("datumingestuurd1");
    }

    public static Kandidaat getKandidaatSample2() {
        return new Kandidaat().id(2L).datumingestuurd("datumingestuurd2");
    }

    public static Kandidaat getKandidaatRandomSampleGenerator() {
        return new Kandidaat().id(longCount.incrementAndGet()).datumingestuurd(UUID.randomUUID().toString());
    }
}

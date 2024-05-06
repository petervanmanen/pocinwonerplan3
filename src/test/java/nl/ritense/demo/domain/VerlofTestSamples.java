package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerlofTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verlof getVerlofSample1() {
        return new Verlof().id(1L).datumtijdeinde("datumtijdeinde1").datumtijdstart("datumtijdstart1");
    }

    public static Verlof getVerlofSample2() {
        return new Verlof().id(2L).datumtijdeinde("datumtijdeinde2").datumtijdstart("datumtijdstart2");
    }

    public static Verlof getVerlofRandomSampleGenerator() {
        return new Verlof()
            .id(longCount.incrementAndGet())
            .datumtijdeinde(UUID.randomUUID().toString())
            .datumtijdstart(UUID.randomUUID().toString());
    }
}

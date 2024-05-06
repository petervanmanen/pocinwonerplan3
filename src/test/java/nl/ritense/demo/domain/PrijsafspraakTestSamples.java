package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PrijsafspraakTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prijsafspraak getPrijsafspraakSample1() {
        return new Prijsafspraak().id(1L).titel("titel1");
    }

    public static Prijsafspraak getPrijsafspraakSample2() {
        return new Prijsafspraak().id(2L).titel("titel2");
    }

    public static Prijsafspraak getPrijsafspraakRandomSampleGenerator() {
        return new Prijsafspraak().id(longCount.incrementAndGet()).titel(UUID.randomUUID().toString());
    }
}

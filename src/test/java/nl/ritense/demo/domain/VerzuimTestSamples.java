package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VerzuimTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Verzuim getVerzuimSample1() {
        return new Verzuim().id(1L).datumtijdeinde("datumtijdeinde1").datumtijdstart("datumtijdstart1");
    }

    public static Verzuim getVerzuimSample2() {
        return new Verzuim().id(2L).datumtijdeinde("datumtijdeinde2").datumtijdstart("datumtijdstart2");
    }

    public static Verzuim getVerzuimRandomSampleGenerator() {
        return new Verzuim()
            .id(longCount.incrementAndGet())
            .datumtijdeinde(UUID.randomUUID().toString())
            .datumtijdstart(UUID.randomUUID().toString());
    }
}

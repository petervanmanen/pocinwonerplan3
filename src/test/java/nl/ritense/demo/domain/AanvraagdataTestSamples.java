package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagdataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraagdata getAanvraagdataSample1() {
        return new Aanvraagdata().id(1L).data("data1").veld("veld1");
    }

    public static Aanvraagdata getAanvraagdataSample2() {
        return new Aanvraagdata().id(2L).data("data2").veld("veld2");
    }

    public static Aanvraagdata getAanvraagdataRandomSampleGenerator() {
        return new Aanvraagdata().id(longCount.incrementAndGet()).data(UUID.randomUUID().toString()).veld(UUID.randomUUID().toString());
    }
}

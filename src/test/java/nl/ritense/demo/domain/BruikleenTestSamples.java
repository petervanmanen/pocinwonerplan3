package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BruikleenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bruikleen getBruikleenSample1() {
        return new Bruikleen().id(1L).aanvraagdoor("aanvraagdoor1").toestemmingdoor("toestemmingdoor1");
    }

    public static Bruikleen getBruikleenSample2() {
        return new Bruikleen().id(2L).aanvraagdoor("aanvraagdoor2").toestemmingdoor("toestemmingdoor2");
    }

    public static Bruikleen getBruikleenRandomSampleGenerator() {
        return new Bruikleen()
            .id(longCount.incrementAndGet())
            .aanvraagdoor(UUID.randomUUID().toString())
            .toestemmingdoor(UUID.randomUUID().toString());
    }
}

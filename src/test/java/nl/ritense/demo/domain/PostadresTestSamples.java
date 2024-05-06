package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PostadresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Postadres getPostadresSample1() {
        return new Postadres()
            .id(1L)
            .postadrestype("postadrestype1")
            .postbusofantwoordnummer("postbusofantwoordnummer1")
            .postcodepostadres("postcodepostadres1");
    }

    public static Postadres getPostadresSample2() {
        return new Postadres()
            .id(2L)
            .postadrestype("postadrestype2")
            .postbusofantwoordnummer("postbusofantwoordnummer2")
            .postcodepostadres("postcodepostadres2");
    }

    public static Postadres getPostadresRandomSampleGenerator() {
        return new Postadres()
            .id(longCount.incrementAndGet())
            .postadrestype(UUID.randomUUID().toString())
            .postbusofantwoordnummer(UUID.randomUUID().toString())
            .postcodepostadres(UUID.randomUUID().toString());
    }
}

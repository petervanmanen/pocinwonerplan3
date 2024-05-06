package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Status getStatusSample1() {
        return new Status()
            .id(1L)
            .datumstatusgezet("datumstatusgezet1")
            .indicatieiaatstgezettestatus("indicatieiaatstgezettestatus1")
            .statustoelichting("statustoelichting1");
    }

    public static Status getStatusSample2() {
        return new Status()
            .id(2L)
            .datumstatusgezet("datumstatusgezet2")
            .indicatieiaatstgezettestatus("indicatieiaatstgezettestatus2")
            .statustoelichting("statustoelichting2");
    }

    public static Status getStatusRandomSampleGenerator() {
        return new Status()
            .id(longCount.incrementAndGet())
            .datumstatusgezet(UUID.randomUUID().toString())
            .indicatieiaatstgezettestatus(UUID.randomUUID().toString())
            .statustoelichting(UUID.randomUUID().toString());
    }
}

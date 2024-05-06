package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ErouteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eroute getErouteSample1() {
        return new Eroute().id(1L).geometrie("geometrie1").eroutecode("eroutecode1").eroutesoort("eroutesoort1");
    }

    public static Eroute getErouteSample2() {
        return new Eroute().id(2L).geometrie("geometrie2").eroutecode("eroutecode2").eroutesoort("eroutesoort2");
    }

    public static Eroute getErouteRandomSampleGenerator() {
        return new Eroute()
            .id(longCount.incrementAndGet())
            .geometrie(UUID.randomUUID().toString())
            .eroutecode(UUID.randomUUID().toString())
            .eroutesoort(UUID.randomUUID().toString());
    }
}

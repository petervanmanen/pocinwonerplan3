package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AreaalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Areaal getAreaalSample1() {
        return new Areaal().id(1L).geometrie("geometrie1");
    }

    public static Areaal getAreaalSample2() {
        return new Areaal().id(2L).geometrie("geometrie2");
    }

    public static Areaal getAreaalRandomSampleGenerator() {
        return new Areaal().id(longCount.incrementAndGet()).geometrie(UUID.randomUUID().toString());
    }
}

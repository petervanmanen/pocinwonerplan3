package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WozdeelobjectcodeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wozdeelobjectcode getWozdeelobjectcodeSample1() {
        return new Wozdeelobjectcode().id(1L).deelobjectcode("deelobjectcode1").naamdeelobjectcode("naamdeelobjectcode1");
    }

    public static Wozdeelobjectcode getWozdeelobjectcodeSample2() {
        return new Wozdeelobjectcode().id(2L).deelobjectcode("deelobjectcode2").naamdeelobjectcode("naamdeelobjectcode2");
    }

    public static Wozdeelobjectcode getWozdeelobjectcodeRandomSampleGenerator() {
        return new Wozdeelobjectcode()
            .id(longCount.incrementAndGet())
            .deelobjectcode(UUID.randomUUID().toString())
            .naamdeelobjectcode(UUID.randomUUID().toString());
    }
}

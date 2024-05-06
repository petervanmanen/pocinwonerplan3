package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WozdeelobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wozdeelobject getWozdeelobjectSample1() {
        return new Wozdeelobject()
            .id(1L)
            .codewozdeelobject("codewozdeelobject1")
            .statuswozdeelobject("statuswozdeelobject1")
            .wozdeelobjectnummer("wozdeelobjectnummer1");
    }

    public static Wozdeelobject getWozdeelobjectSample2() {
        return new Wozdeelobject()
            .id(2L)
            .codewozdeelobject("codewozdeelobject2")
            .statuswozdeelobject("statuswozdeelobject2")
            .wozdeelobjectnummer("wozdeelobjectnummer2");
    }

    public static Wozdeelobject getWozdeelobjectRandomSampleGenerator() {
        return new Wozdeelobject()
            .id(longCount.incrementAndGet())
            .codewozdeelobject(UUID.randomUUID().toString())
            .statuswozdeelobject(UUID.randomUUID().toString())
            .wozdeelobjectnummer(UUID.randomUUID().toString());
    }
}

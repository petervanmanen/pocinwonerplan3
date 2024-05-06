package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OveriggebouwdobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Overiggebouwdobject getOveriggebouwdobjectSample1() {
        return new Overiggebouwdobject()
            .id(1L)
            .bouwjaar("bouwjaar1")
            .indicatieplanobject("indicatieplanobject1")
            .overiggebouwdobjectidentificatie("overiggebouwdobjectidentificatie1");
    }

    public static Overiggebouwdobject getOveriggebouwdobjectSample2() {
        return new Overiggebouwdobject()
            .id(2L)
            .bouwjaar("bouwjaar2")
            .indicatieplanobject("indicatieplanobject2")
            .overiggebouwdobjectidentificatie("overiggebouwdobjectidentificatie2");
    }

    public static Overiggebouwdobject getOveriggebouwdobjectRandomSampleGenerator() {
        return new Overiggebouwdobject()
            .id(longCount.incrementAndGet())
            .bouwjaar(UUID.randomUUID().toString())
            .indicatieplanobject(UUID.randomUUID().toString())
            .overiggebouwdobjectidentificatie(UUID.randomUUID().toString());
    }
}

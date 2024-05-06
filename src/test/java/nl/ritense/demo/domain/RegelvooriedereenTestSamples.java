package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RegelvooriedereenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Regelvooriedereen getRegelvooriedereenSample1() {
        return new Regelvooriedereen().id(1L).activiteitregelkwalificatie("activiteitregelkwalificatie1");
    }

    public static Regelvooriedereen getRegelvooriedereenSample2() {
        return new Regelvooriedereen().id(2L).activiteitregelkwalificatie("activiteitregelkwalificatie2");
    }

    public static Regelvooriedereen getRegelvooriedereenRandomSampleGenerator() {
        return new Regelvooriedereen().id(longCount.incrementAndGet()).activiteitregelkwalificatie(UUID.randomUUID().toString());
    }
}

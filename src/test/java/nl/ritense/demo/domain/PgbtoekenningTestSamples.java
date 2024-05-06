package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PgbtoekenningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pgbtoekenning getPgbtoekenningSample1() {
        return new Pgbtoekenning().id(1L);
    }

    public static Pgbtoekenning getPgbtoekenningSample2() {
        return new Pgbtoekenning().id(2L);
    }

    public static Pgbtoekenning getPgbtoekenningRandomSampleGenerator() {
        return new Pgbtoekenning().id(longCount.incrementAndGet());
    }
}

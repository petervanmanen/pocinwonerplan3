package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class NotarielestatusTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Notarielestatus getNotarielestatusSample1() {
        return new Notarielestatus().id(1L);
    }

    public static Notarielestatus getNotarielestatusSample2() {
        return new Notarielestatus().id(2L);
    }

    public static Notarielestatus getNotarielestatusRandomSampleGenerator() {
        return new Notarielestatus().id(longCount.incrementAndGet());
    }
}

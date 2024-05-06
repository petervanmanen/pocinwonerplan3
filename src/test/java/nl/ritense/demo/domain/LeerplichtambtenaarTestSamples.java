package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LeerplichtambtenaarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leerplichtambtenaar getLeerplichtambtenaarSample1() {
        return new Leerplichtambtenaar().id(1L);
    }

    public static Leerplichtambtenaar getLeerplichtambtenaarSample2() {
        return new Leerplichtambtenaar().id(2L);
    }

    public static Leerplichtambtenaar getLeerplichtambtenaarRandomSampleGenerator() {
        return new Leerplichtambtenaar().id(longCount.incrementAndGet());
    }
}

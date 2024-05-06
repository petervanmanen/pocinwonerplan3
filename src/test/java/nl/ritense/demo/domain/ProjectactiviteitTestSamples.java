package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ProjectactiviteitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Projectactiviteit getProjectactiviteitSample1() {
        return new Projectactiviteit().id(1L);
    }

    public static Projectactiviteit getProjectactiviteitSample2() {
        return new Projectactiviteit().id(2L);
    }

    public static Projectactiviteit getProjectactiviteitRandomSampleGenerator() {
        return new Projectactiviteit().id(longCount.incrementAndGet());
    }
}

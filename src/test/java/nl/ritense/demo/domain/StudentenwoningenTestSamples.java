package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class StudentenwoningenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Studentenwoningen getStudentenwoningenSample1() {
        return new Studentenwoningen().id(1L);
    }

    public static Studentenwoningen getStudentenwoningenSample2() {
        return new Studentenwoningen().id(2L);
    }

    public static Studentenwoningen getStudentenwoningenRandomSampleGenerator() {
        return new Studentenwoningen().id(longCount.incrementAndGet());
    }
}

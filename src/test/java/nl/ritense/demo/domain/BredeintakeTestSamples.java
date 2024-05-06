package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BredeintakeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bredeintake getBredeintakeSample1() {
        return new Bredeintake().id(1L);
    }

    public static Bredeintake getBredeintakeSample2() {
        return new Bredeintake().id(2L);
    }

    public static Bredeintake getBredeintakeRandomSampleGenerator() {
        return new Bredeintake().id(longCount.incrementAndGet());
    }
}

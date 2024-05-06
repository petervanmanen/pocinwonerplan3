package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class BelanghebbendeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Belanghebbende getBelanghebbendeSample1() {
        return new Belanghebbende().id(1L);
    }

    public static Belanghebbende getBelanghebbendeSample2() {
        return new Belanghebbende().id(2L);
    }

    public static Belanghebbende getBelanghebbendeRandomSampleGenerator() {
        return new Belanghebbende().id(longCount.incrementAndGet());
    }
}

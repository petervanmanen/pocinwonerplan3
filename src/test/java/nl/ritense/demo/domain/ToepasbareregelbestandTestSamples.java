package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ToepasbareregelbestandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Toepasbareregelbestand getToepasbareregelbestandSample1() {
        return new Toepasbareregelbestand().id(1L);
    }

    public static Toepasbareregelbestand getToepasbareregelbestandSample2() {
        return new Toepasbareregelbestand().id(2L);
    }

    public static Toepasbareregelbestand getToepasbareregelbestandRandomSampleGenerator() {
        return new Toepasbareregelbestand().id(longCount.incrementAndGet());
    }
}

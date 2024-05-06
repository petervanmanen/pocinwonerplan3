package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class GemachtigdeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gemachtigde getGemachtigdeSample1() {
        return new Gemachtigde().id(1L);
    }

    public static Gemachtigde getGemachtigdeSample2() {
        return new Gemachtigde().id(2L);
    }

    public static Gemachtigde getGemachtigdeRandomSampleGenerator() {
        return new Gemachtigde().id(longCount.incrementAndGet());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class OnderwijsinstituutTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Onderwijsinstituut getOnderwijsinstituutSample1() {
        return new Onderwijsinstituut().id(1L);
    }

    public static Onderwijsinstituut getOnderwijsinstituutSample2() {
        return new Onderwijsinstituut().id(2L);
    }

    public static Onderwijsinstituut getOnderwijsinstituutRandomSampleGenerator() {
        return new Onderwijsinstituut().id(longCount.incrementAndGet());
    }
}

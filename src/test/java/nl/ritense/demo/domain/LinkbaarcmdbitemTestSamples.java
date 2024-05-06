package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LinkbaarcmdbitemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Linkbaarcmdbitem getLinkbaarcmdbitemSample1() {
        return new Linkbaarcmdbitem().id(1L);
    }

    public static Linkbaarcmdbitem getLinkbaarcmdbitemSample2() {
        return new Linkbaarcmdbitem().id(2L);
    }

    public static Linkbaarcmdbitem getLinkbaarcmdbitemRandomSampleGenerator() {
        return new Linkbaarcmdbitem().id(longCount.incrementAndGet());
    }
}

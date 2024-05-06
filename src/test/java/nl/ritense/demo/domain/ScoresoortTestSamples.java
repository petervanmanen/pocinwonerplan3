package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ScoresoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Scoresoort getScoresoortSample1() {
        return new Scoresoort().id(1L).niveau("niveau1");
    }

    public static Scoresoort getScoresoortSample2() {
        return new Scoresoort().id(2L).niveau("niveau2");
    }

    public static Scoresoort getScoresoortRandomSampleGenerator() {
        return new Scoresoort().id(longCount.incrementAndGet()).niveau(UUID.randomUUID().toString());
    }
}

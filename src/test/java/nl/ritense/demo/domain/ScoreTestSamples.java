package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ScoreTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Score getScoreSample1() {
        return new Score().id(1L).datum("datum1");
    }

    public static Score getScoreSample2() {
        return new Score().id(2L).datum("datum2");
    }

    public static Score getScoreRandomSampleGenerator() {
        return new Score().id(longCount.incrementAndGet()).datum(UUID.randomUUID().toString());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeperkingscoreTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beperkingscore getBeperkingscoreSample1() {
        return new Beperkingscore().id(1L).commentaar("commentaar1").score("score1").wet("wet1");
    }

    public static Beperkingscore getBeperkingscoreSample2() {
        return new Beperkingscore().id(2L).commentaar("commentaar2").score("score2").wet("wet2");
    }

    public static Beperkingscore getBeperkingscoreRandomSampleGenerator() {
        return new Beperkingscore()
            .id(longCount.incrementAndGet())
            .commentaar(UUID.randomUUID().toString())
            .score(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}

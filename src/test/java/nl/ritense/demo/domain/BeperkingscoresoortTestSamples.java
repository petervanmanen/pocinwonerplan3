package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeperkingscoresoortTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beperkingscoresoort getBeperkingscoresoortSample1() {
        return new Beperkingscoresoort().id(1L).vraag("vraag1").wet("wet1");
    }

    public static Beperkingscoresoort getBeperkingscoresoortSample2() {
        return new Beperkingscoresoort().id(2L).vraag("vraag2").wet("wet2");
    }

    public static Beperkingscoresoort getBeperkingscoresoortRandomSampleGenerator() {
        return new Beperkingscoresoort()
            .id(longCount.incrementAndGet())
            .vraag(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}

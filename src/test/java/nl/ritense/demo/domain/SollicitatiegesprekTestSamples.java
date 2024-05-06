package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SollicitatiegesprekTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sollicitatiegesprek getSollicitatiegesprekSample1() {
        return new Sollicitatiegesprek().id(1L).opmerkingen("opmerkingen1");
    }

    public static Sollicitatiegesprek getSollicitatiegesprekSample2() {
        return new Sollicitatiegesprek().id(2L).opmerkingen("opmerkingen2");
    }

    public static Sollicitatiegesprek getSollicitatiegesprekRandomSampleGenerator() {
        return new Sollicitatiegesprek().id(longCount.incrementAndGet()).opmerkingen(UUID.randomUUID().toString());
    }
}

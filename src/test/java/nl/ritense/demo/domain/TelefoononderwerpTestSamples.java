package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TelefoononderwerpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telefoononderwerp getTelefoononderwerpSample1() {
        return new Telefoononderwerp().id(1L).onderwerp("onderwerp1");
    }

    public static Telefoononderwerp getTelefoononderwerpSample2() {
        return new Telefoononderwerp().id(2L).onderwerp("onderwerp2");
    }

    public static Telefoononderwerp getTelefoononderwerpRandomSampleGenerator() {
        return new Telefoononderwerp().id(longCount.incrementAndGet()).onderwerp(UUID.randomUUID().toString());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InformatiedakloosheidTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Informatiedakloosheid getInformatiedakloosheidSample1() {
        return new Informatiedakloosheid().id(1L).gemeenteoorsprong("gemeenteoorsprong1");
    }

    public static Informatiedakloosheid getInformatiedakloosheidSample2() {
        return new Informatiedakloosheid().id(2L).gemeenteoorsprong("gemeenteoorsprong2");
    }

    public static Informatiedakloosheid getInformatiedakloosheidRandomSampleGenerator() {
        return new Informatiedakloosheid().id(longCount.incrementAndGet()).gemeenteoorsprong(UUID.randomUUID().toString());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankrekeningTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Bankrekening getBankrekeningSample1() {
        return new Bankrekening().id(1L).bank("bank1").nummer("nummer1").tennaamstelling("tennaamstelling1");
    }

    public static Bankrekening getBankrekeningSample2() {
        return new Bankrekening().id(2L).bank("bank2").nummer("nummer2").tennaamstelling("tennaamstelling2");
    }

    public static Bankrekening getBankrekeningRandomSampleGenerator() {
        return new Bankrekening()
            .id(longCount.incrementAndGet())
            .bank(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .tennaamstelling(UUID.randomUUID().toString());
    }
}

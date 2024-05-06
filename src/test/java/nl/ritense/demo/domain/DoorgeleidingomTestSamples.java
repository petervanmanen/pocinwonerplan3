package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DoorgeleidingomTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Doorgeleidingom getDoorgeleidingomSample1() {
        return new Doorgeleidingom().id(1L).afdoening("afdoening1");
    }

    public static Doorgeleidingom getDoorgeleidingomSample2() {
        return new Doorgeleidingom().id(2L).afdoening("afdoening2");
    }

    public static Doorgeleidingom getDoorgeleidingomRandomSampleGenerator() {
        return new Doorgeleidingom().id(longCount.incrementAndGet()).afdoening(UUID.randomUUID().toString());
    }
}

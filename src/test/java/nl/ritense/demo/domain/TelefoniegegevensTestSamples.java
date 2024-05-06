package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TelefoniegegevensTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telefoniegegevens getTelefoniegegevensSample1() {
        return new Telefoniegegevens().id(1L);
    }

    public static Telefoniegegevens getTelefoniegegevensSample2() {
        return new Telefoniegegevens().id(2L);
    }

    public static Telefoniegegevens getTelefoniegegevensRandomSampleGenerator() {
        return new Telefoniegegevens().id(longCount.incrementAndGet());
    }
}

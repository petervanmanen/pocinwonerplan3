package nl.ritense.demo.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UitvoerdergraafwerkzaamhedenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Uitvoerdergraafwerkzaamheden getUitvoerdergraafwerkzaamhedenSample1() {
        return new Uitvoerdergraafwerkzaamheden().id(1L);
    }

    public static Uitvoerdergraafwerkzaamheden getUitvoerdergraafwerkzaamhedenSample2() {
        return new Uitvoerdergraafwerkzaamheden().id(2L);
    }

    public static Uitvoerdergraafwerkzaamheden getUitvoerdergraafwerkzaamhedenRandomSampleGenerator() {
        return new Uitvoerdergraafwerkzaamheden().id(longCount.incrementAndGet());
    }
}

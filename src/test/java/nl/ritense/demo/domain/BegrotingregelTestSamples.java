package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BegrotingregelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Begrotingregel getBegrotingregelSample1() {
        return new Begrotingregel().id(1L).batenlasten("batenlasten1").soortregel("soortregel1");
    }

    public static Begrotingregel getBegrotingregelSample2() {
        return new Begrotingregel().id(2L).batenlasten("batenlasten2").soortregel("soortregel2");
    }

    public static Begrotingregel getBegrotingregelRandomSampleGenerator() {
        return new Begrotingregel()
            .id(longCount.incrementAndGet())
            .batenlasten(UUID.randomUUID().toString())
            .soortregel(UUID.randomUUID().toString());
    }
}

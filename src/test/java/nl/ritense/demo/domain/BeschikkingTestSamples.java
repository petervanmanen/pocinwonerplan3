package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BeschikkingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Beschikking getBeschikkingSample1() {
        return new Beschikking().id(1L).code("code1").commentaar("commentaar1").grondslagen("grondslagen1").wet("wet1");
    }

    public static Beschikking getBeschikkingSample2() {
        return new Beschikking().id(2L).code("code2").commentaar("commentaar2").grondslagen("grondslagen2").wet("wet2");
    }

    public static Beschikking getBeschikkingRandomSampleGenerator() {
        return new Beschikking()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .commentaar(UUID.randomUUID().toString())
            .grondslagen(UUID.randomUUID().toString())
            .wet(UUID.randomUUID().toString());
    }
}

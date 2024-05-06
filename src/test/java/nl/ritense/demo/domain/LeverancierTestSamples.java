package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LeverancierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Leverancier getLeverancierSample1() {
        return new Leverancier()
            .id(1L)
            .agbcode("agbcode1")
            .leverancierscode("leverancierscode1")
            .naam("naam1")
            .soortleverancier("soortleverancier1")
            .soortleveranciercode("soortleveranciercode1");
    }

    public static Leverancier getLeverancierSample2() {
        return new Leverancier()
            .id(2L)
            .agbcode("agbcode2")
            .leverancierscode("leverancierscode2")
            .naam("naam2")
            .soortleverancier("soortleverancier2")
            .soortleveranciercode("soortleveranciercode2");
    }

    public static Leverancier getLeverancierRandomSampleGenerator() {
        return new Leverancier()
            .id(longCount.incrementAndGet())
            .agbcode(UUID.randomUUID().toString())
            .leverancierscode(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .soortleverancier(UUID.randomUUID().toString())
            .soortleveranciercode(UUID.randomUUID().toString());
    }
}

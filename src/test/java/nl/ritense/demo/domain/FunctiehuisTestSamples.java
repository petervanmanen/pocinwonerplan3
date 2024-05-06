package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FunctiehuisTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Functiehuis getFunctiehuisSample1() {
        return new Functiehuis().id(1L).naam("naam1").omschrijving("omschrijving1");
    }

    public static Functiehuis getFunctiehuisSample2() {
        return new Functiehuis().id(2L).naam("naam2").omschrijving("omschrijving2");
    }

    public static Functiehuis getFunctiehuisRandomSampleGenerator() {
        return new Functiehuis()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}

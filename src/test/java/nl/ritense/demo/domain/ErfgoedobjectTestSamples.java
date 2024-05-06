package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ErfgoedobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Erfgoedobject getErfgoedobjectSample1() {
        return new Erfgoedobject()
            .id(1L)
            .dateringtot("dateringtot1")
            .dateringvanaf("dateringvanaf1")
            .omschrijving("omschrijving1")
            .titel("titel1");
    }

    public static Erfgoedobject getErfgoedobjectSample2() {
        return new Erfgoedobject()
            .id(2L)
            .dateringtot("dateringtot2")
            .dateringvanaf("dateringvanaf2")
            .omschrijving("omschrijving2")
            .titel("titel2");
    }

    public static Erfgoedobject getErfgoedobjectRandomSampleGenerator() {
        return new Erfgoedobject()
            .id(longCount.incrementAndGet())
            .dateringtot(UUID.randomUUID().toString())
            .dateringvanaf(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}

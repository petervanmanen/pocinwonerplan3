package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WerkorderTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Werkorder getWerkorderSample1() {
        return new Werkorder()
            .id(1L)
            .code("code1")
            .documentnummer("documentnummer1")
            .naam("naam1")
            .omschrijving("omschrijving1")
            .werkordertype("werkordertype1");
    }

    public static Werkorder getWerkorderSample2() {
        return new Werkorder()
            .id(2L)
            .code("code2")
            .documentnummer("documentnummer2")
            .naam("naam2")
            .omschrijving("omschrijving2")
            .werkordertype("werkordertype2");
    }

    public static Werkorder getWerkorderRandomSampleGenerator() {
        return new Werkorder()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .documentnummer(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .werkordertype(UUID.randomUUID().toString());
    }
}

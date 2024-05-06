package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ZelfredzaamheidmatrixTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Zelfredzaamheidmatrix getZelfredzaamheidmatrixSample1() {
        return new Zelfredzaamheidmatrix()
            .id(1L)
            .datumeindegeldigheid("datumeindegeldigheid1")
            .datumstartgeldigheid("datumstartgeldigheid1")
            .naam("naam1")
            .omschrijving("omschrijving1");
    }

    public static Zelfredzaamheidmatrix getZelfredzaamheidmatrixSample2() {
        return new Zelfredzaamheidmatrix()
            .id(2L)
            .datumeindegeldigheid("datumeindegeldigheid2")
            .datumstartgeldigheid("datumstartgeldigheid2")
            .naam("naam2")
            .omschrijving("omschrijving2");
    }

    public static Zelfredzaamheidmatrix getZelfredzaamheidmatrixRandomSampleGenerator() {
        return new Zelfredzaamheidmatrix()
            .id(longCount.incrementAndGet())
            .datumeindegeldigheid(UUID.randomUUID().toString())
            .datumstartgeldigheid(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}

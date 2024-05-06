package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpdrachtgeverTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opdrachtgever getOpdrachtgeverSample1() {
        return new Opdrachtgever()
            .id(1L)
            .clustercode("clustercode1")
            .clusteromschrijving("clusteromschrijving1")
            .naam("naam1")
            .nummer("nummer1")
            .omschrijving("omschrijving1");
    }

    public static Opdrachtgever getOpdrachtgeverSample2() {
        return new Opdrachtgever()
            .id(2L)
            .clustercode("clustercode2")
            .clusteromschrijving("clusteromschrijving2")
            .naam("naam2")
            .nummer("nummer2")
            .omschrijving("omschrijving2");
    }

    public static Opdrachtgever getOpdrachtgeverRandomSampleGenerator() {
        return new Opdrachtgever()
            .id(longCount.incrementAndGet())
            .clustercode(UUID.randomUUID().toString())
            .clusteromschrijving(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}

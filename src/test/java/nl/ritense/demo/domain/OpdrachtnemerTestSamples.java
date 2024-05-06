package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OpdrachtnemerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Opdrachtnemer getOpdrachtnemerSample1() {
        return new Opdrachtnemer()
            .id(1L)
            .clustercode("clustercode1")
            .clustercodeomschrijving("clustercodeomschrijving1")
            .naam("naam1")
            .nummer("nummer1")
            .omschrijving("omschrijving1");
    }

    public static Opdrachtnemer getOpdrachtnemerSample2() {
        return new Opdrachtnemer()
            .id(2L)
            .clustercode("clustercode2")
            .clustercodeomschrijving("clustercodeomschrijving2")
            .naam("naam2")
            .nummer("nummer2")
            .omschrijving("omschrijving2");
    }

    public static Opdrachtnemer getOpdrachtnemerRandomSampleGenerator() {
        return new Opdrachtnemer()
            .id(longCount.incrementAndGet())
            .clustercode(UUID.randomUUID().toString())
            .clustercodeomschrijving(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString());
    }
}

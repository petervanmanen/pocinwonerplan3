package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrganisatorischeeenheidhrTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Organisatorischeeenheidhr getOrganisatorischeeenheidhrSample1() {
        return new Organisatorischeeenheidhr().id(1L).naam("naam1").type("type1");
    }

    public static Organisatorischeeenheidhr getOrganisatorischeeenheidhrSample2() {
        return new Organisatorischeeenheidhr().id(2L).naam("naam2").type("type2");
    }

    public static Organisatorischeeenheidhr getOrganisatorischeeenheidhrRandomSampleGenerator() {
        return new Organisatorischeeenheidhr()
            .id(longCount.incrementAndGet())
            .naam(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}

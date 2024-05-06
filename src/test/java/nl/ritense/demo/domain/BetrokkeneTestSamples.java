package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BetrokkeneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Betrokkene getBetrokkeneSample1() {
        return new Betrokkene()
            .id(1L)
            .adresbinnenland("adresbinnenland1")
            .adresbuitenland("adresbuitenland1")
            .identificatie("identificatie1")
            .naam("naam1")
            .rol("rol1");
    }

    public static Betrokkene getBetrokkeneSample2() {
        return new Betrokkene()
            .id(2L)
            .adresbinnenland("adresbinnenland2")
            .adresbuitenland("adresbuitenland2")
            .identificatie("identificatie2")
            .naam("naam2")
            .rol("rol2");
    }

    public static Betrokkene getBetrokkeneRandomSampleGenerator() {
        return new Betrokkene()
            .id(longCount.incrementAndGet())
            .adresbinnenland(UUID.randomUUID().toString())
            .adresbuitenland(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .rol(UUID.randomUUID().toString());
    }
}

package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class VastgoedcontractTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Vastgoedcontract getVastgoedcontractSample1() {
        return new Vastgoedcontract()
            .id(1L)
            .beschrijving("beschrijving1")
            .identificatie("identificatie1")
            .opzegtermijn("opzegtermijn1")
            .status("status1")
            .type("type1");
    }

    public static Vastgoedcontract getVastgoedcontractSample2() {
        return new Vastgoedcontract()
            .id(2L)
            .beschrijving("beschrijving2")
            .identificatie("identificatie2")
            .opzegtermijn("opzegtermijn2")
            .status("status2")
            .type("type2");
    }

    public static Vastgoedcontract getVastgoedcontractRandomSampleGenerator() {
        return new Vastgoedcontract()
            .id(longCount.incrementAndGet())
            .beschrijving(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .opzegtermijn(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}

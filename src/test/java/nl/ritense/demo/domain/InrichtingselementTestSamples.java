package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InrichtingselementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Inrichtingselement getInrichtingselementSample1() {
        return new Inrichtingselement()
            .id(1L)
            .geometrieinrichtingselement("geometrieinrichtingselement1")
            .identificatieinrichtingselement("identificatieinrichtingselement1")
            .lod0geometrieinrichtingselement("lod0geometrieinrichtingselement1")
            .plustypeinrichtingselement("plustypeinrichtingselement1")
            .relatievehoogteligginginrichtingselement("relatievehoogteligginginrichtingselement1")
            .statusinrichtingselement("statusinrichtingselement1")
            .typeinrichtingselement("typeinrichtingselement1");
    }

    public static Inrichtingselement getInrichtingselementSample2() {
        return new Inrichtingselement()
            .id(2L)
            .geometrieinrichtingselement("geometrieinrichtingselement2")
            .identificatieinrichtingselement("identificatieinrichtingselement2")
            .lod0geometrieinrichtingselement("lod0geometrieinrichtingselement2")
            .plustypeinrichtingselement("plustypeinrichtingselement2")
            .relatievehoogteligginginrichtingselement("relatievehoogteligginginrichtingselement2")
            .statusinrichtingselement("statusinrichtingselement2")
            .typeinrichtingselement("typeinrichtingselement2");
    }

    public static Inrichtingselement getInrichtingselementRandomSampleGenerator() {
        return new Inrichtingselement()
            .id(longCount.incrementAndGet())
            .geometrieinrichtingselement(UUID.randomUUID().toString())
            .identificatieinrichtingselement(UUID.randomUUID().toString())
            .lod0geometrieinrichtingselement(UUID.randomUUID().toString())
            .plustypeinrichtingselement(UUID.randomUUID().toString())
            .relatievehoogteligginginrichtingselement(UUID.randomUUID().toString())
            .statusinrichtingselement(UUID.randomUUID().toString())
            .typeinrichtingselement(UUID.randomUUID().toString());
    }
}

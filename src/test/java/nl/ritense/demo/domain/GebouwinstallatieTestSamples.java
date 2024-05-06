package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebouwinstallatieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebouwinstallatie getGebouwinstallatieSample1() {
        return new Gebouwinstallatie()
            .id(1L)
            .geometriegebouwinstallatie("geometriegebouwinstallatie1")
            .identificatiegebouwinstallatie("identificatiegebouwinstallatie1")
            .lod0geometriegebouwinstallatie("lod0geometriegebouwinstallatie1")
            .relatievehoogteligginggebouwinstallatie("relatievehoogteligginggebouwinstallatie1")
            .statusgebouwinstallatie("statusgebouwinstallatie1")
            .typegebouwinstallatie("typegebouwinstallatie1");
    }

    public static Gebouwinstallatie getGebouwinstallatieSample2() {
        return new Gebouwinstallatie()
            .id(2L)
            .geometriegebouwinstallatie("geometriegebouwinstallatie2")
            .identificatiegebouwinstallatie("identificatiegebouwinstallatie2")
            .lod0geometriegebouwinstallatie("lod0geometriegebouwinstallatie2")
            .relatievehoogteligginggebouwinstallatie("relatievehoogteligginggebouwinstallatie2")
            .statusgebouwinstallatie("statusgebouwinstallatie2")
            .typegebouwinstallatie("typegebouwinstallatie2");
    }

    public static Gebouwinstallatie getGebouwinstallatieRandomSampleGenerator() {
        return new Gebouwinstallatie()
            .id(longCount.incrementAndGet())
            .geometriegebouwinstallatie(UUID.randomUUID().toString())
            .identificatiegebouwinstallatie(UUID.randomUUID().toString())
            .lod0geometriegebouwinstallatie(UUID.randomUUID().toString())
            .relatievehoogteligginggebouwinstallatie(UUID.randomUUID().toString())
            .statusgebouwinstallatie(UUID.randomUUID().toString())
            .typegebouwinstallatie(UUID.randomUUID().toString());
    }
}

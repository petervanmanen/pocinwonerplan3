package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GebouwdobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gebouwdobject getGebouwdobjectSample1() {
        return new Gebouwdobject()
            .id(1L)
            .bouwkundigebestemmingactueel("bouwkundigebestemmingactueel1")
            .brutoinhoud("brutoinhoud1")
            .identificatie("identificatie1")
            .inwinningoppervlakte("inwinningoppervlakte1")
            .oppervlakteobject("oppervlakteobject1")
            .statusvoortgangbouw("statusvoortgangbouw1");
    }

    public static Gebouwdobject getGebouwdobjectSample2() {
        return new Gebouwdobject()
            .id(2L)
            .bouwkundigebestemmingactueel("bouwkundigebestemmingactueel2")
            .brutoinhoud("brutoinhoud2")
            .identificatie("identificatie2")
            .inwinningoppervlakte("inwinningoppervlakte2")
            .oppervlakteobject("oppervlakteobject2")
            .statusvoortgangbouw("statusvoortgangbouw2");
    }

    public static Gebouwdobject getGebouwdobjectRandomSampleGenerator() {
        return new Gebouwdobject()
            .id(longCount.incrementAndGet())
            .bouwkundigebestemmingactueel(UUID.randomUUID().toString())
            .brutoinhoud(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .inwinningoppervlakte(UUID.randomUUID().toString())
            .oppervlakteobject(UUID.randomUUID().toString())
            .statusvoortgangbouw(UUID.randomUUID().toString());
    }
}

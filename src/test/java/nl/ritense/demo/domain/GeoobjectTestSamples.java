package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GeoobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Geoobject getGeoobjectSample1() {
        return new Geoobject()
            .id(1L)
            .datumbegingeldigheid("datumbegingeldigheid1")
            .datumeindegeldigheid("datumeindegeldigheid1")
            .geometriesoort("geometriesoort1")
            .identificatie("identificatie1");
    }

    public static Geoobject getGeoobjectSample2() {
        return new Geoobject()
            .id(2L)
            .datumbegingeldigheid("datumbegingeldigheid2")
            .datumeindegeldigheid("datumeindegeldigheid2")
            .geometriesoort("geometriesoort2")
            .identificatie("identificatie2");
    }

    public static Geoobject getGeoobjectRandomSampleGenerator() {
        return new Geoobject()
            .id(longCount.incrementAndGet())
            .datumbegingeldigheid(UUID.randomUUID().toString())
            .datumeindegeldigheid(UUID.randomUUID().toString())
            .geometriesoort(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString());
    }
}

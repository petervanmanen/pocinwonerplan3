package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NummeraanduidingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Nummeraanduiding getNummeraanduidingSample1() {
        return new Nummeraanduiding()
            .id(1L)
            .geometrie("geometrie1")
            .huisletter("huisletter1")
            .huisnummer("huisnummer1")
            .huisnummertoevoeging("huisnummertoevoeging1")
            .identificatie("identificatie1")
            .postcode("postcode1")
            .status("status1")
            .typeadresseerbaarobject("typeadresseerbaarobject1")
            .versie("versie1");
    }

    public static Nummeraanduiding getNummeraanduidingSample2() {
        return new Nummeraanduiding()
            .id(2L)
            .geometrie("geometrie2")
            .huisletter("huisletter2")
            .huisnummer("huisnummer2")
            .huisnummertoevoeging("huisnummertoevoeging2")
            .identificatie("identificatie2")
            .postcode("postcode2")
            .status("status2")
            .typeadresseerbaarobject("typeadresseerbaarobject2")
            .versie("versie2");
    }

    public static Nummeraanduiding getNummeraanduidingRandomSampleGenerator() {
        return new Nummeraanduiding()
            .id(longCount.incrementAndGet())
            .geometrie(UUID.randomUUID().toString())
            .huisletter(UUID.randomUUID().toString())
            .huisnummer(UUID.randomUUID().toString())
            .huisnummertoevoeging(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .postcode(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .typeadresseerbaarobject(UUID.randomUUID().toString())
            .versie(UUID.randomUUID().toString());
    }
}

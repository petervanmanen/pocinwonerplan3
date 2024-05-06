package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WozobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Wozobject getWozobjectSample1() {
        return new Wozobject()
            .id(1L)
            .empty("empty1")
            .gebruikscode("gebruikscode1")
            .geometriewozobject("geometriewozobject1")
            .grondoppervlakte("grondoppervlakte1")
            .soortobjectcode("soortobjectcode1")
            .statuswozobject("statuswozobject1")
            .vastgesteldewaarde("vastgesteldewaarde1")
            .wozobjectnummer("wozobjectnummer1");
    }

    public static Wozobject getWozobjectSample2() {
        return new Wozobject()
            .id(2L)
            .empty("empty2")
            .gebruikscode("gebruikscode2")
            .geometriewozobject("geometriewozobject2")
            .grondoppervlakte("grondoppervlakte2")
            .soortobjectcode("soortobjectcode2")
            .statuswozobject("statuswozobject2")
            .vastgesteldewaarde("vastgesteldewaarde2")
            .wozobjectnummer("wozobjectnummer2");
    }

    public static Wozobject getWozobjectRandomSampleGenerator() {
        return new Wozobject()
            .id(longCount.incrementAndGet())
            .empty(UUID.randomUUID().toString())
            .gebruikscode(UUID.randomUUID().toString())
            .geometriewozobject(UUID.randomUUID().toString())
            .grondoppervlakte(UUID.randomUUID().toString())
            .soortobjectcode(UUID.randomUUID().toString())
            .statuswozobject(UUID.randomUUID().toString())
            .vastgesteldewaarde(UUID.randomUUID().toString())
            .wozobjectnummer(UUID.randomUUID().toString());
    }
}

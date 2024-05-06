package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocatieaanduidingwozobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Locatieaanduidingwozobject getLocatieaanduidingwozobjectSample1() {
        return new Locatieaanduidingwozobject().id(1L).locatieomschrijving("locatieomschrijving1").primair("primair1");
    }

    public static Locatieaanduidingwozobject getLocatieaanduidingwozobjectSample2() {
        return new Locatieaanduidingwozobject().id(2L).locatieomschrijving("locatieomschrijving2").primair("primair2");
    }

    public static Locatieaanduidingwozobject getLocatieaanduidingwozobjectRandomSampleGenerator() {
        return new Locatieaanduidingwozobject()
            .id(longCount.incrementAndGet())
            .locatieomschrijving(UUID.randomUUID().toString())
            .primair(UUID.randomUUID().toString());
    }
}

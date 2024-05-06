package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocatieaanduidingadreswozobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Locatieaanduidingadreswozobject getLocatieaanduidingadreswozobjectSample1() {
        return new Locatieaanduidingadreswozobject().id(1L).locatieomschrijving("locatieomschrijving1");
    }

    public static Locatieaanduidingadreswozobject getLocatieaanduidingadreswozobjectSample2() {
        return new Locatieaanduidingadreswozobject().id(2L).locatieomschrijving("locatieomschrijving2");
    }

    public static Locatieaanduidingadreswozobject getLocatieaanduidingadreswozobjectRandomSampleGenerator() {
        return new Locatieaanduidingadreswozobject().id(longCount.incrementAndGet()).locatieomschrijving(UUID.randomUUID().toString());
    }
}

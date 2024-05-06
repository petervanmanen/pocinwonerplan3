package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EnkelvoudigdocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Enkelvoudigdocument getEnkelvoudigdocumentSample1() {
        return new Enkelvoudigdocument()
            .id(1L)
            .bestandsnaam("bestandsnaam1")
            .documentformaat("documentformaat1")
            .documentinhoud("documentinhoud1")
            .documentlink("documentlink1")
            .documentstatus("documentstatus1")
            .documenttaal("documenttaal1")
            .documentversie("documentversie1");
    }

    public static Enkelvoudigdocument getEnkelvoudigdocumentSample2() {
        return new Enkelvoudigdocument()
            .id(2L)
            .bestandsnaam("bestandsnaam2")
            .documentformaat("documentformaat2")
            .documentinhoud("documentinhoud2")
            .documentlink("documentlink2")
            .documentstatus("documentstatus2")
            .documenttaal("documenttaal2")
            .documentversie("documentversie2");
    }

    public static Enkelvoudigdocument getEnkelvoudigdocumentRandomSampleGenerator() {
        return new Enkelvoudigdocument()
            .id(longCount.incrementAndGet())
            .bestandsnaam(UUID.randomUUID().toString())
            .documentformaat(UUID.randomUUID().toString())
            .documentinhoud(UUID.randomUUID().toString())
            .documentlink(UUID.randomUUID().toString())
            .documentstatus(UUID.randomUUID().toString())
            .documenttaal(UUID.randomUUID().toString())
            .documentversie(UUID.randomUUID().toString());
    }
}

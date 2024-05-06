package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EcoductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ecoduct getEcoductSample1() {
        return new Ecoduct()
            .id(1L)
            .aantaloverspanningen("aantaloverspanningen1")
            .draagvermogen("draagvermogen1")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht1")
            .maximaleasbelasting("maximaleasbelasting1")
            .maximaleoverspanning("maximaleoverspanning1")
            .overbruggingsobjectdoorrijopening("overbruggingsobjectdoorrijopening1")
            .type("type1")
            .zwaarstevoertuig("zwaarstevoertuig1");
    }

    public static Ecoduct getEcoductSample2() {
        return new Ecoduct()
            .id(2L)
            .aantaloverspanningen("aantaloverspanningen2")
            .draagvermogen("draagvermogen2")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht2")
            .maximaleasbelasting("maximaleasbelasting2")
            .maximaleoverspanning("maximaleoverspanning2")
            .overbruggingsobjectdoorrijopening("overbruggingsobjectdoorrijopening2")
            .type("type2")
            .zwaarstevoertuig("zwaarstevoertuig2");
    }

    public static Ecoduct getEcoductRandomSampleGenerator() {
        return new Ecoduct()
            .id(longCount.incrementAndGet())
            .aantaloverspanningen(UUID.randomUUID().toString())
            .draagvermogen(UUID.randomUUID().toString())
            .maximaaltoelaatbaarvoertuiggewicht(UUID.randomUUID().toString())
            .maximaleasbelasting(UUID.randomUUID().toString())
            .maximaleoverspanning(UUID.randomUUID().toString())
            .overbruggingsobjectdoorrijopening(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .zwaarstevoertuig(UUID.randomUUID().toString());
    }
}

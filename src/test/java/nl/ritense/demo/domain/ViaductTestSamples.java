package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ViaductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Viaduct getViaductSample1() {
        return new Viaduct()
            .id(1L)
            .aantaloverspanningen("aantaloverspanningen1")
            .belastingklassenieuw("belastingklassenieuw1")
            .belastingklasseoud("belastingklasseoud1")
            .draagvermogen("draagvermogen1")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht1")
            .maximaleasbelasting("maximaleasbelasting1")
            .maximaleoverspanning("maximaleoverspanning1")
            .overbruggingsobjectdoorrijopening("overbruggingsobjectdoorrijopening1")
            .type("type1")
            .waterobject("waterobject1")
            .zwaarstevoertuig("zwaarstevoertuig1");
    }

    public static Viaduct getViaductSample2() {
        return new Viaduct()
            .id(2L)
            .aantaloverspanningen("aantaloverspanningen2")
            .belastingklassenieuw("belastingklassenieuw2")
            .belastingklasseoud("belastingklasseoud2")
            .draagvermogen("draagvermogen2")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht2")
            .maximaleasbelasting("maximaleasbelasting2")
            .maximaleoverspanning("maximaleoverspanning2")
            .overbruggingsobjectdoorrijopening("overbruggingsobjectdoorrijopening2")
            .type("type2")
            .waterobject("waterobject2")
            .zwaarstevoertuig("zwaarstevoertuig2");
    }

    public static Viaduct getViaductRandomSampleGenerator() {
        return new Viaduct()
            .id(longCount.incrementAndGet())
            .aantaloverspanningen(UUID.randomUUID().toString())
            .belastingklassenieuw(UUID.randomUUID().toString())
            .belastingklasseoud(UUID.randomUUID().toString())
            .draagvermogen(UUID.randomUUID().toString())
            .maximaaltoelaatbaarvoertuiggewicht(UUID.randomUUID().toString())
            .maximaleasbelasting(UUID.randomUUID().toString())
            .maximaleoverspanning(UUID.randomUUID().toString())
            .overbruggingsobjectdoorrijopening(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .waterobject(UUID.randomUUID().toString())
            .zwaarstevoertuig(UUID.randomUUID().toString());
    }
}

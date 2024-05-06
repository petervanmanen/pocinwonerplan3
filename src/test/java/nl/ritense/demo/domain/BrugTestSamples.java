package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BrugTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Brug getBrugSample1() {
        return new Brug()
            .id(1L)
            .aantaloverspanningen("aantaloverspanningen1")
            .bedienaar("bedienaar1")
            .bedieningstijden("bedieningstijden1")
            .belastingklassenieuw("belastingklassenieuw1")
            .belastingklasseoud("belastingklasseoud1")
            .doorrijbreedte("doorrijbreedte1")
            .draagvermogen("draagvermogen1")
            .hoofdroute("hoofdroute1")
            .hoofdvaarroute("hoofdvaarroute1")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht1")
            .maximaleasbelasting("maximaleasbelasting1")
            .maximaleoverspanning("maximaleoverspanning1")
            .statischmoment("statischmoment1")
            .type("type1")
            .typeplus("typeplus1")
            .zwaarstevoertuig("zwaarstevoertuig1");
    }

    public static Brug getBrugSample2() {
        return new Brug()
            .id(2L)
            .aantaloverspanningen("aantaloverspanningen2")
            .bedienaar("bedienaar2")
            .bedieningstijden("bedieningstijden2")
            .belastingklassenieuw("belastingklassenieuw2")
            .belastingklasseoud("belastingklasseoud2")
            .doorrijbreedte("doorrijbreedte2")
            .draagvermogen("draagvermogen2")
            .hoofdroute("hoofdroute2")
            .hoofdvaarroute("hoofdvaarroute2")
            .maximaaltoelaatbaarvoertuiggewicht("maximaaltoelaatbaarvoertuiggewicht2")
            .maximaleasbelasting("maximaleasbelasting2")
            .maximaleoverspanning("maximaleoverspanning2")
            .statischmoment("statischmoment2")
            .type("type2")
            .typeplus("typeplus2")
            .zwaarstevoertuig("zwaarstevoertuig2");
    }

    public static Brug getBrugRandomSampleGenerator() {
        return new Brug()
            .id(longCount.incrementAndGet())
            .aantaloverspanningen(UUID.randomUUID().toString())
            .bedienaar(UUID.randomUUID().toString())
            .bedieningstijden(UUID.randomUUID().toString())
            .belastingklassenieuw(UUID.randomUUID().toString())
            .belastingklasseoud(UUID.randomUUID().toString())
            .doorrijbreedte(UUID.randomUUID().toString())
            .draagvermogen(UUID.randomUUID().toString())
            .hoofdroute(UUID.randomUUID().toString())
            .hoofdvaarroute(UUID.randomUUID().toString())
            .maximaaltoelaatbaarvoertuiggewicht(UUID.randomUUID().toString())
            .maximaleasbelasting(UUID.randomUUID().toString())
            .maximaleoverspanning(UUID.randomUUID().toString())
            .statischmoment(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .zwaarstevoertuig(UUID.randomUUID().toString());
    }
}

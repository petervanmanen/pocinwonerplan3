package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TerreindeelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Terreindeel getTerreindeelSample1() {
        return new Terreindeel()
            .id(1L)
            .breedte("breedte1")
            .cultuurhistorischwaardevol("cultuurhistorischwaardevol1")
            .oppervlakte("oppervlakte1")
            .optalud("optalud1")
            .percentageloofbos("percentageloofbos1")
            .terreindeelsoortnaam("terreindeelsoortnaam1")
            .type("type1")
            .typebewerking("typebewerking1")
            .typeplus("typeplus1")
            .typeplus2("typeplus21");
    }

    public static Terreindeel getTerreindeelSample2() {
        return new Terreindeel()
            .id(2L)
            .breedte("breedte2")
            .cultuurhistorischwaardevol("cultuurhistorischwaardevol2")
            .oppervlakte("oppervlakte2")
            .optalud("optalud2")
            .percentageloofbos("percentageloofbos2")
            .terreindeelsoortnaam("terreindeelsoortnaam2")
            .type("type2")
            .typebewerking("typebewerking2")
            .typeplus("typeplus2")
            .typeplus2("typeplus22");
    }

    public static Terreindeel getTerreindeelRandomSampleGenerator() {
        return new Terreindeel()
            .id(longCount.incrementAndGet())
            .breedte(UUID.randomUUID().toString())
            .cultuurhistorischwaardevol(UUID.randomUUID().toString())
            .oppervlakte(UUID.randomUUID().toString())
            .optalud(UUID.randomUUID().toString())
            .percentageloofbos(UUID.randomUUID().toString())
            .terreindeelsoortnaam(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typebewerking(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .typeplus2(UUID.randomUUID().toString());
    }
}

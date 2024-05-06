package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanbestedingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanbesteding getAanbestedingSample1() {
        return new Aanbesteding()
            .id(1L)
            .datumpublicatie("datumpublicatie1")
            .digitaal("digitaal1")
            .naam("naam1")
            .procedure("procedure1")
            .referentienummer("referentienummer1")
            .scoremaximaal("scoremaximaal1")
            .status("status1")
            .tendernedkenmerk("tendernedkenmerk1")
            .type("type1")
            .volgendesluiting("volgendesluiting1");
    }

    public static Aanbesteding getAanbestedingSample2() {
        return new Aanbesteding()
            .id(2L)
            .datumpublicatie("datumpublicatie2")
            .digitaal("digitaal2")
            .naam("naam2")
            .procedure("procedure2")
            .referentienummer("referentienummer2")
            .scoremaximaal("scoremaximaal2")
            .status("status2")
            .tendernedkenmerk("tendernedkenmerk2")
            .type("type2")
            .volgendesluiting("volgendesluiting2");
    }

    public static Aanbesteding getAanbestedingRandomSampleGenerator() {
        return new Aanbesteding()
            .id(longCount.incrementAndGet())
            .datumpublicatie(UUID.randomUUID().toString())
            .digitaal(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .procedure(UUID.randomUUID().toString())
            .referentienummer(UUID.randomUUID().toString())
            .scoremaximaal(UUID.randomUUID().toString())
            .status(UUID.randomUUID().toString())
            .tendernedkenmerk(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .volgendesluiting(UUID.randomUUID().toString());
    }
}

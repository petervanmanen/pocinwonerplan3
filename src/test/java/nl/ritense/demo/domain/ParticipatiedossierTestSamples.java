package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ParticipatiedossierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Participatiedossier getParticipatiedossierSample1() {
        return new Participatiedossier()
            .id(1L)
            .arbeidsvermogen("arbeidsvermogen1")
            .begeleiderscode("begeleiderscode1")
            .beschutwerk("beschutwerk1")
            .clientbegeleiderid("clientbegeleiderid1")
            .datumeindebegeleiding("datumeindebegeleiding1")
            .datumstartbegeleiding("datumstartbegeleiding1")
            .indicatiedoelgroepregister("indicatiedoelgroepregister1");
    }

    public static Participatiedossier getParticipatiedossierSample2() {
        return new Participatiedossier()
            .id(2L)
            .arbeidsvermogen("arbeidsvermogen2")
            .begeleiderscode("begeleiderscode2")
            .beschutwerk("beschutwerk2")
            .clientbegeleiderid("clientbegeleiderid2")
            .datumeindebegeleiding("datumeindebegeleiding2")
            .datumstartbegeleiding("datumstartbegeleiding2")
            .indicatiedoelgroepregister("indicatiedoelgroepregister2");
    }

    public static Participatiedossier getParticipatiedossierRandomSampleGenerator() {
        return new Participatiedossier()
            .id(longCount.incrementAndGet())
            .arbeidsvermogen(UUID.randomUUID().toString())
            .begeleiderscode(UUID.randomUUID().toString())
            .beschutwerk(UUID.randomUUID().toString())
            .clientbegeleiderid(UUID.randomUUID().toString())
            .datumeindebegeleiding(UUID.randomUUID().toString())
            .datumstartbegeleiding(UUID.randomUUID().toString())
            .indicatiedoelgroepregister(UUID.randomUUID().toString());
    }
}

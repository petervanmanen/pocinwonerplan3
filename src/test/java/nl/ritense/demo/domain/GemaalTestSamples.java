package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GemaalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Gemaal getGemaalSample1() {
        return new Gemaal()
            .id(1L)
            .aantalbedrijfsaansluitingen("aantalbedrijfsaansluitingen1")
            .aantalhuisaansluitingen("aantalhuisaansluitingen1")
            .aantalpompen("aantalpompen1")
            .bedienaar("bedienaar1")
            .effectievegemaalcapaciteit("effectievegemaalcapaciteit1")
            .type("type1");
    }

    public static Gemaal getGemaalSample2() {
        return new Gemaal()
            .id(2L)
            .aantalbedrijfsaansluitingen("aantalbedrijfsaansluitingen2")
            .aantalhuisaansluitingen("aantalhuisaansluitingen2")
            .aantalpompen("aantalpompen2")
            .bedienaar("bedienaar2")
            .effectievegemaalcapaciteit("effectievegemaalcapaciteit2")
            .type("type2");
    }

    public static Gemaal getGemaalRandomSampleGenerator() {
        return new Gemaal()
            .id(longCount.incrementAndGet())
            .aantalbedrijfsaansluitingen(UUID.randomUUID().toString())
            .aantalhuisaansluitingen(UUID.randomUUID().toString())
            .aantalpompen(UUID.randomUUID().toString())
            .bedienaar(UUID.randomUUID().toString())
            .effectievegemaalcapaciteit(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString());
    }
}

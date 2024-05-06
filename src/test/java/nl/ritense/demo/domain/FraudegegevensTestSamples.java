package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FraudegegevensTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fraudegegevens getFraudegegevensSample1() {
        return new Fraudegegevens()
            .id(1L)
            .bedragfraude("bedragfraude1")
            .datumeindefraude("datumeindefraude1")
            .datumgeconstateerd("datumgeconstateerd1")
            .datumstartfraude("datumstartfraude1")
            .verrekening("verrekening1")
            .vorderingen("vorderingen1");
    }

    public static Fraudegegevens getFraudegegevensSample2() {
        return new Fraudegegevens()
            .id(2L)
            .bedragfraude("bedragfraude2")
            .datumeindefraude("datumeindefraude2")
            .datumgeconstateerd("datumgeconstateerd2")
            .datumstartfraude("datumstartfraude2")
            .verrekening("verrekening2")
            .vorderingen("vorderingen2");
    }

    public static Fraudegegevens getFraudegegevensRandomSampleGenerator() {
        return new Fraudegegevens()
            .id(longCount.incrementAndGet())
            .bedragfraude(UUID.randomUUID().toString())
            .datumeindefraude(UUID.randomUUID().toString())
            .datumgeconstateerd(UUID.randomUUID().toString())
            .datumstartfraude(UUID.randomUUID().toString())
            .verrekening(UUID.randomUUID().toString())
            .vorderingen(UUID.randomUUID().toString());
    }
}

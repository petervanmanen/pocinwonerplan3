package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanvraagofmeldingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanvraagofmelding getAanvraagofmeldingSample1() {
        return new Aanvraagofmelding().id(1L).opmerkingen("opmerkingen1").reden("reden1").soortverzuimofaanvraag("soortverzuimofaanvraag1");
    }

    public static Aanvraagofmelding getAanvraagofmeldingSample2() {
        return new Aanvraagofmelding().id(2L).opmerkingen("opmerkingen2").reden("reden2").soortverzuimofaanvraag("soortverzuimofaanvraag2");
    }

    public static Aanvraagofmelding getAanvraagofmeldingRandomSampleGenerator() {
        return new Aanvraagofmelding()
            .id(longCount.incrementAndGet())
            .opmerkingen(UUID.randomUUID().toString())
            .reden(UUID.randomUUID().toString())
            .soortverzuimofaanvraag(UUID.randomUUID().toString());
    }
}

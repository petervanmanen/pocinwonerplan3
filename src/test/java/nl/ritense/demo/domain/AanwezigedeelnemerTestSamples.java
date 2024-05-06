package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AanwezigedeelnemerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aanwezigedeelnemer getAanwezigedeelnemerSample1() {
        return new Aanwezigedeelnemer()
            .id(1L)
            .aanvangaanwezigheid("aanvangaanwezigheid1")
            .eindeaanwezigheid("eindeaanwezigheid1")
            .naam("naam1")
            .rol("rol1")
            .vertegenwoordigtorganisatie("vertegenwoordigtorganisatie1");
    }

    public static Aanwezigedeelnemer getAanwezigedeelnemerSample2() {
        return new Aanwezigedeelnemer()
            .id(2L)
            .aanvangaanwezigheid("aanvangaanwezigheid2")
            .eindeaanwezigheid("eindeaanwezigheid2")
            .naam("naam2")
            .rol("rol2")
            .vertegenwoordigtorganisatie("vertegenwoordigtorganisatie2");
    }

    public static Aanwezigedeelnemer getAanwezigedeelnemerRandomSampleGenerator() {
        return new Aanwezigedeelnemer()
            .id(longCount.incrementAndGet())
            .aanvangaanwezigheid(UUID.randomUUID().toString())
            .eindeaanwezigheid(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .rol(UUID.randomUUID().toString())
            .vertegenwoordigtorganisatie(UUID.randomUUID().toString());
    }
}

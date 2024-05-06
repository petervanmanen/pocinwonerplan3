package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AomaanvraagwmojeugdTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Aomaanvraagwmojeugd getAomaanvraagwmojeugdSample1() {
        return new Aomaanvraagwmojeugd()
            .id(1L)
            .clientreactie("clientreactie1")
            .deskundigheid("deskundigheid1")
            .doorloopmethodiek("doorloopmethodiek1")
            .maximaledoorlooptijd("maximaledoorlooptijd1")
            .redenafsluiting("redenafsluiting1");
    }

    public static Aomaanvraagwmojeugd getAomaanvraagwmojeugdSample2() {
        return new Aomaanvraagwmojeugd()
            .id(2L)
            .clientreactie("clientreactie2")
            .deskundigheid("deskundigheid2")
            .doorloopmethodiek("doorloopmethodiek2")
            .maximaledoorlooptijd("maximaledoorlooptijd2")
            .redenafsluiting("redenafsluiting2");
    }

    public static Aomaanvraagwmojeugd getAomaanvraagwmojeugdRandomSampleGenerator() {
        return new Aomaanvraagwmojeugd()
            .id(longCount.incrementAndGet())
            .clientreactie(UUID.randomUUID().toString())
            .deskundigheid(UUID.randomUUID().toString())
            .doorloopmethodiek(UUID.randomUUID().toString())
            .maximaledoorlooptijd(UUID.randomUUID().toString())
            .redenafsluiting(UUID.randomUUID().toString());
    }
}

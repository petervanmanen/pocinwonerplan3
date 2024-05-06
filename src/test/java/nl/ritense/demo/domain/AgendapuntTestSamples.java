package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgendapuntTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agendapunt getAgendapuntSample1() {
        return new Agendapunt().id(1L).nummer("nummer1").omschrijving("omschrijving1").titel("titel1");
    }

    public static Agendapunt getAgendapuntSample2() {
        return new Agendapunt().id(2L).nummer("nummer2").omschrijving("omschrijving2").titel("titel2");
    }

    public static Agendapunt getAgendapuntRandomSampleGenerator() {
        return new Agendapunt()
            .id(longCount.incrementAndGet())
            .nummer(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .titel(UUID.randomUUID().toString());
    }
}

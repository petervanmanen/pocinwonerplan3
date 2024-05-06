package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SluitingofaangaanhuwelijkofgeregistreerdpartnerschapTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap getSluitingofaangaanhuwelijkofgeregistreerdpartnerschapSample1() {
        return new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap()
            .id(1L)
            .buitenlandseplaatsaanvang("buitenlandseplaatsaanvang1")
            .buitenlandseregioaanvang("buitenlandseregioaanvang1")
            .datumaanvang("datumaanvang1")
            .gemeenteaanvang("gemeenteaanvang1")
            .landofgebiedaanvang("landofgebiedaanvang1")
            .omschrijvinglocatieaanvang("omschrijvinglocatieaanvang1");
    }

    public static Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap getSluitingofaangaanhuwelijkofgeregistreerdpartnerschapSample2() {
        return new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap()
            .id(2L)
            .buitenlandseplaatsaanvang("buitenlandseplaatsaanvang2")
            .buitenlandseregioaanvang("buitenlandseregioaanvang2")
            .datumaanvang("datumaanvang2")
            .gemeenteaanvang("gemeenteaanvang2")
            .landofgebiedaanvang("landofgebiedaanvang2")
            .omschrijvinglocatieaanvang("omschrijvinglocatieaanvang2");
    }

    public static Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap getSluitingofaangaanhuwelijkofgeregistreerdpartnerschapRandomSampleGenerator() {
        return new Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap()
            .id(longCount.incrementAndGet())
            .buitenlandseplaatsaanvang(UUID.randomUUID().toString())
            .buitenlandseregioaanvang(UUID.randomUUID().toString())
            .datumaanvang(UUID.randomUUID().toString())
            .gemeenteaanvang(UUID.randomUUID().toString())
            .landofgebiedaanvang(UUID.randomUUID().toString())
            .omschrijvinglocatieaanvang(UUID.randomUUID().toString());
    }
}

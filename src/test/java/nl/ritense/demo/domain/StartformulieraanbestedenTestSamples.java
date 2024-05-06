package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StartformulieraanbestedenTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Startformulieraanbesteden getStartformulieraanbestedenSample1() {
        return new Startformulieraanbesteden()
            .id(1L)
            .beoogdelooptijd("beoogdelooptijd1")
            .indicatiebeoogdeaanbestedingonderhands("indicatiebeoogdeaanbestedingonderhands1")
            .indicatieeenmaligelos("indicatieeenmaligelos1")
            .indicatiemeerjarigrepeterend("indicatiemeerjarigrepeterend1")
            .indicatoroverkoepelendproject("indicatoroverkoepelendproject1")
            .omschrijving("omschrijving1")
            .opdrachtcategorie("opdrachtcategorie1")
            .opdrachtsoort("opdrachtsoort1")
            .toelichtingaanvullendeopdracht("toelichtingaanvullendeopdracht1")
            .toelichtingeenmaligofrepeterend("toelichtingeenmaligofrepeterend1");
    }

    public static Startformulieraanbesteden getStartformulieraanbestedenSample2() {
        return new Startformulieraanbesteden()
            .id(2L)
            .beoogdelooptijd("beoogdelooptijd2")
            .indicatiebeoogdeaanbestedingonderhands("indicatiebeoogdeaanbestedingonderhands2")
            .indicatieeenmaligelos("indicatieeenmaligelos2")
            .indicatiemeerjarigrepeterend("indicatiemeerjarigrepeterend2")
            .indicatoroverkoepelendproject("indicatoroverkoepelendproject2")
            .omschrijving("omschrijving2")
            .opdrachtcategorie("opdrachtcategorie2")
            .opdrachtsoort("opdrachtsoort2")
            .toelichtingaanvullendeopdracht("toelichtingaanvullendeopdracht2")
            .toelichtingeenmaligofrepeterend("toelichtingeenmaligofrepeterend2");
    }

    public static Startformulieraanbesteden getStartformulieraanbestedenRandomSampleGenerator() {
        return new Startformulieraanbesteden()
            .id(longCount.incrementAndGet())
            .beoogdelooptijd(UUID.randomUUID().toString())
            .indicatiebeoogdeaanbestedingonderhands(UUID.randomUUID().toString())
            .indicatieeenmaligelos(UUID.randomUUID().toString())
            .indicatiemeerjarigrepeterend(UUID.randomUUID().toString())
            .indicatoroverkoepelendproject(UUID.randomUUID().toString())
            .omschrijving(UUID.randomUUID().toString())
            .opdrachtcategorie(UUID.randomUUID().toString())
            .opdrachtsoort(UUID.randomUUID().toString())
            .toelichtingaanvullendeopdracht(UUID.randomUUID().toString())
            .toelichtingeenmaligofrepeterend(UUID.randomUUID().toString());
    }
}

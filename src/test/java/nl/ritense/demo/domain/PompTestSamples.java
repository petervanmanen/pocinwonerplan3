package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PompTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pomp getPompSample1() {
        return new Pomp()
            .id(1L)
            .aanslagniveau("aanslagniveau1")
            .beginstanddraaiurenteller("beginstanddraaiurenteller1")
            .besturingskast("besturingskast1")
            .laatstestanddraaiurenteller("laatstestanddraaiurenteller1")
            .laatstestandkwhteller("laatstestandkwhteller1")
            .levensduur("levensduur1")
            .model("model1")
            .motorvermogen("motorvermogen1")
            .onderdeelmetpomp("onderdeelmetpomp1")
            .ontwerpcapaciteit("ontwerpcapaciteit1")
            .pompcapaciteit("pompcapaciteit1")
            .serienummer("serienummer1")
            .type("type1")
            .typeonderdeelmetpomp("typeonderdeelmetpomp1")
            .typeplus("typeplus1")
            .typewaaier("typewaaier1")
            .uitslagpeil("uitslagpeil1");
    }

    public static Pomp getPompSample2() {
        return new Pomp()
            .id(2L)
            .aanslagniveau("aanslagniveau2")
            .beginstanddraaiurenteller("beginstanddraaiurenteller2")
            .besturingskast("besturingskast2")
            .laatstestanddraaiurenteller("laatstestanddraaiurenteller2")
            .laatstestandkwhteller("laatstestandkwhteller2")
            .levensduur("levensduur2")
            .model("model2")
            .motorvermogen("motorvermogen2")
            .onderdeelmetpomp("onderdeelmetpomp2")
            .ontwerpcapaciteit("ontwerpcapaciteit2")
            .pompcapaciteit("pompcapaciteit2")
            .serienummer("serienummer2")
            .type("type2")
            .typeonderdeelmetpomp("typeonderdeelmetpomp2")
            .typeplus("typeplus2")
            .typewaaier("typewaaier2")
            .uitslagpeil("uitslagpeil2");
    }

    public static Pomp getPompRandomSampleGenerator() {
        return new Pomp()
            .id(longCount.incrementAndGet())
            .aanslagniveau(UUID.randomUUID().toString())
            .beginstanddraaiurenteller(UUID.randomUUID().toString())
            .besturingskast(UUID.randomUUID().toString())
            .laatstestanddraaiurenteller(UUID.randomUUID().toString())
            .laatstestandkwhteller(UUID.randomUUID().toString())
            .levensduur(UUID.randomUUID().toString())
            .model(UUID.randomUUID().toString())
            .motorvermogen(UUID.randomUUID().toString())
            .onderdeelmetpomp(UUID.randomUUID().toString())
            .ontwerpcapaciteit(UUID.randomUUID().toString())
            .pompcapaciteit(UUID.randomUUID().toString())
            .serienummer(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .typeonderdeelmetpomp(UUID.randomUUID().toString())
            .typeplus(UUID.randomUUID().toString())
            .typewaaier(UUID.randomUUID().toString())
            .uitslagpeil(UUID.randomUUID().toString());
    }
}

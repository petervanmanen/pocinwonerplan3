package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArtefactTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Artefact getArtefactSample1() {
        return new Artefact()
            .id(1L)
            .artefectnummer("artefectnummer1")
            .beschrijving("beschrijving1")
            .datering("datering1")
            .dateringcomplex("dateringcomplex1")
            .determinatieniveau("determinatieniveau1")
            .dianummer("dianummer1")
            .doosnummer("doosnummer1")
            .fotonummer("fotonummer1")
            .functie("functie1")
            .herkomst("herkomst1")
            .key("key1")
            .keydoos("keydoos1")
            .keymagazijnplaatsing("keymagazijnplaatsing1")
            .keyput("keyput1")
            .keyvondst("keyvondst1")
            .literatuur("literatuur1")
            .maten("maten1")
            .naam("naam1")
            .opmerkingen("opmerkingen1")
            .origine("origine1")
            .projectcd("projectcd1")
            .putnummer("putnummer1")
            .tekeningnummer("tekeningnummer1")
            .type("type1")
            .vondstnummer("vondstnummer1");
    }

    public static Artefact getArtefactSample2() {
        return new Artefact()
            .id(2L)
            .artefectnummer("artefectnummer2")
            .beschrijving("beschrijving2")
            .datering("datering2")
            .dateringcomplex("dateringcomplex2")
            .determinatieniveau("determinatieniveau2")
            .dianummer("dianummer2")
            .doosnummer("doosnummer2")
            .fotonummer("fotonummer2")
            .functie("functie2")
            .herkomst("herkomst2")
            .key("key2")
            .keydoos("keydoos2")
            .keymagazijnplaatsing("keymagazijnplaatsing2")
            .keyput("keyput2")
            .keyvondst("keyvondst2")
            .literatuur("literatuur2")
            .maten("maten2")
            .naam("naam2")
            .opmerkingen("opmerkingen2")
            .origine("origine2")
            .projectcd("projectcd2")
            .putnummer("putnummer2")
            .tekeningnummer("tekeningnummer2")
            .type("type2")
            .vondstnummer("vondstnummer2");
    }

    public static Artefact getArtefactRandomSampleGenerator() {
        return new Artefact()
            .id(longCount.incrementAndGet())
            .artefectnummer(UUID.randomUUID().toString())
            .beschrijving(UUID.randomUUID().toString())
            .datering(UUID.randomUUID().toString())
            .dateringcomplex(UUID.randomUUID().toString())
            .determinatieniveau(UUID.randomUUID().toString())
            .dianummer(UUID.randomUUID().toString())
            .doosnummer(UUID.randomUUID().toString())
            .fotonummer(UUID.randomUUID().toString())
            .functie(UUID.randomUUID().toString())
            .herkomst(UUID.randomUUID().toString())
            .key(UUID.randomUUID().toString())
            .keydoos(UUID.randomUUID().toString())
            .keymagazijnplaatsing(UUID.randomUUID().toString())
            .keyput(UUID.randomUUID().toString())
            .keyvondst(UUID.randomUUID().toString())
            .literatuur(UUID.randomUUID().toString())
            .maten(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .opmerkingen(UUID.randomUUID().toString())
            .origine(UUID.randomUUID().toString())
            .projectcd(UUID.randomUUID().toString())
            .putnummer(UUID.randomUUID().toString())
            .tekeningnummer(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .vondstnummer(UUID.randomUUID().toString());
    }
}

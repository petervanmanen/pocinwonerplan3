package nl.ritense.demo.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EobjectTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Eobject getEobjectSample1() {
        return new Eobject()
            .id(1L)
            .adresbinnenland("adresbinnenland1")
            .adresbuitenland("adresbuitenland1")
            .domein("domein1")
            .geometrie("geometrie1")
            .identificatie("identificatie1")
            .indicatierisico("indicatierisico1")
            .kadastraleaanduiding("kadastraleaanduiding1")
            .naam("naam1")
            .eobjecttype("eobjecttype1")
            .toelichting("toelichting1");
    }

    public static Eobject getEobjectSample2() {
        return new Eobject()
            .id(2L)
            .adresbinnenland("adresbinnenland2")
            .adresbuitenland("adresbuitenland2")
            .domein("domein2")
            .geometrie("geometrie2")
            .identificatie("identificatie2")
            .indicatierisico("indicatierisico2")
            .kadastraleaanduiding("kadastraleaanduiding2")
            .naam("naam2")
            .eobjecttype("eobjecttype2")
            .toelichting("toelichting2");
    }

    public static Eobject getEobjectRandomSampleGenerator() {
        return new Eobject()
            .id(longCount.incrementAndGet())
            .adresbinnenland(UUID.randomUUID().toString())
            .adresbuitenland(UUID.randomUUID().toString())
            .domein(UUID.randomUUID().toString())
            .geometrie(UUID.randomUUID().toString())
            .identificatie(UUID.randomUUID().toString())
            .indicatierisico(UUID.randomUUID().toString())
            .kadastraleaanduiding(UUID.randomUUID().toString())
            .naam(UUID.randomUUID().toString())
            .eobjecttype(UUID.randomUUID().toString())
            .toelichting(UUID.randomUUID().toString());
    }
}

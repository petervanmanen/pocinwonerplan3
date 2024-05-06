package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AdresaanduidingTestSamples.*;
import static nl.ritense.demo.domain.BriefadresTestSamples.*;
import static nl.ritense.demo.domain.BuurtTestSamples.*;
import static nl.ritense.demo.domain.GebiedTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static nl.ritense.demo.domain.VestigingTestSamples.*;
import static nl.ritense.demo.domain.WoonplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NummeraanduidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nummeraanduiding.class);
        Nummeraanduiding nummeraanduiding1 = getNummeraanduidingSample1();
        Nummeraanduiding nummeraanduiding2 = new Nummeraanduiding();
        assertThat(nummeraanduiding1).isNotEqualTo(nummeraanduiding2);

        nummeraanduiding2.setId(nummeraanduiding1.getId());
        assertThat(nummeraanduiding1).isEqualTo(nummeraanduiding2);

        nummeraanduiding2 = getNummeraanduidingSample2();
        assertThat(nummeraanduiding1).isNotEqualTo(nummeraanduiding2);
    }

    @Test
    void ligtinWoonplaatsTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Woonplaats woonplaatsBack = getWoonplaatsRandomSampleGenerator();

        nummeraanduiding.setLigtinWoonplaats(woonplaatsBack);
        assertThat(nummeraanduiding.getLigtinWoonplaats()).isEqualTo(woonplaatsBack);

        nummeraanduiding.ligtinWoonplaats(null);
        assertThat(nummeraanduiding.getLigtinWoonplaats()).isNull();
    }

    @Test
    void ligtinBuurtTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Buurt buurtBack = getBuurtRandomSampleGenerator();

        nummeraanduiding.setLigtinBuurt(buurtBack);
        assertThat(nummeraanduiding.getLigtinBuurt()).isEqualTo(buurtBack);

        nummeraanduiding.ligtinBuurt(null);
        assertThat(nummeraanduiding.getLigtinBuurt()).isNull();
    }

    @Test
    void ligtinGebiedTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Gebied gebiedBack = getGebiedRandomSampleGenerator();

        nummeraanduiding.addLigtinGebied(gebiedBack);
        assertThat(nummeraanduiding.getLigtinGebieds()).containsOnly(gebiedBack);

        nummeraanduiding.removeLigtinGebied(gebiedBack);
        assertThat(nummeraanduiding.getLigtinGebieds()).doesNotContain(gebiedBack);

        nummeraanduiding.ligtinGebieds(new HashSet<>(Set.of(gebiedBack)));
        assertThat(nummeraanduiding.getLigtinGebieds()).containsOnly(gebiedBack);

        nummeraanduiding.setLigtinGebieds(new HashSet<>());
        assertThat(nummeraanduiding.getLigtinGebieds()).doesNotContain(gebiedBack);
    }

    @Test
    void verwijstnaarAdresaanduidingTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Adresaanduiding adresaanduidingBack = getAdresaanduidingRandomSampleGenerator();

        nummeraanduiding.setVerwijstnaarAdresaanduiding(adresaanduidingBack);
        assertThat(nummeraanduiding.getVerwijstnaarAdresaanduiding()).isEqualTo(adresaanduidingBack);
        assertThat(adresaanduidingBack.getVerwijstnaarNummeraanduiding()).isEqualTo(nummeraanduiding);

        nummeraanduiding.verwijstnaarAdresaanduiding(null);
        assertThat(nummeraanduiding.getVerwijstnaarAdresaanduiding()).isNull();
        assertThat(adresaanduidingBack.getVerwijstnaarNummeraanduiding()).isNull();
    }

    @Test
    void emptyBriefadresTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Briefadres briefadresBack = getBriefadresRandomSampleGenerator();

        nummeraanduiding.addEmptyBriefadres(briefadresBack);
        assertThat(nummeraanduiding.getEmptyBriefadres()).containsOnly(briefadresBack);
        assertThat(briefadresBack.getEmptyNummeraanduiding()).isEqualTo(nummeraanduiding);

        nummeraanduiding.removeEmptyBriefadres(briefadresBack);
        assertThat(nummeraanduiding.getEmptyBriefadres()).doesNotContain(briefadresBack);
        assertThat(briefadresBack.getEmptyNummeraanduiding()).isNull();

        nummeraanduiding.emptyBriefadres(new HashSet<>(Set.of(briefadresBack)));
        assertThat(nummeraanduiding.getEmptyBriefadres()).containsOnly(briefadresBack);
        assertThat(briefadresBack.getEmptyNummeraanduiding()).isEqualTo(nummeraanduiding);

        nummeraanduiding.setEmptyBriefadres(new HashSet<>());
        assertThat(nummeraanduiding.getEmptyBriefadres()).doesNotContain(briefadresBack);
        assertThat(briefadresBack.getEmptyNummeraanduiding()).isNull();
    }

    @Test
    void heeftalslocatieadresVestigingTest() throws Exception {
        Nummeraanduiding nummeraanduiding = getNummeraanduidingRandomSampleGenerator();
        Vestiging vestigingBack = getVestigingRandomSampleGenerator();

        nummeraanduiding.addHeeftalslocatieadresVestiging(vestigingBack);
        assertThat(nummeraanduiding.getHeeftalslocatieadresVestigings()).containsOnly(vestigingBack);
        assertThat(vestigingBack.getHeeftalslocatieadresNummeraanduiding()).isEqualTo(nummeraanduiding);

        nummeraanduiding.removeHeeftalslocatieadresVestiging(vestigingBack);
        assertThat(nummeraanduiding.getHeeftalslocatieadresVestigings()).doesNotContain(vestigingBack);
        assertThat(vestigingBack.getHeeftalslocatieadresNummeraanduiding()).isNull();

        nummeraanduiding.heeftalslocatieadresVestigings(new HashSet<>(Set.of(vestigingBack)));
        assertThat(nummeraanduiding.getHeeftalslocatieadresVestigings()).containsOnly(vestigingBack);
        assertThat(vestigingBack.getHeeftalslocatieadresNummeraanduiding()).isEqualTo(nummeraanduiding);

        nummeraanduiding.setHeeftalslocatieadresVestigings(new HashSet<>());
        assertThat(nummeraanduiding.getHeeftalslocatieadresVestigings()).doesNotContain(vestigingBack);
        assertThat(vestigingBack.getHeeftalslocatieadresNummeraanduiding()).isNull();
    }
}

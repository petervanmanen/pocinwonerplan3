package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieregelTestSamples.*;
import static nl.ritense.demo.domain.ToewijzingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclaratieregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Declaratieregel.class);
        Declaratieregel declaratieregel1 = getDeclaratieregelSample1();
        Declaratieregel declaratieregel2 = new Declaratieregel();
        assertThat(declaratieregel1).isNotEqualTo(declaratieregel2);

        declaratieregel2.setId(declaratieregel1.getId());
        assertThat(declaratieregel1).isEqualTo(declaratieregel2);

        declaratieregel2 = getDeclaratieregelSample2();
        assertThat(declaratieregel1).isNotEqualTo(declaratieregel2);
    }

    @Test
    void isvoorBeschikkingTest() throws Exception {
        Declaratieregel declaratieregel = getDeclaratieregelRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        declaratieregel.setIsvoorBeschikking(beschikkingBack);
        assertThat(declaratieregel.getIsvoorBeschikking()).isEqualTo(beschikkingBack);

        declaratieregel.isvoorBeschikking(null);
        assertThat(declaratieregel.getIsvoorBeschikking()).isNull();
    }

    @Test
    void betreftClientTest() throws Exception {
        Declaratieregel declaratieregel = getDeclaratieregelRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        declaratieregel.setBetreftClient(clientBack);
        assertThat(declaratieregel.getBetreftClient()).isEqualTo(clientBack);

        declaratieregel.betreftClient(null);
        assertThat(declaratieregel.getBetreftClient()).isNull();
    }

    @Test
    void valtbinnenDeclaratieTest() throws Exception {
        Declaratieregel declaratieregel = getDeclaratieregelRandomSampleGenerator();
        Declaratie declaratieBack = getDeclaratieRandomSampleGenerator();

        declaratieregel.setValtbinnenDeclaratie(declaratieBack);
        assertThat(declaratieregel.getValtbinnenDeclaratie()).isEqualTo(declaratieBack);

        declaratieregel.valtbinnenDeclaratie(null);
        assertThat(declaratieregel.getValtbinnenDeclaratie()).isNull();
    }

    @Test
    void isopbasisvanToewijzingTest() throws Exception {
        Declaratieregel declaratieregel = getDeclaratieregelRandomSampleGenerator();
        Toewijzing toewijzingBack = getToewijzingRandomSampleGenerator();

        declaratieregel.setIsopbasisvanToewijzing(toewijzingBack);
        assertThat(declaratieregel.getIsopbasisvanToewijzing()).isEqualTo(toewijzingBack);

        declaratieregel.isopbasisvanToewijzing(null);
        assertThat(declaratieregel.getIsopbasisvanToewijzing()).isNull();
    }
}

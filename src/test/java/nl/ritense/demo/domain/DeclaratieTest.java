package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DeclaratieTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieregelTestSamples.*;
import static nl.ritense.demo.domain.DeclaratiesoortTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclaratieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Declaratie.class);
        Declaratie declaratie1 = getDeclaratieSample1();
        Declaratie declaratie2 = new Declaratie();
        assertThat(declaratie1).isNotEqualTo(declaratie2);

        declaratie2.setId(declaratie1.getId());
        assertThat(declaratie1).isEqualTo(declaratie2);

        declaratie2 = getDeclaratieSample2();
        assertThat(declaratie1).isNotEqualTo(declaratie2);
    }

    @Test
    void ingedienddoorLeverancierTest() throws Exception {
        Declaratie declaratie = getDeclaratieRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        declaratie.setIngedienddoorLeverancier(leverancierBack);
        assertThat(declaratie.getIngedienddoorLeverancier()).isEqualTo(leverancierBack);

        declaratie.ingedienddoorLeverancier(null);
        assertThat(declaratie.getIngedienddoorLeverancier()).isNull();
    }

    @Test
    void soortdeclaratieDeclaratiesoortTest() throws Exception {
        Declaratie declaratie = getDeclaratieRandomSampleGenerator();
        Declaratiesoort declaratiesoortBack = getDeclaratiesoortRandomSampleGenerator();

        declaratie.setSoortdeclaratieDeclaratiesoort(declaratiesoortBack);
        assertThat(declaratie.getSoortdeclaratieDeclaratiesoort()).isEqualTo(declaratiesoortBack);

        declaratie.soortdeclaratieDeclaratiesoort(null);
        assertThat(declaratie.getSoortdeclaratieDeclaratiesoort()).isNull();
    }

    @Test
    void dientinWerknemerTest() throws Exception {
        Declaratie declaratie = getDeclaratieRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        declaratie.setDientinWerknemer(werknemerBack);
        assertThat(declaratie.getDientinWerknemer()).isEqualTo(werknemerBack);

        declaratie.dientinWerknemer(null);
        assertThat(declaratie.getDientinWerknemer()).isNull();
    }

    @Test
    void valtbinnenDeclaratieregelTest() throws Exception {
        Declaratie declaratie = getDeclaratieRandomSampleGenerator();
        Declaratieregel declaratieregelBack = getDeclaratieregelRandomSampleGenerator();

        declaratie.addValtbinnenDeclaratieregel(declaratieregelBack);
        assertThat(declaratie.getValtbinnenDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getValtbinnenDeclaratie()).isEqualTo(declaratie);

        declaratie.removeValtbinnenDeclaratieregel(declaratieregelBack);
        assertThat(declaratie.getValtbinnenDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getValtbinnenDeclaratie()).isNull();

        declaratie.valtbinnenDeclaratieregels(new HashSet<>(Set.of(declaratieregelBack)));
        assertThat(declaratie.getValtbinnenDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getValtbinnenDeclaratie()).isEqualTo(declaratie);

        declaratie.setValtbinnenDeclaratieregels(new HashSet<>());
        assertThat(declaratie.getValtbinnenDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getValtbinnenDeclaratie()).isNull();
    }
}

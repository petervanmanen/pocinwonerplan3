package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.RapportagemomentTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RapportagemomentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rapportagemoment.class);
        Rapportagemoment rapportagemoment1 = getRapportagemomentSample1();
        Rapportagemoment rapportagemoment2 = new Rapportagemoment();
        assertThat(rapportagemoment1).isNotEqualTo(rapportagemoment2);

        rapportagemoment2.setId(rapportagemoment1.getId());
        assertThat(rapportagemoment1).isEqualTo(rapportagemoment2);

        rapportagemoment2 = getRapportagemomentSample2();
        assertThat(rapportagemoment1).isNotEqualTo(rapportagemoment2);
    }

    @Test
    void heeftDocumentTest() throws Exception {
        Rapportagemoment rapportagemoment = getRapportagemomentRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        rapportagemoment.addHeeftDocument(documentBack);
        assertThat(rapportagemoment.getHeeftDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getHeeftRapportagemoment()).isEqualTo(rapportagemoment);

        rapportagemoment.removeHeeftDocument(documentBack);
        assertThat(rapportagemoment.getHeeftDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getHeeftRapportagemoment()).isNull();

        rapportagemoment.heeftDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(rapportagemoment.getHeeftDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getHeeftRapportagemoment()).isEqualTo(rapportagemoment);

        rapportagemoment.setHeeftDocuments(new HashSet<>());
        assertThat(rapportagemoment.getHeeftDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getHeeftRapportagemoment()).isNull();
    }

    @Test
    void heeftSubsidieTest() throws Exception {
        Rapportagemoment rapportagemoment = getRapportagemomentRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        rapportagemoment.setHeeftSubsidie(subsidieBack);
        assertThat(rapportagemoment.getHeeftSubsidie()).isEqualTo(subsidieBack);

        rapportagemoment.heeftSubsidie(null);
        assertThat(rapportagemoment.getHeeftSubsidie()).isNull();
    }

    @Test
    void projectleiderRechtspersoonTest() throws Exception {
        Rapportagemoment rapportagemoment = getRapportagemomentRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        rapportagemoment.setProjectleiderRechtspersoon(rechtspersoonBack);
        assertThat(rapportagemoment.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoonBack);

        rapportagemoment.projectleiderRechtspersoon(null);
        assertThat(rapportagemoment.getProjectleiderRechtspersoon()).isNull();
    }
}

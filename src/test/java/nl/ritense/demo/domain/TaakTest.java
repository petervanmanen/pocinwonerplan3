package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.TaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taak.class);
        Taak taak1 = getTaakSample1();
        Taak taak2 = new Taak();
        assertThat(taak1).isNotEqualTo(taak2);

        taak2.setId(taak1.getId());
        assertThat(taak1).isEqualTo(taak2);

        taak2 = getTaakSample2();
        assertThat(taak1).isNotEqualTo(taak2);
    }

    @Test
    void projectleiderRechtspersoonTest() throws Exception {
        Taak taak = getTaakRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        taak.setProjectleiderRechtspersoon(rechtspersoonBack);
        assertThat(taak.getProjectleiderRechtspersoon()).isEqualTo(rechtspersoonBack);

        taak.projectleiderRechtspersoon(null);
        assertThat(taak.getProjectleiderRechtspersoon()).isNull();
    }

    @Test
    void heeftSubsidieTest() throws Exception {
        Taak taak = getTaakRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        taak.setHeeftSubsidie(subsidieBack);
        assertThat(taak.getHeeftSubsidie()).isEqualTo(subsidieBack);

        taak.heeftSubsidie(null);
        assertThat(taak.getHeeftSubsidie()).isNull();
    }
}

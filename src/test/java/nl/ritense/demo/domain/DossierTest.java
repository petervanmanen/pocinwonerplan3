package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DossierTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dossier.class);
        Dossier dossier1 = getDossierSample1();
        Dossier dossier2 = new Dossier();
        assertThat(dossier1).isNotEqualTo(dossier2);

        dossier2.setId(dossier1.getId());
        assertThat(dossier1).isEqualTo(dossier2);

        dossier2 = getDossierSample2();
        assertThat(dossier1).isNotEqualTo(dossier2);
    }

    @Test
    void hoortbijRaadsstukTest() throws Exception {
        Dossier dossier = getDossierRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        dossier.addHoortbijRaadsstuk(raadsstukBack);
        assertThat(dossier.getHoortbijRaadsstuks()).containsOnly(raadsstukBack);

        dossier.removeHoortbijRaadsstuk(raadsstukBack);
        assertThat(dossier.getHoortbijRaadsstuks()).doesNotContain(raadsstukBack);

        dossier.hoortbijRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(dossier.getHoortbijRaadsstuks()).containsOnly(raadsstukBack);

        dossier.setHoortbijRaadsstuks(new HashSet<>());
        assertThat(dossier.getHoortbijRaadsstuks()).doesNotContain(raadsstukBack);
    }
}

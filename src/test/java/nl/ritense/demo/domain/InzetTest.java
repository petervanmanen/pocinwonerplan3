package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstverbandTestSamples.*;
import static nl.ritense.demo.domain.InzetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InzetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inzet.class);
        Inzet inzet1 = getInzetSample1();
        Inzet inzet2 = new Inzet();
        assertThat(inzet1).isNotEqualTo(inzet2);

        inzet2.setId(inzet1.getId());
        assertThat(inzet1).isEqualTo(inzet2);

        inzet2 = getInzetSample2();
        assertThat(inzet1).isNotEqualTo(inzet2);
    }

    @Test
    void aantalvolgensinzetDienstverbandTest() throws Exception {
        Inzet inzet = getInzetRandomSampleGenerator();
        Dienstverband dienstverbandBack = getDienstverbandRandomSampleGenerator();

        inzet.addAantalvolgensinzetDienstverband(dienstverbandBack);
        assertThat(inzet.getAantalvolgensinzetDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetInzet()).isEqualTo(inzet);

        inzet.removeAantalvolgensinzetDienstverband(dienstverbandBack);
        assertThat(inzet.getAantalvolgensinzetDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetInzet()).isNull();

        inzet.aantalvolgensinzetDienstverbands(new HashSet<>(Set.of(dienstverbandBack)));
        assertThat(inzet.getAantalvolgensinzetDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetInzet()).isEqualTo(inzet);

        inzet.setAantalvolgensinzetDienstverbands(new HashSet<>());
        assertThat(inzet.getAantalvolgensinzetDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetInzet()).isNull();
    }
}

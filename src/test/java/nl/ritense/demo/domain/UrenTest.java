package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstverbandTestSamples.*;
import static nl.ritense.demo.domain.UrenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UrenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uren.class);
        Uren uren1 = getUrenSample1();
        Uren uren2 = new Uren();
        assertThat(uren1).isNotEqualTo(uren2);

        uren2.setId(uren1.getId());
        assertThat(uren1).isEqualTo(uren2);

        uren2 = getUrenSample2();
        assertThat(uren1).isNotEqualTo(uren2);
    }

    @Test
    void aantalvolgensinzetDienstverbandTest() throws Exception {
        Uren uren = getUrenRandomSampleGenerator();
        Dienstverband dienstverbandBack = getDienstverbandRandomSampleGenerator();

        uren.addAantalvolgensinzetDienstverband(dienstverbandBack);
        assertThat(uren.getAantalvolgensinzetDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetUren()).isEqualTo(uren);

        uren.removeAantalvolgensinzetDienstverband(dienstverbandBack);
        assertThat(uren.getAantalvolgensinzetDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetUren()).isNull();

        uren.aantalvolgensinzetDienstverbands(new HashSet<>(Set.of(dienstverbandBack)));
        assertThat(uren.getAantalvolgensinzetDienstverbands()).containsOnly(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetUren()).isEqualTo(uren);

        uren.setAantalvolgensinzetDienstverbands(new HashSet<>());
        assertThat(uren.getAantalvolgensinzetDienstverbands()).doesNotContain(dienstverbandBack);
        assertThat(dienstverbandBack.getAantalvolgensinzetUren()).isNull();
    }
}

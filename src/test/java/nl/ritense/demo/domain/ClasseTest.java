package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClasseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classe.class);
        Classe classe1 = getClasseSample1();
        Classe classe2 = new Classe();
        assertThat(classe1).isNotEqualTo(classe2);

        classe2.setId(classe1.getId());
        assertThat(classe1).isEqualTo(classe2);

        classe2 = getClasseSample2();
        assertThat(classe1).isNotEqualTo(classe2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classe classe = new Classe();
        assertThat(classe.hashCode()).isZero();

        Classe classe1 = getClasseSample1();
        classe.setId(classe1.getId());
        assertThat(classe).hasSameHashCodeAs(classe1);
    }
}

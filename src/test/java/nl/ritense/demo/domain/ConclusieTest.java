package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ConclusieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConclusieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conclusie.class);
        Conclusie conclusie1 = getConclusieSample1();
        Conclusie conclusie2 = new Conclusie();
        assertThat(conclusie1).isNotEqualTo(conclusie2);

        conclusie2.setId(conclusie1.getId());
        assertThat(conclusie1).isEqualTo(conclusie2);

        conclusie2 = getConclusieSample2();
        assertThat(conclusie1).isNotEqualTo(conclusie2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Conclusie conclusie = new Conclusie();
        assertThat(conclusie.hashCode()).isZero();

        Conclusie conclusie1 = getConclusieSample1();
        conclusie.setId(conclusie1.getId());
        assertThat(conclusie).hasSameHashCodeAs(conclusie1);
    }
}

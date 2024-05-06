package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OorspronkelijkefunctieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OorspronkelijkefunctieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oorspronkelijkefunctie.class);
        Oorspronkelijkefunctie oorspronkelijkefunctie1 = getOorspronkelijkefunctieSample1();
        Oorspronkelijkefunctie oorspronkelijkefunctie2 = new Oorspronkelijkefunctie();
        assertThat(oorspronkelijkefunctie1).isNotEqualTo(oorspronkelijkefunctie2);

        oorspronkelijkefunctie2.setId(oorspronkelijkefunctie1.getId());
        assertThat(oorspronkelijkefunctie1).isEqualTo(oorspronkelijkefunctie2);

        oorspronkelijkefunctie2 = getOorspronkelijkefunctieSample2();
        assertThat(oorspronkelijkefunctie1).isNotEqualTo(oorspronkelijkefunctie2);
    }
}

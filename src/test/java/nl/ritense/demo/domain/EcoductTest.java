package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EcoductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcoductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecoduct.class);
        Ecoduct ecoduct1 = getEcoductSample1();
        Ecoduct ecoduct2 = new Ecoduct();
        assertThat(ecoduct1).isNotEqualTo(ecoduct2);

        ecoduct2.setId(ecoduct1.getId());
        assertThat(ecoduct1).isEqualTo(ecoduct2);

        ecoduct2 = getEcoductSample2();
        assertThat(ecoduct1).isNotEqualTo(ecoduct2);
    }
}

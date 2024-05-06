package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GeneralisatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeneralisatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Generalisatie.class);
        Generalisatie generalisatie1 = getGeneralisatieSample1();
        Generalisatie generalisatie2 = new Generalisatie();
        assertThat(generalisatie1).isNotEqualTo(generalisatie2);

        generalisatie2.setId(generalisatie1.getId());
        assertThat(generalisatie1).isEqualTo(generalisatie2);

        generalisatie2 = getGeneralisatieSample2();
        assertThat(generalisatie1).isNotEqualTo(generalisatie2);
    }
}

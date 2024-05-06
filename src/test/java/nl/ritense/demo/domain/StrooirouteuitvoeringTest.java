package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StrooirouteuitvoeringTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrooirouteuitvoeringTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strooirouteuitvoering.class);
        Strooirouteuitvoering strooirouteuitvoering1 = getStrooirouteuitvoeringSample1();
        Strooirouteuitvoering strooirouteuitvoering2 = new Strooirouteuitvoering();
        assertThat(strooirouteuitvoering1).isNotEqualTo(strooirouteuitvoering2);

        strooirouteuitvoering2.setId(strooirouteuitvoering1.getId());
        assertThat(strooirouteuitvoering1).isEqualTo(strooirouteuitvoering2);

        strooirouteuitvoering2 = getStrooirouteuitvoeringSample2();
        assertThat(strooirouteuitvoering1).isNotEqualTo(strooirouteuitvoering2);
    }
}

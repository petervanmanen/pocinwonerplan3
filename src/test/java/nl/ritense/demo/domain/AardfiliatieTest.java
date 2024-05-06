package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AardfiliatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AardfiliatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aardfiliatie.class);
        Aardfiliatie aardfiliatie1 = getAardfiliatieSample1();
        Aardfiliatie aardfiliatie2 = new Aardfiliatie();
        assertThat(aardfiliatie1).isNotEqualTo(aardfiliatie2);

        aardfiliatie2.setId(aardfiliatie1.getId());
        assertThat(aardfiliatie1).isEqualTo(aardfiliatie2);

        aardfiliatie2 = getAardfiliatieSample2();
        assertThat(aardfiliatie1).isNotEqualTo(aardfiliatie2);
    }
}

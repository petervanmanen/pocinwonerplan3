package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Actie.class);
        Actie actie1 = getActieSample1();
        Actie actie2 = new Actie();
        assertThat(actie1).isNotEqualTo(actie2);

        actie2.setId(actie1.getId());
        assertThat(actie1).isEqualTo(actie2);

        actie2 = getActieSample2();
        assertThat(actie1).isNotEqualTo(actie2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Actie actie = new Actie();
        assertThat(actie.hashCode()).isZero();

        Actie actie1 = getActieSample1();
        actie.setId(actie1.getId());
        assertThat(actie).hasSameHashCodeAs(actie1);
    }
}

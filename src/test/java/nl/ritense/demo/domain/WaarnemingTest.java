package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WaarnemingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaarnemingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waarneming.class);
        Waarneming waarneming1 = getWaarnemingSample1();
        Waarneming waarneming2 = new Waarneming();
        assertThat(waarneming1).isNotEqualTo(waarneming2);

        waarneming2.setId(waarneming1.getId());
        assertThat(waarneming1).isEqualTo(waarneming2);

        waarneming2 = getWaarnemingSample2();
        assertThat(waarneming1).isNotEqualTo(waarneming2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Waarneming waarneming = new Waarneming();
        assertThat(waarneming.hashCode()).isZero();

        Waarneming waarneming1 = getWaarnemingSample1();
        waarneming.setId(waarneming1.getId());
        assertThat(waarneming).hasSameHashCodeAs(waarneming1);
    }
}

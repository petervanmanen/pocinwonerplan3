package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IndieningsvereistenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndieningsvereistenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indieningsvereisten.class);
        Indieningsvereisten indieningsvereisten1 = getIndieningsvereistenSample1();
        Indieningsvereisten indieningsvereisten2 = new Indieningsvereisten();
        assertThat(indieningsvereisten1).isNotEqualTo(indieningsvereisten2);

        indieningsvereisten2.setId(indieningsvereisten1.getId());
        assertThat(indieningsvereisten1).isEqualTo(indieningsvereisten2);

        indieningsvereisten2 = getIndieningsvereistenSample2();
        assertThat(indieningsvereisten1).isNotEqualTo(indieningsvereisten2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Indieningsvereisten indieningsvereisten = new Indieningsvereisten();
        assertThat(indieningsvereisten.hashCode()).isZero();

        Indieningsvereisten indieningsvereisten1 = getIndieningsvereistenSample1();
        indieningsvereisten.setId(indieningsvereisten1.getId());
        assertThat(indieningsvereisten).hasSameHashCodeAs(indieningsvereisten1);
    }
}

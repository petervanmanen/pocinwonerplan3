package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InburgeraarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InburgeraarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inburgeraar.class);
        Inburgeraar inburgeraar1 = getInburgeraarSample1();
        Inburgeraar inburgeraar2 = new Inburgeraar();
        assertThat(inburgeraar1).isNotEqualTo(inburgeraar2);

        inburgeraar2.setId(inburgeraar1.getId());
        assertThat(inburgeraar1).isEqualTo(inburgeraar2);

        inburgeraar2 = getInburgeraarSample2();
        assertThat(inburgeraar1).isNotEqualTo(inburgeraar2);
    }
}

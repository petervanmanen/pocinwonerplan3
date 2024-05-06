package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerjaarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeerjaarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leerjaar.class);
        Leerjaar leerjaar1 = getLeerjaarSample1();
        Leerjaar leerjaar2 = new Leerjaar();
        assertThat(leerjaar1).isNotEqualTo(leerjaar2);

        leerjaar2.setId(leerjaar1.getId());
        assertThat(leerjaar1).isEqualTo(leerjaar2);

        leerjaar2 = getLeerjaarSample2();
        assertThat(leerjaar1).isNotEqualTo(leerjaar2);
    }
}

package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitkeringsrunTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitkeringsrunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitkeringsrun.class);
        Uitkeringsrun uitkeringsrun1 = getUitkeringsrunSample1();
        Uitkeringsrun uitkeringsrun2 = new Uitkeringsrun();
        assertThat(uitkeringsrun1).isNotEqualTo(uitkeringsrun2);

        uitkeringsrun2.setId(uitkeringsrun1.getId());
        assertThat(uitkeringsrun1).isEqualTo(uitkeringsrun2);

        uitkeringsrun2 = getUitkeringsrunSample2();
        assertThat(uitkeringsrun1).isNotEqualTo(uitkeringsrun2);
    }
}

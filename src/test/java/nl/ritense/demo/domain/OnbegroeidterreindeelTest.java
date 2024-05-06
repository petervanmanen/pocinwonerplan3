package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnbegroeidterreindeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnbegroeidterreindeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onbegroeidterreindeel.class);
        Onbegroeidterreindeel onbegroeidterreindeel1 = getOnbegroeidterreindeelSample1();
        Onbegroeidterreindeel onbegroeidterreindeel2 = new Onbegroeidterreindeel();
        assertThat(onbegroeidterreindeel1).isNotEqualTo(onbegroeidterreindeel2);

        onbegroeidterreindeel2.setId(onbegroeidterreindeel1.getId());
        assertThat(onbegroeidterreindeel1).isEqualTo(onbegroeidterreindeel2);

        onbegroeidterreindeel2 = getOnbegroeidterreindeelSample2();
        assertThat(onbegroeidterreindeel1).isNotEqualTo(onbegroeidterreindeel2);
    }
}

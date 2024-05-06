package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerkooppuntTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerkooppuntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verkooppunt.class);
        Verkooppunt verkooppunt1 = getVerkooppuntSample1();
        Verkooppunt verkooppunt2 = new Verkooppunt();
        assertThat(verkooppunt1).isNotEqualTo(verkooppunt2);

        verkooppunt2.setId(verkooppunt1.getId());
        assertThat(verkooppunt1).isEqualTo(verkooppunt2);

        verkooppunt2 = getVerkooppuntSample2();
        assertThat(verkooppunt1).isNotEqualTo(verkooppunt2);
    }
}

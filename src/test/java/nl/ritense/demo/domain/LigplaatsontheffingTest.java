package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LigplaatsontheffingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigplaatsontheffingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ligplaatsontheffing.class);
        Ligplaatsontheffing ligplaatsontheffing1 = getLigplaatsontheffingSample1();
        Ligplaatsontheffing ligplaatsontheffing2 = new Ligplaatsontheffing();
        assertThat(ligplaatsontheffing1).isNotEqualTo(ligplaatsontheffing2);

        ligplaatsontheffing2.setId(ligplaatsontheffing1.getId());
        assertThat(ligplaatsontheffing1).isEqualTo(ligplaatsontheffing2);

        ligplaatsontheffing2 = getLigplaatsontheffingSample2();
        assertThat(ligplaatsontheffing1).isNotEqualTo(ligplaatsontheffing2);
    }
}

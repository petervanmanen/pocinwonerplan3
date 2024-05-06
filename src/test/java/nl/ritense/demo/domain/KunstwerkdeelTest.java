package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KunstwerkdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KunstwerkdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kunstwerkdeel.class);
        Kunstwerkdeel kunstwerkdeel1 = getKunstwerkdeelSample1();
        Kunstwerkdeel kunstwerkdeel2 = new Kunstwerkdeel();
        assertThat(kunstwerkdeel1).isNotEqualTo(kunstwerkdeel2);

        kunstwerkdeel2.setId(kunstwerkdeel1.getId());
        assertThat(kunstwerkdeel1).isEqualTo(kunstwerkdeel2);

        kunstwerkdeel2 = getKunstwerkdeelSample2();
        assertThat(kunstwerkdeel1).isNotEqualTo(kunstwerkdeel2);
    }
}

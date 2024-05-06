package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverigbouwwerkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverigbouwwerkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overigbouwwerk.class);
        Overigbouwwerk overigbouwwerk1 = getOverigbouwwerkSample1();
        Overigbouwwerk overigbouwwerk2 = new Overigbouwwerk();
        assertThat(overigbouwwerk1).isNotEqualTo(overigbouwwerk2);

        overigbouwwerk2.setId(overigbouwwerk1.getId());
        assertThat(overigbouwwerk1).isEqualTo(overigbouwwerk2);

        overigbouwwerk2 = getOverigbouwwerkSample2();
        assertThat(overigbouwwerk1).isNotEqualTo(overigbouwwerk2);
    }
}

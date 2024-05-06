package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortoverigbouwwerkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortoverigbouwwerkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortoverigbouwwerk.class);
        Soortoverigbouwwerk soortoverigbouwwerk1 = getSoortoverigbouwwerkSample1();
        Soortoverigbouwwerk soortoverigbouwwerk2 = new Soortoverigbouwwerk();
        assertThat(soortoverigbouwwerk1).isNotEqualTo(soortoverigbouwwerk2);

        soortoverigbouwwerk2.setId(soortoverigbouwwerk1.getId());
        assertThat(soortoverigbouwwerk1).isEqualTo(soortoverigbouwwerk2);

        soortoverigbouwwerk2 = getSoortoverigbouwwerkSample2();
        assertThat(soortoverigbouwwerk1).isNotEqualTo(soortoverigbouwwerk2);
    }
}

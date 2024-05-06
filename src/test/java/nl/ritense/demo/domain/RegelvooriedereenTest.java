package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RegelvooriedereenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegelvooriedereenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regelvooriedereen.class);
        Regelvooriedereen regelvooriedereen1 = getRegelvooriedereenSample1();
        Regelvooriedereen regelvooriedereen2 = new Regelvooriedereen();
        assertThat(regelvooriedereen1).isNotEqualTo(regelvooriedereen2);

        regelvooriedereen2.setId(regelvooriedereen1.getId());
        assertThat(regelvooriedereen1).isEqualTo(regelvooriedereen2);

        regelvooriedereen2 = getRegelvooriedereenSample2();
        assertThat(regelvooriedereen1).isNotEqualTo(regelvooriedereen2);
    }
}

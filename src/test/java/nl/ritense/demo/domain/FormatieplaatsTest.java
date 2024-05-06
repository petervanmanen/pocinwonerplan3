package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FormatieplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormatieplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formatieplaats.class);
        Formatieplaats formatieplaats1 = getFormatieplaatsSample1();
        Formatieplaats formatieplaats2 = new Formatieplaats();
        assertThat(formatieplaats1).isNotEqualTo(formatieplaats2);

        formatieplaats2.setId(formatieplaats1.getId());
        assertThat(formatieplaats1).isEqualTo(formatieplaats2);

        formatieplaats2 = getFormatieplaatsSample2();
        assertThat(formatieplaats1).isNotEqualTo(formatieplaats2);
    }
}
